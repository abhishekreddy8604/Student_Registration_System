package com.spring.springjpa.Model;

public class Courses {

    private String dept_code;
    private int courseID;
    private String title;

    public Courses(String dept_code, String title, int courseID) {
        this.dept_code = dept_code;
        this.title = title;
        this.courseID = courseID;
    }

    public String getDept_code() {
        return dept_code;
    }

    public void setDept_code(String dept_code) {
        this.dept_code = dept_code;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}