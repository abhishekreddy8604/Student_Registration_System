package com.spring.springjpa.repository;

import com.spring.springjpa.Model.Courses;

import java.util.List;

public interface CoursesRepository {
    Courses findById(String dept_code, int courseID);
    List<Courses> findAll();
    void save(Courses course);
    void deleteById(String dept_code, int courseID);
}
