package tech.kvothe.libraryapi.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.kvothe.libraryapi.dto.BookDTO;
import tech.kvothe.libraryapi.dto.BookResponseDTO;
import tech.kvothe.libraryapi.mapper.BookMapper;
import tech.kvothe.libraryapi.model.Book;
import tech.kvothe.libraryapi.service.BookService;

import java.net.URI;
import java.util.UUID;

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
    public ResponseEntity<Void> save(@RequestBody @Valid BookDTO dto) {
        Book book = bookService.save(mapper.toEntity(dto));
        URI location = generateHeaderLocation(book.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<BookResponseDTO> getDetails(@PathVariable("id") String id) {
        return bookService.getById(UUID.fromString(id))
                .map(book -> {
                    BookResponseDTO dto = mapper.toDto(book);
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() ->  ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        return bookService.getById(UUID.fromString(id))
                .map(book -> {
                    bookService.delete(book);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());

    }
}
