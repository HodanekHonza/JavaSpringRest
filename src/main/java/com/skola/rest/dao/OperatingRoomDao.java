package com.skola.rest.dao;

import com.skola.rest.Entity.OperatingRoom;

import java.util.List;

public interface OperatingRoomDao {
    List<OperatingRoom> findAll();
    OperatingRoom findById(Long id);
    int save(OperatingRoom operatingRoom);
    int update(OperatingRoom operatingRoom);
    int deleteById(Long id);
}
