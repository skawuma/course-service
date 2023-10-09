package com.kawuma.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceResponse<T> {

    private HttpStatus status;
    private T response;
    private List<ErrorDTO>error;

    public ServiceResponse(HttpStatus status, T response) {
        this.status = status;
        this.response = response;
    }
}
