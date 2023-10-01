package com.example.spotitubelukas.domain;

public class UserDTO {

    private String username;
    private String password;
    private String name;
    private String usertoken;


    public UserDTO(String username, String password, String name, String usertoken){
        this.username = username;
        this.password = password;
        this.name = name;
        this.usertoken = usertoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsertoken() {
        return usertoken;
    }

    public void setUsertoken(String usertoken) {
        this.usertoken = usertoken;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", usertoken='" + usertoken + '\'' +
                '}';
    }
}
