package com.spring.springjpa.repository;

import com.spring.springjpa.Model.Student;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;

@Repository
public class JdbcStudentRepository implements StudentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query("SELECT * FROM students", new StudentRowMapper());
    }

    @Override
    public Student findById(String BID) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM students WHERE B# = ?", new StudentRowMapper(), BID);
        }catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Student findByEmail(String email) {
        String query = "SELECT * FROM students WHERE email = ?";
        List<Student> students = jdbcTemplate.query(query, new Object[]{email}, new StudentRowMapper());
        if (students.isEmpty()) {
            return null; // or throw an exception if needed
        } else {
            return students.get(0);
        }
    }

    @Override
    public void save(Student student) {
        jdbcTemplate.update("INSERT INTO students (B#, first_name, last_name, st_level, gpa, email, bdate) VALUES (?, ?, ?, ?, ?, ?, ?)",
                student.getbNumber(), student.getFirstName(), student.getLastName(), student.getStudentLevel(), student.getGpa(), student.getEmail(), student.getBirthDate());
    }


    @Override
    public void deleteById(String bNumber) {
        try {
            String sql = "DELETE FROM students WHERE B# = ?";
            jdbcTemplate.update(sql, bNumber);
        }
        catch(BadSqlGrammarException ex){
            System.out.println(ex.getSql());
            System.out.println(ex.getMostSpecificCause());
            ex.printStackTrace();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    //need explannation

    private static class StudentRowMapper implements RowMapper<Student> {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setbNumber(rs.getString("B#"));
            student.setFirstName(rs.getString("first_name"));
            student.setLastName(rs.getString("last_name"));
            student.setStudentLevel(rs.getString("st_level"));
            student.setGpa(rs.getDouble("gpa"));
            student.setEmail(rs.getString("email"));
            Date birthDate = rs.getDate("bdate");
            if (birthDate != null) {
                try {
                    student.setBirthDate(String.valueOf(birthDate));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
            return student;
        }
    }
}
