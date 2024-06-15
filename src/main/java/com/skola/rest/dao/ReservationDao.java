package com.skola.rest.dao;

import com.skola.rest.Entity.Reservation;
import java.util.List;

public interface ReservationDao {
    List<Reservation> findAll();
    Reservation findById(Long id);
    int save(Reservation reservation);
    int update(Reservation reservation);
    int deleteById(Long id);
}
