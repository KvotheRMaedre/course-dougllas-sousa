package tech.kvothe.libraryapi.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.kvothe.libraryapi.dto.AuthorDTO;
import tech.kvothe.libraryapi.dto.AuthorResponseDTO;
import tech.kvothe.libraryapi.model.Author;
import tech.kvothe.libraryapi.service.AuthorService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid AuthorDTO request) {
        var author = authorService.save(request.toEntity());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(author.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AuthorResponseDTO> getDetails(@PathVariable String id) {
        var authorOptional = authorService.getById(id);

        if (authorOptional.isPresent()) {
            var author = authorOptional.get();
            var response = new AuthorResponseDTO(
                    author.getId(),
                    author.getName(),
                    author.getBirthday(),
                    author.getNationality()
            );
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
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

        List<AuthorResponseDTO> test = search.stream().map(
                author -> new AuthorResponseDTO(
                        author.getId(),
                        author.getName(),
                        author.getBirthday(),
                        author.getNationality()
                )).toList();
        return ResponseEntity.ok(test);
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
