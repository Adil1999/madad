package com.adil.madad;

import java.io.Serializable;

public class Doctors implements Serializable {
    String id, name, licenseId, email, number, area, hospital, timmings;

    Doctors(){}

    public Doctors(String id, String name, String licenseId, String email, String number, String area, String hospital, String timmings) {
        this.id = id;
        this.name = name;
        this.licenseId = licenseId;
        this.email = email;
        this.number = number;
        this.area = area;
        this.hospital = hospital;
        this.timmings = timmings;
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

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getTimmings() {
        return timmings;
    }

    public void setTimmings(String timmings) {
        this.timmings = timmings;
    }
}
