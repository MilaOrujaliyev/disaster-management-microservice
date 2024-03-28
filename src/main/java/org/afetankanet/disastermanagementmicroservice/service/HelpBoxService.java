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
        return helpBoxRepository.findByUserId(userId,Sort.by(Sort.Direction.DESC, "active"));
    }

    public void sendHelpBoxEmail(Long helpBoxId, String emailContent) {

            HelpBox helpBox = helpBoxRepository.findById(helpBoxId)
                    .orElseThrow(() -> new RuntimeException("Yardım Kutusu Bulunamadı"));

            User user = helpBox.getUser();

            Map<String, Object> extraTemplateData = new HashMap<>();

            extraTemplateData.put("emailContent", emailContent);

            emailClientService.sendEmail(
                    new UserResponse(user),
                    "Yardım Kutusu Bildirimi",
                    "helpBoxTemplate",
                    extraTemplateData);
        }
}
