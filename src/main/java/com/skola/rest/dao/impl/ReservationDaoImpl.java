package com.skola.rest.dao.impl;

import com.skola.rest.dao.ReservationDao;
import com.skola.rest.Entity.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class ReservationDaoImpl implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Reservation reservation) {
        return jdbcTemplate.update(
                "INSERT INTO reservation (doctor_id, patient_id, hall_id, reservation_date, " +
                        "start_time, end_time, description, status) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                reservation.getDoctorId(), reservation.getPatientId(), reservation.getHallId(),
                reservation.getReservationDate(), reservation.getStartTime(), reservation.getEndTime(),
                reservation.getDescription(), reservation.getStatus()
        );
    }

    @Override
    public Reservation findById(Long reservationId) {
        List<Reservation> results = jdbcTemplate.query(
                "SELECT reservation_id, doctor_id, patient_id, hall_id, reservation_date, " +
                        "start_time, end_time, description, status " +
                        "FROM reservation WHERE reservation_id = ? LIMIT 1",
                new ReservationRowMapper(), reservationId);

        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "SELECT reservation_id, doctor_id, patient_id, hall_id, reservation_date, " +
                        "start_time, end_time, description, status FROM reservation",
                new ReservationRowMapper()
        );
    }

    @Override
    public List<Reservation> findByDoctorAndDate(Long doctorId, LocalDate date) {
        return jdbcTemplate.query(
                "SELECT reservation_id, doctor_id, patient_id, hall_id, reservation_date, " +
                        "start_time, end_time, description, status " +
                        "FROM reservation WHERE doctor_id = ? AND reservation_date = ?",
                new ReservationRowMapper(), doctorId, date
        );
    }

    @Override
    public int update(Reservation reservation) {
        return jdbcTemplate.update(
                "UPDATE reservation SET doctor_id = ?, patient_id = ?, hall_id = ?, " +
                        "reservation_date = ?, start_time = ?, end_time = ?, " +
                        "description = ?, status = ? WHERE reservation_id = ?",
                reservation.getDoctorId(), reservation.getPatientId(), reservation.getHallId(),
                reservation.getReservationDate(), reservation.getStartTime(), reservation.getEndTime(),
                reservation.getDescription(), reservation.getStatus(), reservation.getReservationId()
        );
    }

    @Override
    public int deleteById(Long reservationId) {
        return jdbcTemplate.update(
                "DELETE FROM reservation WHERE reservation_id = ?",
                reservationId
        );
    }

    public static class ReservationRowMapper implements RowMapper<Reservation> {
        @Override
        public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Reservation.builder()
                    .reservationId(rs.getLong("reservation_id"))
                    .doctorId(rs.getLong("doctor_id"))
                    .patientId(rs.getLong("patient_id"))
                    .hallId(rs.getLong("hall_id"))
                    .reservationDate(rs.getDate("reservation_date").toLocalDate())
                    .startTime(rs.getTime("start_time").toLocalTime())
                    .endTime(rs.getTime("end_time").toLocalTime())
                    .description(rs.getString("description"))
                    .status(rs.getString("status"))
                    .build();
        }
    }
}
