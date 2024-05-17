package com.spring.springjpa.repository;

import com.spring.springjpa.Model.Enrollment;

import java.util.List;

public interface EnrollmentRepository {
    String enrollStudent(String bID, String classID);
    List<Enrollment> getAllEnrollments();
    String deleteEnrollment(String bID, String classID);
}
