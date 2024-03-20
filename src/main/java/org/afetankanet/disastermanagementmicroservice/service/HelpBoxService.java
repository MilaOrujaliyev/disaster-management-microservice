package org.afetankanet.disastermanagementmicroservice.service;

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
        helpBox.setActive(true);
        return helpBoxRepository.save(helpBox);
    }


    public List<HelpBox> getAllHelpBoxes() {
        return helpBoxRepository.findAll();
    }

    public List<HelpBox> getHelpBoxesByUserId(Long userId) {
        return helpBoxRepository.findByUserId(userId);
    }
}
