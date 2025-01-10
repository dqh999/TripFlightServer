package com.flight.server.infrastructure.dataTransferObject.request;

import java.util.Map;

public class EmailRequest {
    private String to;
    private String subject;
    private String template;
    private Map<String, Object> variables;

    public EmailRequest() {
    }

    public EmailRequest(String to, String subject, String template, Map<String, Object> variables) {
        this.to = to;
        this.subject = subject;
        this.template = template;
        this.variables = variables;
    }

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

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }
}
