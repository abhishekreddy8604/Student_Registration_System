package com.spring.springjpa.utilities;

import com.spring.springjpa.Model.Courses;

public class ApiResponseCourse {
    private boolean status;
    private String message;
    private Courses data;

    public ApiResponseCourse(boolean status, String message, Courses data) {
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

    public Courses getData() {
        return data;
    }

    public void setData(Courses data) {
        this.data = data;
    }
}
