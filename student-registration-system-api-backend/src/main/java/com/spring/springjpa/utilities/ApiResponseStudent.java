package com.spring.springjpa.utilities;

import com.spring.springjpa.Model.Student;

public class ApiResponseStudent {
    private boolean status;
    private String message;
    private Student data;

    public ApiResponseStudent(boolean status, String message, Student data) {
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

    public Student getData() {
        return data;
    }

    public void setData(Student data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
