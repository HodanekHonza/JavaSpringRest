package com.skola.rest.controller;

import com.skola.rest.dao.impl.OperatingRoomDaoImpl;
import com.skola.rest.Entity.OperatingRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class OperatingRoomController {

    private final OperatingRoomDaoImpl roomDao;

    @Autowired
    public OperatingRoomController(OperatingRoomDaoImpl roomDao) {
        this.roomDao = roomDao;
    }

    @GetMapping("test")
    public String test() {
        List<OperatingRoom> rooms = roomDao.findAll();
        return "ok";
    }
    @GetMapping
    public ResponseEntity<List<OperatingRoom>> getAllRooms() {
        List<OperatingRoom> rooms = roomDao.findAll();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperatingRoom> getRoomById(@PathVariable Long id) {
        OperatingRoom room = roomDao.findById(id);
        if (room != null) {
            return ResponseEntity.ok(room);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<OperatingRoom> createRoom(@RequestBody OperatingRoom room) {
        int result = roomDao.save(room);
        if (result > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(room);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OperatingRoom> updateRoom(@PathVariable Long id, @RequestBody OperatingRoom room) {
        room.setHallId(id); // Ensure the ID is set for update
        int result = roomDao.update(room);
        if (result > 0) {
            return ResponseEntity.ok(room);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        int result = roomDao.deleteById(id);
        if (result > 0) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
