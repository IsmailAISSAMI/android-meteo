package com.example.meteo.models;
// Correspond à la partie Main du response JSON, on l'utilise pour  recuperer la var temp
public class Main {
    private String temp;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
