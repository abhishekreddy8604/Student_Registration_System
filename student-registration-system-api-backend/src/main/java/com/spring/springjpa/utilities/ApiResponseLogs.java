package com.spring.springjpa.utilities;

import com.spring.springjpa.Model.Logs;

import java.util.List;

public class ApiResponseLogs {
    private boolean status;
    private String message;
    private List<Logs> data;

    public ApiResponseLogs(boolean status, String message, List<Logs> data) {
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

    public List<Logs> getData() {
        return data;
    }

    public void setData(List<Logs> data) {
        this.data = data;
    }
}
