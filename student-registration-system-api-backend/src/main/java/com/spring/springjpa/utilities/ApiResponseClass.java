package com.spring.springjpa.utilities;

import com.spring.springjpa.Model.Classes;

public class ApiResponseClass {
    private boolean status;
    private String message;
    private Classes data;

    public ApiResponseClass(boolean status, String message, Classes data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Classes getData() {
        return data;
    }

    public void setData(Classes data) {
        this.data = data;
    }
}
