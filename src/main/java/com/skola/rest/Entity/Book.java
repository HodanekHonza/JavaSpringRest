package com.skola.rest.Entity;

import java.util.UUID;

public class Book {
    private String nameOfBook;
    private UUID uuid;
    private boolean isTaken;
    private UUID takenByUserWithId;

    public Book() {
    }

    public Book(String nameOfBook) {
        this.nameOfBook = nameOfBook;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public UUID getTakenByUserWithId() {
        return takenByUserWithId;
    }

    public void setTakenByUserWithId(UUID takenByUserWithId) {
        this.takenByUserWithId = takenByUserWithId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getNameOfBook() {
        return this.nameOfBook;
    }

    public void setNameOfBook(String name) {
        this.nameOfBook = name;
    }
}
