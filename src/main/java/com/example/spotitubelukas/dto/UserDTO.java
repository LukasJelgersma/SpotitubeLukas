package com.example.spotitubelukas.dto;

public class UserDTO {

    private String user;
    private String password;
    private String name;
    private String usertoken;

    public UserDTO(String user, String password, String name, String usertoken){
        this.user = user;
        this.password = password;
        this.name = name;
        this.usertoken = usertoken;
    }

    public UserDTO(){

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", usertoken='" + usertoken + '\'' +
                '}';
    }
}
