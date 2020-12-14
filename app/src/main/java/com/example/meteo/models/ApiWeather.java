package com.example.meteo.models;

import java.util.List;

public class ApiWeather {
    // Faut garder la meme nomination de variable comme dans le fichier JSON!
    private int cod;
    private String name;
    private String message; // message d'erreur

    private Main main;
    private List<Weather> weather;

    // Getters and Setters
    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }
}
