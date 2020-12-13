package com.adil.madad;

public class BloodRequest {
    private String name, userId, img, address, phno, bloodType;

    BloodRequest(){}

    public BloodRequest(String name, String userId, String img, String address, String phno, String bloodType) {
        this.name = name;
        this.userId = userId;
        this.img = img;
        this.address = address;
        this.phno = phno;
        this.bloodType = bloodType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}
