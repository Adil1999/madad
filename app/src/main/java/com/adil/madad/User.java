package com.adil.madad;

import java.io.Serializable;

public class User implements Serializable {
    String id, name, email, img;
    Boolean isHospital;

    User(){}

    public Boolean getHospital() {
        return isHospital;
    }

    public void setHospital(Boolean hospital) {
        isHospital = hospital;
    }

    User(String id, String n, String e, String img, Boolean isHospital){
        this.id = id;
        this.name = n;
        this.email = e;
        this.img = img;
        this.isHospital = isHospital;
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
