package tech.kvothe.libraryapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.kvothe.libraryapi.dto.BookDTO;
import tech.kvothe.libraryapi.dto.BookResponseDTO;
import tech.kvothe.libraryapi.mapper.BookMapper;
import tech.kvothe.libraryapi.model.Book;
import tech.kvothe.libraryapi.model.BookGenre;
import tech.kvothe.libraryapi.service.BookService;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("books")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@Tag(name = "Books")
public class BookController implements GenericController{

    private final BookService bookService;
    private final BookMapper mapper;

    public BookController(BookService bookService, BookMapper mapper) {
        this.bookService = bookService;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Save", description = "Save new book")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "409", description = "Book already exists"),

    })
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

    @GetMapping
    public ResponseEntity<Page<BookResponseDTO>> search(@RequestParam(value = "isbn", required = false) String isbn,
                                                        @RequestParam(value = "title", required = false) String title,
                                                        @RequestParam(value = "author-name", required = false) String authorName,
                                                        @RequestParam(value = "genre", required = false) BookGenre genre,
                                                        @RequestParam(value = "publication-year", required = false) Integer publicationYear,
                                                        @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                        @RequestParam(value = "page-size", defaultValue = "10") Integer pageSize) {

        var search = bookService.search(isbn, title, authorName, genre, publicationYear, page, pageSize);
        Page<BookResponseDTO> result = search.map(mapper::toDto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id,
                                    @RequestBody @Valid BookDTO dto) {

        return bookService.getById(UUID.fromString(id))
                .map(book -> {
                    Book bookAux = mapper.toEntity(dto);
                    book.setPublicationDate(bookAux.getPublicationDate());
                    book.setIsbn(bookAux.getIsbn());
                    book.setPrice(bookAux.getPrice());
                    book.setGenre(bookAux.getGenre());
                    book.setTitle(bookAux.getTitle());
                    book.setAuthor(bookAux.getAuthor());
                    bookService.update(book);

                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
