package com.example.weather.service;

import com.example.weather.exceptions.RecordNotFoundException;
import com.example.weather.helper.MessageHelper;
import com.example.weather.model.WeatherData;
import com.example.weather.model.WeatherDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Value("${app.api.key}")
    private String apiKey;

    @Value("${app.api.url}")
    private String apiUrl;

    @Override
    public WeatherData getMinTempByZipCode(String zipCode) {

        WeatherDetails weatherDetails = getWeatherDetails(zipCode);
        if(weatherDetails==null || weatherDetails.getData().isEmpty()){
            throw new RecordNotFoundException(MessageHelper.get("record.not.found", new String[]{zipCode}));
        }
        List<WeatherData> sortedDateList = weatherDetails.getData().stream().sorted(Comparator.comparing(WeatherData::getDateTime)).collect(Collectors.toList());
        List<WeatherData> sortedList = sortedDateList.stream().sorted(Comparator.comparingDouble(WeatherData::getTemp)).collect(Collectors.toList());
        WeatherData weatherData = !sortedList.isEmpty()? sortedList.get(0): new WeatherData();
        weatherData.setCoolestHr(coolestHour(weatherData));
        return weatherData;
    }

    private WeatherDetails getWeatherDetails(String zipCode){
        final String uri = this.apiUrl+"?postal_code={zipCode},us&key={apiKey}";
        Map<String, String> params = new HashMap<>();
        params.put("zipCode", zipCode);
        params.put("apiKey", this.apiKey);
        RestTemplate restTemplate = new RestTemplate();
        WeatherDetails weatherDetails = restTemplate.getForObject(uri, WeatherDetails.class, params);
        return weatherDetails;
    }

    private int coolestHour(WeatherData weatherData){
        return weatherData.getDateTime().getHour();
    }
}
