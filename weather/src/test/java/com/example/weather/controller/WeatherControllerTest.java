package com.example.weather.controller;


import com.example.weather.exceptions.RecordNotFoundException;
import com.example.weather.model.WeatherData;
import com.example.weather.service.WeatherService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = WeatherController.class)
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @InjectMocks
    private WeatherController weatherController;

    @Test
    void validZipCode() throws Exception {

        WeatherData weatherInformation = new WeatherData();
        weatherInformation.setTemp(8.4);
        weatherInformation.setDateTime(LocalDateTime.now());

        when(weatherService.getMinTempByZipCode("10001")).thenReturn(weatherInformation);

        Assert.assertEquals( new ResponseEntity<>(weatherInformation, HttpStatus.OK), weatherController.getMinTempByZipCode("10001"));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/weather/min-temp/{zipcode}", "10001")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void invalidZipCode() throws Exception {

        when(weatherService.getMinTempByZipCode("00000")).thenThrow(new RecordNotFoundException("Invalid zip code"));
        Assertions.assertThrows(RecordNotFoundException.class, () -> weatherController.getMinTempByZipCode("00000"));
        mockMvc.perform(get("/api/weather/min-temp/{zipcode}", "00000")).andExpect(status().isNotFound());
    }

    @Test
    void incorrectZipCode() throws Exception {
        mockMvc.perform(get("/api/weather/min-temp/{zipcode}", "-1")).andExpect(status().isBadRequest());
    }

    @Test
    void noZipCode() throws Exception {
        mockMvc.perform(get("/api/weather/min-temp/{zipcode}", "")).andExpect(status().isNotFound());
    }

}
