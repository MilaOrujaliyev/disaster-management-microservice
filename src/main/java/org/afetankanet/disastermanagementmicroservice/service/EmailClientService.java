package org.afetankanet.disastermanagementmicroservice.service;

import org.afetankanet.disastermanagementmicroservice.model.EmailRequest;
import org.afetankanet.disastermanagementmicroservice.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class EmailClientService {

    private final RestTemplate restTemplate;
    private final String emailServiceUrl = "http://localhost:8089/api/email/send";

    @Autowired
    public EmailClientService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void sendRegistrationEmail(UserResponse userResponse) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(userResponse.getEmail());
        emailRequest.setSubject("Hoş Geldiniz!");

        // E-posta şablonu için gerekli bilgiler
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("username", userResponse.getUsername().toUpperCase(Locale.forLanguageTag("tr-TR"))); // Kullanıcının adını ve soyadını şablona eklemek için
        emailRequest.setTemplateModel(templateModel);
        emailRequest.setTemplateName("welcome-email"); // Thymeleaf şablonunuzun adı


        restTemplate.postForObject(emailServiceUrl, emailRequest, String.class);
    }
}

