package com.skola.rest;

import java.util.HashMap;
import java.util.UUID;

import com.skola.rest.Entity.User;
import com.skola.rest.Entity.Book;

public class Database {
    public static HashMap<UUID, User> userHashMap = new HashMap<>();
    public static HashMap<UUID, Book> bookHashMap = new HashMap<>();
}
