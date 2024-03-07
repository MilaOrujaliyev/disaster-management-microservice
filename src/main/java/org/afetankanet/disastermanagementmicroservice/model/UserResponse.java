package org.afetankanet.disastermanagementmicroservice.model;

public class UserResponse {

    private Long id;
    private String email;
    private String username;
    private String nameSurname;

    public UserResponse(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public UserResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNameSurname() {
        return nameSurname;
    }

    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }
}
