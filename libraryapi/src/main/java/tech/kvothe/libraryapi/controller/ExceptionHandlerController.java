package tech.kvothe.libraryapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.kvothe.libraryapi.dto.ErrorResponseDTO;
import tech.kvothe.libraryapi.exception.DuplicatedResourceException;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(DuplicatedResourceException.class)
    public ResponseEntity<ErrorResponseDTO>handleDuplicateResourceException(DuplicatedResourceException exception) {
        var errorDto = ErrorResponseDTO.conflict(exception.getMessage());
        return ResponseEntity.status(errorDto.status()).body(errorDto);
    }
}
