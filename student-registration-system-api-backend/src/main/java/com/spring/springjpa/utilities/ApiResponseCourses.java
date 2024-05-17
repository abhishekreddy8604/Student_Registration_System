package com.spring.springjpa.utilities;

import com.spring.springjpa.Model.Courses;

import java.util.List;

public class ApiResponseCourses {
    private boolean status;
    private String message;
    private List<Courses> data;

    public ApiResponseCourses(boolean status, String message, List<Courses> data) {
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

    public List<Courses> getData() {
        return data;
    }

    public void setData(List<Courses> data) {
        this.data = data;
    }
}
