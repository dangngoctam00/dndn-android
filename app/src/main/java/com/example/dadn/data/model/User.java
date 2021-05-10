package com.example.dadn.data.model;

public class User {
    private String fullname;
    private String email;
    private String password;
    private String token;

    public User(String fullname, String email, String password, String token) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.token = token;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
