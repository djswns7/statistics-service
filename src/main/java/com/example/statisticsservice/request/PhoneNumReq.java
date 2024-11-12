package com.example.statisticsservice.request;

public class PhoneNumReq {
    private String phoneNumber;

    // 요청 DTO에는 기본 생성자가 꼭 필요함
    PhoneNumReq(){}

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber.trim();
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
}
