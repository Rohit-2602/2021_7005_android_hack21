package com.example.bonfire;

class onlinefrnds_Model{


    public onlinefrnds_Model(String imageUri,String name){
        this.imageUri=imageUri;
        this.name=name;
    }

    public String getImageUri() {
        return imageUri;
    }


    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String imageUri;
    String name;
}