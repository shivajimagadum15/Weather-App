package com.example.weather.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ResponseHelper {

    public static ResponseEntity<?> createResponse(Object object, HttpStatus httpStatus){
        return new ResponseEntity<Object>(object, httpStatus);
    }

}
