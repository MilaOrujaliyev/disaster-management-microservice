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

    public void sendEmail(UserResponse userResponse,String subject, String templateName,Map<String, Object> extraTemplateData) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(userResponse.getEmail());
        emailRequest.setSubject(subject);

        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("username", userResponse.getUsername().toUpperCase(Locale.forLanguageTag("tr-TR")));
        templateModel.put("email", userResponse.getEmail());

        templateModel.putAll(extraTemplateData);

        emailRequest.setTemplateModel(templateModel);
        emailRequest.setTemplateName(templateName);

        restTemplate.postForObject(emailServiceUrl, emailRequest, String.class);
    }

    public void sendEmail(UserResponse userResponse,String subject, String templateName) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(userResponse.getEmail());
        emailRequest.setSubject(subject);

        // E-posta şablonu için bilgi
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("username", userResponse.getUsername().toUpperCase(Locale.forLanguageTag("tr-TR")));

        emailRequest.setTemplateModel(templateModel);
        emailRequest.setTemplateName(templateName);

        restTemplate.postForObject(emailServiceUrl, emailRequest, String.class);
    }

}

