package com.skola.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
@Log
public class RestApplication  {
    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }
}
