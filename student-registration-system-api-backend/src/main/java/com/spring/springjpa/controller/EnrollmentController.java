package com.spring.springjpa.controller;

import com.spring.springjpa.Model.Enrollment;
import com.spring.springjpa.repository.EnrollmentRepository;
import com.spring.springjpa.utilities.ApiResponseCourse;
import com.spring.springjpa.utilities.ApiResponseCourses;
import com.spring.springjpa.utilities.ApiResponseEnrollment;
import com.spring.springjpa.utilities.ApiResponseEnrollments;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentController(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllEnrollments() {
        List<Enrollment> enrollments = enrollmentRepository.getAllEnrollments();
        return ResponseEntity.ok().body(new ApiResponseEnrollments(true, "Courses found", enrollments));
    }

    @PostMapping("/enroll")
    public ResponseEntity<?> enrollStudent(@RequestBody Enrollment enrollmentRequest) {
        try {
            String res = enrollmentRepository.enrollStudent(enrollmentRequest.getbID().toUpperCase(), enrollmentRequest.getClassID().toLowerCase());
            if(res!=null){
                return ResponseEntity.badRequest().body(new ApiResponseEnrollment(false, "Error: " + res, null));
            }
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body("Error occured at controller");
        }
        Enrollment enrollment = new Enrollment();
        enrollment.setbID(enrollmentRequest.getbID().toUpperCase());
        enrollment.setClassID(enrollmentRequest.getClassID().toLowerCase());
        return ResponseEntity.ok().body(new ApiResponseEnrollment(true, "Enrollment completed successfully.", enrollment));
    }

    @DeleteMapping("/drop/{bID}/{classID}")
    public ResponseEntity<?> dropStudentFromClass(@PathVariable("bID") String bID, @PathVariable("classID") String classID) {
        try {
            String res = enrollmentRepository.deleteEnrollment(bID.toUpperCase(), classID.toLowerCase());
            if(res!=null){
                return ResponseEntity.badRequest().body(new ApiResponseEnrollment(false, "Error: " + res, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to drop student from class. Reason: " + e.getMessage());
        }
        Enrollment enrollment = new Enrollment();
        enrollment.setbID(bID.toUpperCase());
        enrollment.setClassID(classID.toLowerCase());
        return ResponseEntity.ok().body(new ApiResponseEnrollment(true, "Enrollment deleted successfully.", enrollment));
    }
}
