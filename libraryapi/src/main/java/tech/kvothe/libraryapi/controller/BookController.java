package tech.kvothe.libraryapi.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.kvothe.libraryapi.dto.BookDTO;
import tech.kvothe.libraryapi.mapper.BookMapper;
import tech.kvothe.libraryapi.model.Book;
import tech.kvothe.libraryapi.service.BookService;

import java.net.URI;

@RestController
@RequestMapping("books")
public class BookController implements GenericController{

    private final BookService bookService;
    private final BookMapper mapper;

    public BookController(BookService bookService, BookMapper mapper) {
        this.bookService = bookService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid BookDTO dto) {
        Book book = bookService.save(mapper.toEntity(dto));
        URI location = generateHeaderLocation(book.getId());
        return ResponseEntity.created(location).build();
    }
}
