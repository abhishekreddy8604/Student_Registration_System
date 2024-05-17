package com.spring.springjpa.repository;

import com.spring.springjpa.Model.Courses;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcCoursesRepository implements CoursesRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcCoursesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Courses findById(String dept_code, int courseID ) {
        String sql = "SELECT * FROM courses WHERE dept_code = ? AND course# = ?";
        List<Courses> courses = jdbcTemplate.query(sql, new Object[]{dept_code, courseID}, new CoursesRowMapper());
        return courses.isEmpty() ? null : courses.get(0);
    }


    @Override
    public List<Courses> findAll() {
        String sql = "SELECT * FROM courses";
        return jdbcTemplate.query(sql, new CoursesRowMapper());
    }

    @Override
    public void save(Courses course) {
        String sql = "INSERT INTO courses (dept_code, title, course#) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, course.getDept_code().toUpperCase(), course.getTitle(), course.getCourseID());
    }

    @Override
    public void deleteById(String dept_code, int courseID ) {
        String sql = "DELETE FROM courses WHERE dept_code = ? AND course# = ?";
        jdbcTemplate.update(sql,dept_code, courseID);
    }

    private static class CoursesRowMapper implements RowMapper<Courses> {
        @Override
        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
            String dept_code = resultSet.getString("dept_code");
            int courseID = resultSet.getInt("course#");
            String title = resultSet.getString("title");
            return new Courses(dept_code, title, courseID);
        }
    }
}
