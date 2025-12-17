package tech.kvothe.libraryapi.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.kvothe.libraryapi.dto.ClientDTO;
import tech.kvothe.libraryapi.mapper.ClientMapper;
import tech.kvothe.libraryapi.model.Client;
import tech.kvothe.libraryapi.service.ClientService;

@RestController
@RequestMapping("/clients")
@PreAuthorize("hasRole('ADMIN')")
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper mapper;

    public ClientController(ClientService clientService, ClientMapper mapper) {
        this.clientService = clientService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid ClientDTO request) {
        Client client = mapper.toEntity(request);
        clientService.save(client);
        return ResponseEntity.ok().build();
    }




}
