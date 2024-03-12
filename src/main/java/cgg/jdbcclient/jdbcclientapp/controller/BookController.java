package cgg.jdbcclient.jdbcclientapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cgg.jdbcclient.jdbcclientapp.entity.Book;

@RestController
@RequestMapping("/books")
public class BookController {

    private JdbcClient jdbcClient;

    public BookController(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @PostMapping
    public String addNewBook(@RequestBody Book book) {
        jdbcClient.sql("INSERT INTO BOOK (ID,NAME,TITLE) VALUES(?,?,?)")
                .param(1, book.getId())
                .param(2, book.getName())
                .param(3, book.getTitle())
                .update();
        return "book added to the system";
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return jdbcClient.sql("SELECT ID,NAME,TITLE FROM BOOK")
                .query(Book.class).list();
    }

    @GetMapping("/{id}")
    public Optional<Book> getBookById(@PathVariable int id) {
        return jdbcClient.sql("SELECT ID,NAME,TITLE FROM BOOK WHERE ID=:ID")
                .param("ID", id).query(Book.class).optional();
    }

    @PutMapping("/{id}")
    public String updateBook(@PathVariable int id, @RequestBody Book book) {
        jdbcClient.sql("UPDATE BOOK SET TITLE=?,NAME=? WHERE ID=?")
                .param(1, book.getTitle())
                .param(2, book.getName())
                .param(3, id).update();
        return "book modified in system";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) {

        jdbcClient.sql("DELETE FROM BOOK WHERE ID=:ID").param("ID", id).update();
        return "book record removed !";
    }

}
