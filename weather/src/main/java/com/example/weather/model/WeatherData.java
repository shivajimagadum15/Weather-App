package com.example.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WeatherData {

    @JsonProperty("temp")
    private Double temp;

    @JsonProperty("timestamp_local")
    private LocalDateTime dateTime;

    private int coolestHr;

}
