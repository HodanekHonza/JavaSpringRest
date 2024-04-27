package com.skola.rest.controller;

import com.skola.rest.Database;
import com.skola.rest.Entity.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/book")
public class BookController {
    @PostMapping()
    public ResponseEntity<String> createBook(@RequestBody Book book) {
        UUID uuid = UUID.randomUUID();
        for (Map.Entry<UUID, Book> entry : Database.bookHashMap.entrySet()) {
            Book bookValue = entry.getValue();
            if (bookValue != null && bookValue.getNameOfBook().equals(book.getNameOfBook())) {
                return new ResponseEntity<>("BOOK ALREADY CREATED", HttpStatus.NOT_FOUND);
            }
        }
        book.setUuid(uuid);
        Database.bookHashMap.put(uuid, book);
        System.out.println(book);
        return new ResponseEntity<>("SUCCESSFUL CREATE - name of book: " + book.getNameOfBook(), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<String> getUser(@RequestParam(value = "name") String name) {
        for (Map.Entry<UUID, Book> entry : Database.bookHashMap.entrySet()) {
            Book book = entry.getValue();
            if (book != null && book.getNameOfBook().equals(name)) {
                return new ResponseEntity<>("Book Name: " + book.getNameOfBook() + " Is book taken? " + book.isTaken() + " Book is taken by user with id: " + book.getTakenByUserWithId(), HttpStatus.OK);

            }
        }
        return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
    }

    @PutMapping()
    public ResponseEntity<String> updateBook(@RequestParam(value = "name") String name,
                                             @RequestParam(value = "isTaken") boolean isTaken,
                                             @RequestParam(value = "isTakenByUserId") UUID isTakenByUserId) {
        for (Map.Entry<UUID, Book> entry : Database.bookHashMap.entrySet()) {
            Book book = entry.getValue();
            if (book != null && book.getNameOfBook().equals(name) && book.getTakenByUserWithId() == null) {
                book.setTaken(isTaken);
                book.setTakenByUserWithId(isTakenByUserId);
                System.out.println("Updated book, user with ID: " + book.getTakenByUserWithId());
                return new ResponseEntity<>("Book updated successfully", HttpStatus.OK);
            } else if (book != null && book.getTakenByUserWithId().equals(isTakenByUserId)) {
                book.setTaken(isTaken);
                book.setTakenByUserWithId(isTakenByUserId);
                System.out.println("Updated book status, taken by the same user with ID: " + book.getTakenByUserWithId());
                return new ResponseEntity<>("Book status updated successfully", HttpStatus.OK);
            } else if (book != null && !book.isTaken() && book.getTakenByUserWithId() != null && !book.getTakenByUserWithId().equals(isTakenByUserId)) {
                book.setTaken(isTaken);
                book.setTakenByUserWithId(isTakenByUserId);
                return new ResponseEntity<>("Book reserved to new owner: " + isTakenByUserId, HttpStatus.OK);
            } else if (book != null && book.isTaken() && book.getTakenByUserWithId() != null) {
                return new ResponseEntity<>("Book is now taken, cant reserve ATM", HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteBook(@RequestParam(value = "name") String name) {
        for (Map.Entry<UUID, Book> entry : Database.bookHashMap.entrySet()) {
            Book book = entry.getValue();
            if (name.equals(book.getNameOfBook())) {
                Database.bookHashMap.remove(book.getUuid());
                System.out.println(Database.bookHashMap);
                return new ResponseEntity<>("Book successfully deleted", HttpStatus.OK);

            }
        }
        return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
    }

}
