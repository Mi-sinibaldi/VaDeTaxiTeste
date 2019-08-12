package com.example.vadetaxiteste.Data.Remote.Contract;

import com.example.vadetaxiteste.Model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IWeatherEndPoint {
    @GET("weather")
    Call<WeatherResponse> getWeather(@Query("q") String state, @Query("appid") String AppId, @Query("units") String units, @Query("lang") String lang);
}
