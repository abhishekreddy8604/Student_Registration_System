package com.spring.springjpa.utilities;

import com.spring.springjpa.Model.Classes;

import java.util.List;

public class ApiResponseClasses {
    private boolean status;
    private String message;
    private List<Classes> data;

    public ApiResponseClasses(boolean status, String message, List<Classes> data) {
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

    public List<Classes> getData() {
        return data;
    }

    public void setData(List<Classes> data) {
        this.data = data;
    }
}
