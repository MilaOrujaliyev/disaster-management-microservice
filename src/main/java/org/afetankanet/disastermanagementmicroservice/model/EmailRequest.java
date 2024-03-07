package org.afetankanet.disastermanagementmicroservice.model;

import java.util.Map;

public class EmailRequest {
    private String to;
    private String subject;
    private String templateName;
    private Map<String, Object> templateModel;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Map<String, Object> getTemplateModel() {
        return templateModel;
    }

    public void setTemplateModel(Map<String, Object> templateModel) {
        this.templateModel = templateModel;
    }
}

