package com.spring.springjpa.repository;

import com.spring.springjpa.Model.Classes;

import java.util.List;

public interface ClassesRepository {
    Classes findById(String classid);
    List<Classes> findAll();
    void save(Classes classes);
    void deleteById(String classid);
    Classes findByCompositeKey(String dept_code, int courseID, int sect, int year, String semester);
}
