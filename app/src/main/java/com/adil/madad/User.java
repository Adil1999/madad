package com.adil.madad;

import java.io.Serializable;

public class User implements Serializable {
    String id, name, email, img, number;
    String address;
    Boolean isHospital;
    Double longtitude, latitude;

    User(){}

    public User(String id, String name, String email, String img, String address, Boolean isHospital, Double longtitude, Double latitude, String number) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.img = img;
        this.address = address;
        this.isHospital = isHospital;
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Boolean getHospital() {
        return isHospital;
    }

    public void setHospital(Boolean hospital) {
        isHospital = hospital;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
