package com.skola.rest.dao;

import com.skola.rest.Entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationDao {
    List<Reservation> findAll();
    Reservation findById(Long id);
    int save(Reservation reservation);
    List<Reservation> findByDoctorAndDate(Long doctorId, LocalDate date);
    int update(Reservation reservation);
    int deleteById(Long id);
}
