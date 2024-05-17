package com.spring.springjpa.repository;

import com.spring.springjpa.Model.Student;

import java.util.List;

public interface StudentRepository {
        List<Student> findAll();
        Student findById(String BID);
        void save(Student student);
        void deleteById(String BID);
        Student findByEmail(String email);
}
