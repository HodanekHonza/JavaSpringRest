package com.skola.rest.controller;

import com.skola.rest.dao.impl.ReservationDaoImpl;
import com.skola.rest.Entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationDaoImpl reservationDao;

    @Autowired
    public ReservationController(ReservationDaoImpl reservationDao) {
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

//    @PostMapping
//    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
//        int result = reservationDao.save(reservation);
//        if (result > 0) {
//            return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
@PostMapping
public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
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
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        reservation.setReservationId(id); // Ensure the ID is set for update
        int result = reservationDao.update(reservation);
        if (result > 0) {
            return ResponseEntity.ok(reservation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        int result = reservationDao.deleteById(id);
        if (result > 0) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/doctor")
    public ResponseEntity<List<Reservation>> getReservationsByDoctorAndDate(
            @RequestParam Long doctorId,
            @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date); // Parse date string into LocalDate
        List<Reservation> reservations = reservationDao.findByDoctorAndDate(doctorId, localDate);
        return ResponseEntity.ok(reservations);
    }
}
