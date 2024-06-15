package com.skola.rest.dao.impl;

import com.skola.rest.dao.PatientDao;
import com.skola.rest.Entity.Patient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class PatientDaoImpl implements PatientDao {

    private final JdbcTemplate jdbcTemplate;

    public PatientDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Patient patient) {
        return jdbcTemplate.update(
                "INSERT INTO patient (first_name, last_name) VALUES (?, ?)",
                patient.getFirstName(), patient.getLastName()
        );
    }

    @Override
    public Patient findById(Long patientId) {
        List<Patient> results = jdbcTemplate.query(
                "SELECT patient_id, first_name, last_name FROM patient WHERE patient_id = ? LIMIT 1",
                new PatientRowMapper(), patientId);

        return results.isEmpty() ? null : results.get(0);
    }

    public Patient findByName(String patientName) {
        List<Patient> results = jdbcTemplate.query(
                "SELECT patient_id, first_name, last_name FROM patient WHERE last_name = ? LIMIT 1",
                new PatientRowMapper(), patientName);

        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public List<Patient> findAll() {
        return jdbcTemplate.query(
                "SELECT patient_id, first_name, last_name FROM patient",
                new PatientRowMapper()
        );
    }

    @Override
    public int update(Patient patient) {
        return jdbcTemplate.update(
                "UPDATE patient SET first_name = ?, last_name = ? WHERE patient_id = ?",
                patient.getFirstName(), patient.getLastName(), patient.getPatientId()
        );
    }

    @Override
    public int deleteById(Long patientId) {
        return jdbcTemplate.update(
                "DELETE FROM patient WHERE patient_id = ?",
                patientId
        );
    }

    public static class PatientRowMapper implements RowMapper<Patient> {
        @Override
        public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Patient.builder()
                    .patientId(rs.getLong("patient_id"))
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .build();
        }
    }
}
