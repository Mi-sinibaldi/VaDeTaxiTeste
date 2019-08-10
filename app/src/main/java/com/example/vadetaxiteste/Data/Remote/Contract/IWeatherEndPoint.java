package com.example.vadetaxiteste.Data.Remote.Contract;

import com.example.vadetaxiteste.Model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IWeatherEndPoint {

    @GET("weather?q=Hurzuf&appid=18255c9fb107f943bad5ae65743d3aea&units=metric")
    Call<WeatherResponse> getWeather();
}
