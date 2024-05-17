package com.spring.springjpa.controller;

import com.spring.springjpa.Model.Classes;
import com.spring.springjpa.Model.Student;
import com.spring.springjpa.repository.ClassesRepository;
import com.spring.springjpa.utilities.ApiResponseClass;
import com.spring.springjpa.utilities.ApiResponseClasses;
import com.spring.springjpa.utilities.ApiResponseStudent;
import com.spring.springjpa.utilities.ApiResponseStudents;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Year;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/classes")
public class ClassesController {

    private final ClassesRepository classesRepository;

    public ClassesController(ClassesRepository classesRepository) {
        this.classesRepository = classesRepository;
    }

    // @GetMapping("/{classid}")
    // public ResponseEntity<?> getClassById(@PathVariable String classid) {
    //     try {
    //         Classes classes = classesRepository.findById(classid.toLowerCase());
    //         if (classes==null) {
    //             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseStudent(false, "Class not found", null));
    //         }
    //         return ResponseEntity.ok().body(new ApiResponseClass(true, "Class found", classes));
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseClass(false, e.getMessage(), null));
    //     }
    // }

    @GetMapping("/")
    public ResponseEntity<?> getAllClasses() {
        List<Classes> classes = classesRepository.findAll();
        return ResponseEntity.ok().body(new ApiResponseClasses(true, "Classes found", classes));
    }

    @PostMapping("/")
    public ResponseEntity<?> createClass(@RequestBody Classes classes) {

        // Check if classid is not null and unique
        if (classes.getClassid() == null || classesRepository.findById(classes.getClassid()) != null) {
            return ResponseEntity.badRequest().body(new ApiResponseClass(false, "Error: classid should be unique and not null", null));
        }
        if(classes.getDept_code()==null){
            return ResponseEntity.badRequest().body(new ApiResponseClass(false, "Error: Dept_code should be not null", null));

        }
        if(classes.getCourseID()==0){
            return ResponseEntity.badRequest().bo  dy(new ApiResponseClass(false, "Error: CourseID should be not null", null));

        }

        // Check if classid starts with 'c%'
        if (!classes.getClassid().startsWith("c")) {
            return ResponseEntity.badRequest().body(new ApiResponseClass(false, "Error: classid should start with 'c'", null));
        }

        // Check if semester is valid
        List<String> validSemesters = Arrays.asList("Spring", "Fall", "Summer 1", "Summer 2", "Winter");
        if (!validSemesters.contains(classes.getSemester())) {
            return ResponseEntity.badRequest().body(new ApiResponseClass(false, "Error: Invalid semester", null));
        }

        // Check if class_size <= limit
        if (classes.getClassSize() > classes.getLimit()) {
            return ResponseEntity.badRequest().body(new ApiResponseClass(false,"Error: class_size should be less than or equal to limit" , null));
        }

        // Check if class_size conditions are met
        if (!((classes.getClassSize() >= 6 && classes.getCourseID() >= 500) || classes.getClassSize() >= 10)) {
            return ResponseEntity.badRequest().body(new ApiResponseClass(false,"Error: class_size conditions not met" , null));
        }

        // Check if dept_code, course#, sect#, year, semester are unique as a whole
        Classes existingClass = classesRepository.findByCompositeKey(classes.getDept_code(), classes.getCourseID(), classes.getSect(), classes.getYear(), classes.getSemester());
        if (existingClass != null) {
            return ResponseEntity.badRequest().body(new ApiResponseClass(false, "Error: Class with the same dept_code, course#, sect#, year, and semester already exists", null));
        }
        // Check if dept_code is not an integer
        try {
            Integer.parseInt(classes.getDept_code());
            return ResponseEntity.badRequest().body(new ApiResponseClass(false, "Error: dept_code should not be an integer.", null));
        } catch (NumberFormatException e) {
            // dept_code is not an integer, continue
        }

        // Check if year is a valid year
        int currentYear = Year.now().getValue();
        if (classes.getYear() < currentYear - 10 || classes.getYear() > currentYear + 10) {
            return ResponseEntity.badRequest().body(new ApiResponseClass(false, "Error: Invalid year.", null));
        }
        try{
            classesRepository.save(classes);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponseClass(true, "Class created successfully.", classes));
        }catch(DataIntegrityViolationException e){

            Throwable rootCause = e.getRootCause();
            System.out.println(e.getMostSpecificCause().getClass());
            if (rootCause instanceof oracle.jdbc.OracleDatabaseException) {
                oracle.jdbc.OracleDatabaseException ex = (oracle.jdbc.OracleDatabaseException) rootCause;
                int errorCode = ex.getOracleErrorNumber();
                if (errorCode == 2291) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseClass(false, "Error: Integrity constraint violation: Parent key not found" , null));
                }
            }

        }

        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseClass(false, e.getMessage(), null));
        }
        return null;
    }

    @DeleteMapping("/{classid}")
    public ResponseEntity<?> deleteClass(@PathVariable String classid) {
        try {
            Classes existingClass = classesRepository.findById(classid.toLowerCase());
            if (existingClass == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseStudent(false, "Class not found", null));
            }
            else {
                classesRepository.deleteById(classid.toLowerCase());
                return ResponseEntity.ok().body(new ApiResponseClass(true, "Class Deleted", existingClass));
            }
        } catch (DataIntegrityViolationException e) {
            // Check if the error is due to a foreign key constraint violation
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                SQLIntegrityConstraintViolationException sqlException = (SQLIntegrityConstraintViolationException) e.getCause();
                if(sqlException.getErrorCode() ==2292) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseClass(false, "Error: Cannot delete class because dependent records exist." , null));
                }
                else{
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseClass(false, "Error: Cannot delete class because integrity constraints violated." + sqlException.getErrorCode(), null));
                }
            }
            // Other data integrity violation errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseClass(false, "Error occurred: " + e.getMessage(), null));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseClass(false, e.getMessage(), null));
        }
    }
}

