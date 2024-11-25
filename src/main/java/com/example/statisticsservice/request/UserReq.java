package com.example.statisticsservice.request;

public class UserReq {
    private String userName;
    private String email;
    private String password;

    // Getters and setters (Lombok @Data를 사용할 수도 있음)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
