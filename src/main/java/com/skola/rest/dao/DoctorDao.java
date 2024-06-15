package com.skola.rest.dao;
import com.skola.rest.Entity.Doctor;
import java.util.List;

public interface DoctorDao {
    List<Doctor> findAll();
    Doctor findById(Long id);
    int save(Doctor doctor);
    int update(Doctor doctor);
    int deleteById(Long id);
}
