package com.spring.springjpa.repository;

import com.spring.springjpa.Model.Classes;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcClassesRepository implements ClassesRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcClassesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Classes findById(String classid) {
        String sql = "SELECT * FROM classes WHERE classid = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{classid}, new ClassesRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    @Override
    public List<Classes> findAll() {
        String sql = "SELECT * FROM classes";
        return jdbcTemplate.query(sql, new ClassesRowMapper());
    }

    @Override
    public void save(Classes classes) {
        String sql = "INSERT INTO classes (classid, dept_code, course#, sect#, year, semester, limit, class_size, room) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, classes.getClassid(), classes.getDept_code(), classes.getCourseID(), classes.getSect(),
                classes.getYear(), classes.getSemester(), classes.getLimit(), classes.getClassSize(), classes.getRoom());
    }

    @Override
    public void deleteById(String classid) {
        String sql = "DELETE FROM classes WHERE classid = ?";
        jdbcTemplate.update(sql, classid);
    }


    @Override
    public Classes findByCompositeKey(String dept_code, int courseID, int sect, int year, String semester) {
        String sql = "SELECT * FROM classes WHERE dept_code = ? AND course# = ? AND sect# = ? AND year = ? AND semester = ?";
        Object[] params = {dept_code, courseID, sect, year, semester};
        List<Classes> result = jdbcTemplate.query(sql, params, new ClassesRowMapper());
        return result.isEmpty() ? null : result.get(0);
    }

    public class ClassesRowMapper implements RowMapper<Classes> {
        @Override
        public Classes mapRow(ResultSet rs, int rowNum) throws SQLException {
            String classid = rs.getString("classid");
            String dept_code = rs.getString("dept_code");
            int courseID = rs.getInt("course#");
            int sect = rs.getInt("sect#");
            int year = rs.getInt("year");
            String semester = rs.getString("semester");
            int limit = rs.getInt("limit");
            int classSize = rs.getInt("class_size");
            String room = rs.getString("room");

            return new Classes(classid, dept_code, courseID, sect, year, semester, limit, classSize, room);
        }
    }
}
