package org.afetankanet.disastermanagementmicroservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.afetankanet.disastermanagementmicroservice.entity.HelpBox;
import org.afetankanet.disastermanagementmicroservice.entity.User;
import org.afetankanet.disastermanagementmicroservice.model.UserResponse;
import org.afetankanet.disastermanagementmicroservice.repository.HelpBoxRepository;
import org.afetankanet.disastermanagementmicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HelpBoxService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailClientService emailClientService;
    @Autowired
    private HelpBoxRepository helpBoxRepository;

    @Autowired
    private ChatGPTService chatGPTService;

    public HelpBox createHelpBox(HelpBox helpBox) {

        boolean hasActiveBox=helpBoxRepository.existsByUserIdAndActive(helpBox.getUser().getId(),true);
        if(hasActiveBox){
            throw new IllegalStateException("Kullanıcı zaten aktif bir yardım kutusuna sahip.");
        }

        if(!chatGPTService.isContentAppropriate(helpBox.getSummary())||!chatGPTService.isContentAppropriate(helpBox.getPurpose())){
            throw new IllegalStateException("Yardım kutusu açıklaması veya amacı uygun değil.");
        }

        helpBox.setActive(true);
        return helpBoxRepository.save(helpBox);
    }

    public HelpBox updateHelpBox(Long id, HelpBox updatedHelpBox) {
        HelpBox existingHelpBox = helpBoxRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("yardım kutusu bulunamadı"));

        existingHelpBox.setSummary(updatedHelpBox.getSummary());
        existingHelpBox.setCategories(updatedHelpBox.getCategories());
        existingHelpBox.setContactInfo(updatedHelpBox.getContactInfo());
        existingHelpBox.setActive(updatedHelpBox.getActive());
        existingHelpBox.setCity(updatedHelpBox.getCity());
        existingHelpBox.setPurpose(updatedHelpBox.getPurpose());

        return helpBoxRepository.save(existingHelpBox);
    }
    public List<HelpBox> getAllHelpBoxes() {
        return helpBoxRepository.findAll(Sort.by(Sort.Direction.DESC,"active")); //order descending according to active status

    }

    public List<HelpBox> getHelpBoxesByUserId(Long userId) {

        List<HelpBox> allHelpBoxes = helpBoxRepository.findAll();

        return allHelpBoxes.stream()
                .filter(helpBox -> helpBox.getUser() != null && helpBox.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }


    public void sendHelpBoxEmail(Long helpBoxId, String emailContent, String userEmail, String username) {
        HelpBox helpBox = helpBoxRepository.findById(helpBoxId)
                .orElseThrow(() -> new RuntimeException("Yardım Kutusu Bulunamadı"));

        User helpBoxOwner  = helpBox.getUser();

        if (!helpBoxOwner.getEmail().equals(userEmail)) {
        Map<String, Object> extraTemplateData = new HashMap<>();
        extraTemplateData.put("emailContent", emailContent);
        extraTemplateData.put("userEmail", userEmail);
        extraTemplateData.put("username", username);

        emailClientService.sendEmail(
                new UserResponse(helpBoxOwner ),
                "Yardım Kutusu Bildirimi",
                "helpBoxTemplate",
                extraTemplateData);
        } else {
            throw new IllegalArgumentException("Kutu sahibi kendine e-posta gönderemez.");
        }
    }
}
