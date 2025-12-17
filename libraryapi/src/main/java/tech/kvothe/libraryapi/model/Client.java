package tech.kvothe.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "client")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String clientId;

    @Column
    private String clientSecret;

    @Column
    private String redirectURI;

    @Column
    private String scope;
}
