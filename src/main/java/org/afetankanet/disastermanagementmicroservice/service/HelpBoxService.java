package org.afetankanet.disastermanagementmicroservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.afetankanet.disastermanagementmicroservice.entity.HelpBox;
import org.afetankanet.disastermanagementmicroservice.repository.HelpBoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelpBoxService {

    @Autowired
    private HelpBoxRepository helpBoxRepository;

    public HelpBox createHelpBox(HelpBox helpBox) {

        boolean hasActiveBox=helpBoxRepository.existsByUserIdAndActive(helpBox.getUser().getId(),true);
        if(hasActiveBox){
            throw new IllegalStateException("Kullanıcı zaten aktif bir yardım kutusuna sahip.");
        }

        helpBox.setActive(true);
        return helpBoxRepository.save(helpBox);
    }

    public HelpBox updateHelpBox(Long id, HelpBox updatedHelpBox) {
        HelpBox existingHelpBox = helpBoxRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("yardım kutusu bulunamadı");

        existingHelpBox.setSummary(updatedHelpBox.getSummary());
        existingHelpBox.setCategories(updatedHelpBox.getCategories());
        existingHelpBox.setContactInfo(updatedHelpBox.getContactInfo());
        existingHelpBox.setActive(updatedHelpBox.getActive());
        existingHelpBox.setCity(updatedHelpBox.getCity());
        existingHelpBox.setPurpose(updatedHelpBox.getPurpose());

        return helpBoxRepository.save(existingHelpBox);
    }
    public List<HelpBox> getAllHelpBoxes() {
        return helpBoxRepository.findAll();
    }

    public List<HelpBox> getHelpBoxesByUserId(Long userId) {
        return helpBoxRepository.findByUserId(userId);
    }
}
