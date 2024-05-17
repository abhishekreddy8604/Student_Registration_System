package com.spring.springjpa.Model;

public class Classes {

    private String classid;

    private String dept_code;

    private int courseID;

    private int sect;

    private int year;

    private String semester;

    private int limit;

    private int classSize;

    private String room;

    public Classes(String classid, String dept_code, int courseID, int sect, int year, String semester, int limit, int classSize, String room) {
        this.classid = classid;
        this.dept_code = dept_code;
        this.courseID = courseID;
        this.sect = sect;
        this.year = year;
        this.semester = semester;
        this.limit = limit;
        this.classSize = classSize;
        this.room = room;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
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

    public int getSect() {
        return sect;
    }

    public void setSect(int sect) {
        this.sect = sect;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getClassSize() {
        return classSize;
    }

    public void setClassSize(int classSize) {
        this.classSize = classSize;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}