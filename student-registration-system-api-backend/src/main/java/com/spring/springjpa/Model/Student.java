package com.spring.springjpa.Model;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Student {
    private String bNumber;
    private String firstName;
    private String lastName;
    private String studentLevel;
    private Double gpa;
    private String email;
    private Date birthDate;
    public String getbNumber() {
        return bNumber;
    }
    public void setbNumber(String bNumber) {
        this.bNumber = bNumber;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getStudentLevel() {
        return studentLevel;
    }
    public void setStudentLevel(String studentLevel) {
        this.studentLevel = studentLevel;
    }
    public Double getGpa() {
        return gpa;
    }
    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getBirthDate() {
        return new SimpleDateFormat("dd-MMM-yyyy").format(birthDate);
    }
    public void setBirthDate(String birthDate) throws ParseException {
        if (birthDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            // If the input date matches the format "yyyy-MM-dd"
            this.birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
        } else if (birthDate.matches("\\d{2}-[a-zA-Z]{3}-\\d{4}")) {
            // If the input date matches the format "dd-MMM-yyyy"
            this.birthDate = new SimpleDateFormat("dd-MMM-yyyy").parse(birthDate);
        } else {
            throw new IllegalArgumentException("Invalid date format");
        }
    }
    
}
