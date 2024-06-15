package com.skola.rest.dao;

import com.skola.rest.Entity.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDao {
    List<Reservation> findAll();
    Reservation findById(Long id);
    List<Reservation> findByDoctorAndDate(Long doctorId, LocalDate date);
    int save(Reservation reservation);
    int update(Reservation reservation);
    int deleteById(Long id);
}
