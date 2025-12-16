package tech.kvothe.libraryapi.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.kvothe.libraryapi.dto.AuthorDTO;
import tech.kvothe.libraryapi.dto.AuthorResponseDTO;
import tech.kvothe.libraryapi.mapper.AuthorMapper;
import tech.kvothe.libraryapi.model.Author;
import tech.kvothe.libraryapi.service.AuthorService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("authors")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class AuthorController implements GenericController{

    private final AuthorService authorService;
    private final AuthorMapper mapper;

    public AuthorController(AuthorService authorService, AuthorMapper mapper) {
        this.authorService = authorService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid AuthorDTO request) {
        Author author = mapper.toEntity(request);
        authorService.save(author);

        URI location = generateHeaderLocation(author.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AuthorResponseDTO> getDetails(@PathVariable String id) {
        return authorService.getById(id)
                .map(author -> {
                    AuthorResponseDTO dto = mapper.toDto(author);
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() ->  ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<AuthorResponseDTO> delete(@PathVariable String id) {
        var authorOptional = authorService.getById(id);

        if (authorOptional.isPresent()) {
            authorService.delete(authorOptional.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> search(@RequestParam(value = "name", required = false) String name,
                                                          @RequestParam(value = "nationality", required = false) String nationality) {

        List<Author> search = authorService.search(name, nationality);

        List<AuthorResponseDTO> listAuthor = search.stream()
                .map(mapper::toDto)
                .toList();
        return ResponseEntity.ok(listAuthor);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable("id") String id,
                                       @RequestBody @Valid AuthorDTO request) {
        var authorOptional = authorService.getById(id);

        if (authorOptional.isPresent()) {
            var author = authorOptional.get();
            author.setName(request.name());
            author.setBirthday(request.birthday());
            author.setNationality(request.nationality());

            authorService.update(author);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
