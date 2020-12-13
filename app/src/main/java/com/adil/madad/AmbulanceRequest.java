package com.adil.madad;

public class AmbulanceRequest {
    public String name, userId, img, address, number, secNumber;

    AmbulanceRequest() {
    }

    public AmbulanceRequest(String name, String userId, String img, String address, String number, String secNumber) {
        this.name = name;
        this.userId = userId;
        this.img = img;
        this.address = address;
        this.number = number;
        this.secNumber = secNumber;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSecNumber() {
        return secNumber;
    }

    public void setSecNumber(String secNumber) {
        this.secNumber = secNumber;
    }
}

