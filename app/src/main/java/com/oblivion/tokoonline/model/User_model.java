package com.oblivion.tokoonline.model;

public class User_model {

    private String userId;
    private String name;
    private String email;
    private String num_telp;
    private String photo_url;
    private String location;


    public User_model(){

    }

    public User_model(String userId, String name, String email, String num_telp, String photo_url, String location) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.num_telp = num_telp;
        this.photo_url = photo_url;
        this.location = location;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getNum_telp() {
        return num_telp;
    }

    public void setNum_telp(String num_telp) {
        this.num_telp = num_telp;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
