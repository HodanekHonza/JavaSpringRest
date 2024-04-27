package com.skola.rest.Entity;

import java.util.UUID;

public class User {
    private String email;
    private String password;
    private UUID uuid;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

