package com.skola.rest.dao;

import com.skola.rest.Entity.Patient;
import java.util.List;

public interface PatientDao {
    List<Patient> findAll();
    Patient findById(Long id);
    int save(Patient patient);
    int update(Patient patient);
    int deleteById(Long id);
}
