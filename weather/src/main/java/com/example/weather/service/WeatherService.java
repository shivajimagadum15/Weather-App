package com.example.weather.service;

import com.example.weather.model.WeatherData;

public interface WeatherService {

    WeatherData getMinTempByZipCode(String zipCode);
}
