package com.skola.rest.services;

import com.skola.rest.Entity.Reservation;
import com.skola.rest.dao.impl.ReservationDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Service
public class ReservationService {

    private final ReservationDaoImpl reservationDao;

    @Autowired
    public ReservationService(ReservationDaoImpl reservationDao) {
        this.reservationDao = reservationDao;
    }



    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationDao.findAll();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Reservation reservation = reservationDao.findById(id);
        if (reservation != null) {
            return ResponseEntity.ok(reservation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    public ResponseEntity<?> createReservation(Reservation reservation) {
        // Check if the doctor is available for the requested time slot
        if (!isDoctorAvailable(reservation.getDoctorId(), reservation.getReservationDate(),
                reservation.getStartTime(), reservation.getEndTime())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Doctor is not available at the requested time slot.");
        }

        int result = reservationDao.save(reservation);
        if (result > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Helper method to check if the doctor is available for the given time slot
    private boolean isDoctorAvailable(Long doctorId, LocalDate reservationDate, LocalTime startTime, LocalTime endTime) {
        List<Reservation> existingReservations = reservationDao.findByDoctorAndDate(doctorId, reservationDate);

        for (Reservation existingReservation : existingReservations) {
            LocalTime existingStartTime = existingReservation.getStartTime();
            LocalTime existingEndTime = existingReservation.getEndTime();

            // Check if there's an overlap in time
            if (!(endTime.isBefore(existingStartTime) || startTime.isAfter(existingEndTime))) {
                return false; // There's an overlap, doctor is not available
            }
        }

        return true; // No overlap found, doctor is available
    }
    public ResponseEntity<Reservation> updateReservation(Long id, Reservation reservation) {
        reservation.setReservationId(id);
        int result = reservationDao.update(reservation);
        if (result > 0) {
            return ResponseEntity.ok(reservation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Void> deleteReservation(Long id) {
        int result = reservationDao.deleteById(id);
        if (result > 0) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<List<Reservation>> getReservationsByDoctorAndDate(
            Long doctorId,
            String date) {
        LocalDate localDate = LocalDate.parse(date); // Parse date string into LocalDate
        List<Reservation> reservations = reservationDao.findByDoctorAndDate(doctorId, localDate);
        return ResponseEntity.ok(reservations);
    }
}





