package com.skola.rest.dao.impl;

import com.skola.rest.dao.DoctorDao;
import com.skola.rest.Entity.Doctor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DoctorDaoImpl implements DoctorDao {

    private final JdbcTemplate jdbcTemplate;

    public DoctorDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Doctor> rowMapper = new RowMapper<Doctor>() {
        @Override
        public Doctor mapRow(ResultSet rs, int rowNum) throws SQLException {
            Doctor doctor = new Doctor();
            doctor.setDoctorId(rs.getLong("doctor_id"));
            doctor.setFirstName(rs.getString("first_name"));
            doctor.setLastName(rs.getString("last_name"));
            doctor.setSpecialty(rs.getString("specialty"));
            doctor.setPhoneNumber(rs.getString("phone_number"));
            doctor.setEmail(rs.getString("email"));
            return doctor;
        }
    };

    @Override
    public List<Doctor> findAll() {
        String sql = "SELECT * FROM doctor";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Doctor findById(Long id) {
        String sql = "SELECT * FROM doctor WHERE doctor_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
    }

    @Override
    public int save(Doctor doctor) {
        String sql = "INSERT INTO doctor (first_name, last_name, specialty, phone_number, email) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, doctor.getFirstName(), doctor.getLastName(), doctor.getSpecialty(), doctor.getPhoneNumber(), doctor.getEmail());
    }

    @Override
    public int update(Doctor doctor) {
        String sql = "UPDATE doctor SET first_name = ?, last_name = ?, specialty = ?, phone_number = ?, email = ? WHERE doctor_id = ?";
        return jdbcTemplate.update(sql, doctor.getFirstName(), doctor.getLastName(), doctor.getSpecialty(), doctor.getPhoneNumber(), doctor.getEmail(), doctor.getDoctorId());
    }

    @Override
    public int deleteById(Long id) {
        String sql = "DELETE FROM doctor WHERE doctor_id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
