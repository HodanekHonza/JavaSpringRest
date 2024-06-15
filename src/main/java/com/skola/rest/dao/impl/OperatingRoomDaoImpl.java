package com.skola.rest.dao.impl;

import com.skola.rest.dao.OperatingRoomDao;
import com.skola.rest.Entity.OperatingRoom;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class OperatingRoomDaoImpl implements OperatingRoomDao {

    private final JdbcTemplate jdbcTemplate;

    public OperatingRoomDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(OperatingRoom room) {
        return jdbcTemplate.update(
                "INSERT INTO operating_hall (hall_name, location) VALUES (?, ?)",
                room.getHallName(), room.getLocation()
        );
    }

    @Override
    public OperatingRoom findById(Long hallId) {
        List<OperatingRoom> results = jdbcTemplate.query(
                "SELECT hall_id, hall_name, location FROM operating_hall WHERE hall_id = ? LIMIT 1",
                new OperatingRoomRowMapper(), hallId);

        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public List<OperatingRoom> findAll() {
        return jdbcTemplate.query(
                "SELECT hall_id, hall_name, location FROM operating_hall",
                new OperatingRoomRowMapper()
        );
    }

    @Override
    public int update(OperatingRoom room) {
        return jdbcTemplate.update(
                "UPDATE operating_hall SET hall_name = ?, location = ? WHERE hall_id = ?",
                room.getHallName(), room.getLocation(), room.getHallId()
        );
    }

    @Override
    public int deleteById(Long hallId) {
        return jdbcTemplate.update(
                "DELETE FROM operating_hall WHERE hall_id = ?",
                hallId
        );
    }

    public static class OperatingRoomRowMapper implements RowMapper<OperatingRoom> {
        @Override
        public OperatingRoom mapRow(ResultSet rs, int rowNum) throws SQLException {
            return OperatingRoom.builder()
                    .hallId(rs.getLong("hall_id"))
                    .hallName(rs.getString("hall_name"))
                    .location(rs.getString("location"))
                    .build();
        }
    }
}
