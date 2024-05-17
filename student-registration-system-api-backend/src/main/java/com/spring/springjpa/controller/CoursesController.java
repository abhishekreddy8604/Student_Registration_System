package com.spring.springjpa.controller;

import com.spring.springjpa.Model.Courses;
import com.spring.springjpa.Model.Student;
import com.spring.springjpa.repository.CoursesRepository;
import com.spring.springjpa.utilities.ApiResponseCourse;
import com.spring.springjpa.utilities.ApiResponseCourses;
import com.spring.springjpa.utilities.ApiResponseStudent;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CoursesController {
    private final CoursesRepository coursesRepository;

    public CoursesController(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    // @GetMapping("/{dept_code}/{courseID}")
    // public ResponseEntity<?> getCourseById(@PathVariable("dept_code") String dept_code, @PathVariable("courseID") int courseID) {
    //     Courses course = coursesRepository.findById(dept_code.toUpperCase(), courseID);
    //     if (course != null) {
    //         return ResponseEntity.ok().body(new ApiResponseCourse(true, "Course found", course));
    //     } else {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseCourse(false, "Course not found", null));
    //     }
    // }

    @GetMapping("/")
    public ResponseEntity<?> getAllCourses() {
        List<Courses> courses = coursesRepository.findAll();
        return ResponseEntity.ok().body(new ApiResponseCourses(true, "Courses found", courses));
    }

    @PostMapping("/")
    public ResponseEntity<?> createCourse(@RequestBody Courses course) {
        if (course.getDept_code() == null) {
            return ResponseEntity.badRequest().body(new ApiResponseCourse(false, "Error: dept_code must be provided", null));
        }
        if (course.getTitle() == null) {
            return ResponseEntity.badRequest().body(new ApiResponseCourse(false, "Error: Title must be provided", null));
        }
        if (course.getCourseID() == 0) {
            return ResponseEntity.badRequest().body(new ApiResponseCourse(false, "Error: courseID must be provided", null));
        }
        Courses existingCourse = coursesRepository.findById(course.getDept_code(), course.getCourseID());
        if (existingCourse != null) {
            return ResponseEntity.badRequest().body(new ApiResponseCourse(false, "Error: Course with the same dept_code and courseID already exists", null));
        }
        if(course.getCourseID()<100 || course.getCourseID()>799){
            return ResponseEntity.badRequest().body(new ApiResponseCourse(false, "Error: courseID should be between 100 and 799", null));
        }
        if (course.getDept_code().matches(".*\\d.*")) {
            return ResponseEntity.badRequest().body(new ApiResponseCourse(false, "Error: dept_code cannot contain numeric values", null));
        }

        if (course.getTitle().matches(".*\\d.*")) {
            return ResponseEntity.badRequest().body(new ApiResponseCourse(false, "Error: title cannot contain numeric values", null));
        }
        try {
            coursesRepository.save(course);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseCourse(true, "Course created successfully", course));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseCourse(false, "Error occurred", null));
        }
    }

    @DeleteMapping("/{dept_code}/{courseID}")
    public ResponseEntity<?> deleteCourse(@PathVariable("dept_code") String dept_code, @PathVariable("courseID") int courseID) {
        try {
            Courses course = coursesRepository.findById(dept_code, courseID);
            if (course==null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseStudent(false, "Course not found", null));
            }
            else {
                coursesRepository.deleteById(dept_code, courseID);
                return ResponseEntity.ok().body(new ApiResponseCourse(true, "Course deleted successfully", course));
            }
        } catch (DataIntegrityViolationException e) {
            // Check if the error is due to a foreign key constraint violation
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                SQLIntegrityConstraintViolationException sqlException = (SQLIntegrityConstraintViolationException) e.getCause();
                if(sqlException.getErrorCode() ==2292) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseCourse(false, "Error: Cannot delete course because dependent records exist." , null));
                }
                else{
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseCourse(false, "Error: Cannot delete course because integrity constraints violated." + sqlException.getErrorCode(), null));
                }
            }
            // Other data integrity violation errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseCourse(false, "Error occurred: " + e.getMessage(), null));
        }
        catch (Exception e) {
            System.out.println(e.getClass());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseCourse(false, e.getMessage(), null));
        }
    }
}
