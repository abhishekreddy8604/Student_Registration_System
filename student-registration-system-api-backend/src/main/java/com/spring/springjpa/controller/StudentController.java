package com.spring.springjpa.controller;

import com.spring.springjpa.Model.Student;
import com.spring.springjpa.repository.StudentRepository;
import com.spring.springjpa.utilities.ApiResponseStudent;
import com.spring.springjpa.utilities.ApiResponseStudents;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/students")
public class StudentController {

        @Autowired
        private StudentRepository studentRepository;

        @GetMapping("/")
        public ResponseEntity<?> getAllStudents() {
            List<Student> students = studentRepository.findAll();
            return ResponseEntity.ok().body(new ApiResponseStudents(true, "Students found", students));
        }
        
        // @GetMapping("/{BNumber}")
        // public ResponseEntity<?> getStudentById(@PathVariable("BNumber") String BNumber) {
        //     try {
        //         Student student = studentRepository.findById(BNumber);
        //         if (student==null) {
        //             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseStudent(false, "Student not found", null));
        //         }
        //         return ResponseEntity.ok().body(new ApiResponseStudent(true, "Student found", student));
        //     } catch (Exception e) {
        //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseStudent(false, e.getMessage(), null));
        //     }
        // }

        public boolean correctStudentlevel(String studentlevel) {
            String[] levels = {"freshman", "sophomore", "junior", "senior", "master", "PhD"};
            for (String element : levels) {
                if (element.equals(studentlevel)) {
                    return true;
                }
            }
            return false;
        }

        @PostMapping("/")
        public ResponseEntity<?> createStudent(@RequestBody Student student) {
            if (student == null || student.getbNumber() == null) {
                return ResponseEntity.badRequest().body(new ApiResponseStudent(false, "Error: BID should exist.", null));
            }
            if (student.getFirstName() == null || student.getFirstName().matches(".*\\d.*")) {
                return ResponseEntity.badRequest().body(new ApiResponseStudent(false, "Error: FirstName should exist and cannot contain numeric values.", null));
            }
            if (student.getLastName() == null || student.getLastName().matches(".*\\d.*")) {
                return ResponseEntity.badRequest().body(new ApiResponseStudent(false, "Error: LastName should exist and cannot contain numeric values.", null));
            }
            boolean isBNumberUnique = studentRepository.findById(student.getbNumber()) == null;
            if (!isBNumberUnique) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponseStudent(false, "Error: BID already exists", null));
            }
            
            // Check if email is unique
            boolean isEmailUnique = studentRepository.findByEmail(student.getEmail()) == null;
            if (!isEmailUnique) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponseStudent(false, "Error: Email already exists", null));
            }
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            Pattern pattern = Pattern.compile(emailRegex);
            if (!pattern.matcher(student.getEmail()).matches()) {
                return ResponseEntity.badRequest().body(new ApiResponseStudent(false, "Error: Invalid email format", null));
            }

            if (!student.getbNumber().startsWith("B")) {
                return ResponseEntity.badRequest().body(new ApiResponseStudent(false, "Error: BID should start with 'B'.", null));
            }

            if (!correctStudentlevel(student.getStudentLevel())) {
                return ResponseEntity.badRequest().body(new ApiResponseStudent(false, "Error: Student level invalid.", null));
            }
            double roundedGpa = Math.floor(student.getGpa() * 10.0) / 10.0;
            if (roundedGpa < 0 || roundedGpa > 4.0) {
                return ResponseEntity.badRequest().body(new ApiResponseStudent(false, "Error: GPA should be between 0 and 4.0.", null));
            }
            student.setGpa(roundedGpa);

            try {
                studentRepository.save(student);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ApiResponseStudent(true, "Student created successfully.", student));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponseStudent(false, "Error occurred: " + e.getMessage(), null));
            }
        }

    public static String extractConstraintNumber(String errorMessage) {
        Pattern pattern = Pattern.compile("SYS_C(\\d+)");
        Matcher matcher = pattern.matcher(errorMessage);
        if (matcher.find()) {
            return matcher.group(1); // Extract the group containing the constraint number
        } else {
            return null; // Or handle the case when no match is found
        }
    }
    public static String extractColumnName(String errorMessage) {
        Pattern pattern = Pattern.compile("\\\"([^\"]+)\\\"\\)");
        Matcher matcher = pattern.matcher(errorMessage);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null; // Or handle the case when no match is found
        }
    }

        @DeleteMapping("/{bNumber}")
        public ResponseEntity<?> deleteStudent(@PathVariable("bNumber") String bNumber) {
            try {
                Student student = studentRepository.findById(bNumber);
                if (student==null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseStudent(false, "Student not found", null));
                }
                else {
                    studentRepository.deleteById(bNumber);
                    return ResponseEntity.ok().body(new ApiResponseStudent(true, "Student Deleted", student));
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseStudent(false, "Error occurred: " + e.getMessage(), null));
            }
        }
}

