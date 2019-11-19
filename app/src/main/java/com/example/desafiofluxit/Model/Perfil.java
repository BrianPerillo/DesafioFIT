package com.example.desafiofluxit.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Perfil implements Serializable {

    private String username;
    private String thumbnail;
    private String largePic;
    private String name;
    private String lastName;
    private String email;
    private Integer edad;
    private String latitude;
    private String longitude;


    public Perfil(String username, String thumbnail, String largePic, String name, String lastName, String email, Integer edad, String latitude, String longitude) {
        this.username = username;
        this.thumbnail = thumbnail;
        this.largePic = largePic;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.edad = edad;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLargePic() {
        return largePic;
    }

    public void setLargePic(String largePic) {
        this.largePic = largePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
