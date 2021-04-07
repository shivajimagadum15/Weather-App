package com.example.weather.controller;

import com.example.weather.helper.ResponseHelper;
import com.example.weather.model.WeatherData;
import com.example.weather.service.WeatherService;
import com.example.weather.validator.ValuesAllowed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class WeatherController {

    @Autowired
    private WeatherService weatherService;


    @ApiOperation(
            value = "Get the coolest hour of location by zip code",
            response = WeatherData.class
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "The coolest hour of the day", response = WeatherData.class),
                    @ApiResponse(code = 500, message = "Internal Server Error"),
                    @ApiResponse(code = 404, message = "No data found for given zip code"),
                    @ApiResponse(code = 400, message = "Invalid zip code")
            }
    )
    @GetMapping("/api/weather/min-temp/{zipCode}")
    public ResponseEntity<?> getMinTempByZipCode(@ValuesAllowed @PathVariable String zipCode){
        WeatherData weatherData = this.weatherService.getMinTempByZipCode(zipCode);
        return ResponseHelper.createResponse(weatherData, HttpStatus.OK);
    }
}
