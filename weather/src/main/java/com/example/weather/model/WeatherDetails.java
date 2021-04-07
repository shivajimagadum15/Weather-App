package com.example.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WeatherDetails {

    @JsonProperty("data")
    private List<WeatherData> data = new ArrayList<>();

}
