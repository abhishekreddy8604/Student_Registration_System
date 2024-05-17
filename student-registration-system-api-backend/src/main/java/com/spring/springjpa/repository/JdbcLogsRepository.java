package com.spring.springjpa.repository;

import com.spring.springjpa.Model.Logs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcLogsRepository implements LogsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    public Logs findById(int id) {
//        String sql = "SELECT * FROM logs WHERE LOG# = ?";
//        return jdbcTemplate.queryForObject(sql, new LogsRowMapper(), id);
//    }

    //should use a procedure for this
    
    public List<Logs> findAll() {
        String sql = "SELECT * FROM logs";
        return jdbcTemplate.query(sql, new LogsRowMapper());
    }

    private class LogsRowMapper implements RowMapper<Logs> {
        @Override
        public Logs mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getShort("LOG#");
            String userName = rs.getString("user_name");
            Date opTime = rs.getDate("op_time");
            String tableName = rs.getString("table_name");
            String operation = rs.getString("operation");
            String tupleKeyvalue = rs.getString("tuple_keyvalue");

            return new Logs(id, userName, opTime, tableName, operation, tupleKeyvalue);
        }
    }
}
