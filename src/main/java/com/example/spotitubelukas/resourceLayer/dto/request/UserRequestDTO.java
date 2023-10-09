package com.example.spotitubelukas.resourceLayer.dto.request;

public class UserRequestDTO {
    private String user;
    private String password;

    public UserRequestDTO(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public UserRequestDTO() {

    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}

