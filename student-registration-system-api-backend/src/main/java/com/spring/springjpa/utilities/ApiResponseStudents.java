package com.spring.springjpa.utilities;

import com.spring.springjpa.Model.Student;

import java.util.List;

public class ApiResponseStudents{
    private boolean status;
    private String message;
    private List<Student> data;

    public ApiResponseStudents(boolean status, String message, List<Student> data) {
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

    public List<Student> getData() {
        return data;
    }

    public void setData(List<Student> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
