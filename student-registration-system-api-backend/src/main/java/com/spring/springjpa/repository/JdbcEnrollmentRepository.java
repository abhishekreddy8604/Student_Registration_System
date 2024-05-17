package com.spring.springjpa.repository;

import com.spring.springjpa.Model.Enrollment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcEnrollmentRepository implements EnrollmentRepository{
    private final JdbcTemplate jdbcTemplate;

    public JdbcEnrollmentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String enrollStudent(String bID, String classID) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName("STUDENT_MANAGEMENT_SYSTEM_PKG") // Specify the catalog name separately
                    .withProcedureName("enroll_student") // Specify the procedure name directly
                    .declareParameters(
                            new SqlOutParameter("output_param", Types.VARCHAR));

            Map<String, Object> inParams = Map.of(
                    "p_B#", bID,
                    "p_classid", classID);

            Map<String, Object> result = jdbcCall.execute(inParams);
            return null;
        }catch(UncategorizedSQLException e){
            String Message = e.getMessage().split("\n")[0];
            String[] parts = Message.split(":");
            String err_msg = parts[parts.length - 1];
            System.out.println(err_msg);
            return err_msg;
        }catch (Exception e) {
            handleException(e);
        }
        return "Error occured while enrolling student.";
    }

    //should use a procedure for this

    @Override
    public List<Enrollment> getAllEnrollments() {
        String sql = "SELECT * FROM g_enrollments";
        return jdbcTemplate.query(sql, new EnrollmentRowMapper());
    }

    @Override
    public String deleteEnrollment(String bID, String classID) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName("STUDENT_MANAGEMENT_SYSTEM_PKG") // Specify the catalog name separately
                    .withProcedureName("drop_grad_student_from_class")
                    .declareParameters(
                            new SqlOutParameter("output_param", Types.VARCHAR));

            Map<String, Object> inParams = Map.of(
                    "p_B#", bID,
                    "p_classid", classID);

            jdbcCall.execute(inParams);
            System.out.println("Student dropped from class successfully.");
            return null;
        }catch(UncategorizedSQLException e){ //why is this needed
            System.out.println(e.getMessage());
            String Message = e.getMessage().split("\n")[0];
            String[] parts = Message.split(":");
            String err_msg = parts[parts.length - 1];
            return err_msg;
        } catch (Exception e) {
            // Handle other exceptions if needed
            handleException(e);
        }
        return "Error occured while deleting student's enrollment.";
    }

    private void handleException(Exception e) {
        System.out.println("Exception occurred: " + e.getMessage());
    }
    public class EnrollmentRowMapper implements RowMapper<Enrollment> {
        @Override
        public Enrollment mapRow(ResultSet resultSet, int i) throws SQLException {
            Enrollment enrollment = new Enrollment();
            enrollment.setbID(resultSet.getString("G_B#"));
            enrollment.setClassID(resultSet.getString("classID"));
            enrollment.setScore(resultSet.getInt("score"));
            return enrollment;
        }
    }
}
