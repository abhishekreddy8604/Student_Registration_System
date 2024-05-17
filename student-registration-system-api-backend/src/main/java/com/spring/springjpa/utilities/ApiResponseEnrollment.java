package com.spring.springjpa.utilities;

import com.spring.springjpa.Model.Enrollment;

public class ApiResponseEnrollment {
    private boolean status;
    private String message;
    private Enrollment data;

    public ApiResponseEnrollment(boolean status, String message, Enrollment data) {
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

    public Enrollment getData() {
        return data;
    }

    public void setData(Enrollment data) {
        this.data = data;
    }
}
