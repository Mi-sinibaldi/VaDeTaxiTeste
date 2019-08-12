package com.example.vadetaxiteste.Model;


import java.io.Serializable;
import java.util.List;

public class WeatherResponse implements Serializable {
    private String message;
    private  String  name;
    private int cod;
    private WeatherSys sys;
    private WeatherMain main;

    public List<WeatherWeather> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherWeather> weather) {
        this.weather = weather;
    }

    private List<WeatherWeather> weather;

    public WeatherMain getMain() {
        return main;
    }

    public void setMain(WeatherMain main) {
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WeatherSys getSys() {
        return sys;
    }

    public void setSys(WeatherSys sys) {
        this.sys = sys;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }
}

