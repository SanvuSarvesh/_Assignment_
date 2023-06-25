package com.example.GreenStich.Common;

import lombok.Data;

@Data
public class BaseResponse {
    private String statusCode;
    private Object payLoad;
    private boolean success;
    private String message;
}
