package com.spring.springjpa.utilities;

import com.spring.springjpa.Model.Enrollment;

import java.util.List;

public class ApiResponseEnrollments {
    private boolean status;
    private String message;
    private List<Enrollment> data;

    public ApiResponseEnrollments(boolean status, String message, List<Enrollment> data) {
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

    public List<Enrollment> getData() {
        return data;
    }

    public void setData(List<Enrollment> data) {
        this.data = data;
    }
}
