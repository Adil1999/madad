package com.adil.madad;

public class Appointment {
    String id;
    String name;
    String number;
    String img;
    String address;
    String docName;
    String status;
    String hospital;

    Appointment(){}

    public Appointment(String id, String name, String number, String img, String address, String docName, String status, String hospital) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.img = img;
        this.address = address;
        this.docName = docName;
        this.status = status;
        this.hospital = hospital;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
