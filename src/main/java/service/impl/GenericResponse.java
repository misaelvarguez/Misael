package com.example.misael.serviceimpl;

public class GenericResponse {
    int code;
    String message;
    Object loginAuthentication;


    public GenericResponse(int code, String message, Object loginAuthentication) {
        this.code = code;
        this.message = message;
        this.loginAuthentication = loginAuthentication;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getLoginAuthentication() {
        return loginAuthentication;
    }

    public void setLoginAuthentication(Object loginAuthentication) {
        this.loginAuthentication = loginAuthentication;
    }

}