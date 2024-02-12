package org.afetankanet.disastermanagementmicroservice.util;

public  class MessageResponse {
    private String message;
    private String code;

    public MessageResponse(String message) {
        this.message = message;
    }

    public MessageResponse(String code, String message) {

        this.message = message;
        this.code=code;
    }

    // Getter
    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
