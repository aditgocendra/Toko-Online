package com.oblivion.tokoonline.model;

public class Favorite_model {

    private String idUpload;


    public Favorite_model(){

    }

    public Favorite_model(String idUpload) {
        this.idUpload = idUpload;
    }


    public String getIdUpload() {
        return idUpload;
    }

    public void setIdUpload(String idUpload) {
        this.idUpload = idUpload;
    }
}
