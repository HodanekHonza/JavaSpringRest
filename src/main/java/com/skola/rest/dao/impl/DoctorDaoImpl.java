package com.skola.rest.dao.impl;

import com.skola.rest.dao.DoctorDao;
import com.skola.rest.Entity.Doctor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class DoctorDaoImpl implements DoctorDao {

    private final JdbcTemplate jdbcTemplate;

    public DoctorDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Doctor doctor) {
        return jdbcTemplate.update(
                "INSERT INTO doctor (first_name, last_name, specialty, phone_number, email) " +
                        "VALUES (?, ?, ?, ?, ?)",
                doctor.getFirstName(), doctor.getLastName(), doctor.getSpecialty(),
                doctor.getPhoneNumber(), doctor.getEmail()
        );
    }

    @Override
    public Doctor findById(Long doctorId) {
        List<Doctor> results = jdbcTemplate.query(
                "SELECT doctor_id, first_name, last_name, specialty, phone_number, email " +
                        "FROM doctor WHERE doctor_id = ? LIMIT 1",
                new DoctorRowMapper(), doctorId);

        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public List<Doctor> findAll() {
        return jdbcTemplate.query(
                "SELECT doctor_id, first_name, last_name, specialty, phone_number, email " +
                        "FROM doctor",
                new DoctorRowMapper()
        );
    }

    @Override
    public int update(Doctor doctor) {
        return jdbcTemplate.update(
                "UPDATE doctor SET first_name = ?, last_name = ?, specialty = ?, " +
                        "phone_number = ?, email = ? WHERE doctor_id = ?",
                doctor.getFirstName(), doctor.getLastName(), doctor.getSpecialty(),
                doctor.getPhoneNumber(), doctor.getEmail(), doctor.getDoctorId()
        );
    }

    @Override
    public int deleteById(Long doctorId) {
        return jdbcTemplate.update(
                "DELETE FROM doctor WHERE doctor_id = ?",
                doctorId
        );
    }

    public static class DoctorRowMapper implements RowMapper<Doctor> {
        @Override
        public Doctor mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Doctor.builder()
                    .doctorId(rs.getLong("doctor_id"))
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .specialty(rs.getString("specialty"))
                    .phoneNumber(rs.getString("phone_number"))
                    .email(rs.getString("email"))
                    .build();
        }
    }
}
