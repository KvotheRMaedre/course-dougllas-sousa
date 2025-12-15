package tech.kvothe.libraryapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.kvothe.libraryapi.dto.ErrorResponseDTO;
import tech.kvothe.libraryapi.dto.ParamErrorDTO;
import tech.kvothe.libraryapi.exception.DuplicatedResourceException;
import tech.kvothe.libraryapi.exception.OperationNotAllowedException;

import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO handleUnhandledExceptions(RuntimeException exception) {
        return new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "", List.of());
    }

    @ExceptionHandler(DuplicatedResourceException.class)
    public ResponseEntity<ErrorResponseDTO>handleDuplicateResourceException(DuplicatedResourceException exception) {
        var errorDto = ErrorResponseDTO.conflict(exception.getMessage());
        return ResponseEntity.status(errorDto.status()).body(errorDto);
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    public ResponseEntity<ErrorResponseDTO>handleOperationNotAllowedException(OperationNotAllowedException exception) {
        var errorDto = ErrorResponseDTO.defaultResponse(exception.getMessage());
        return ResponseEntity.status(errorDto.status()).body(errorDto);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO>handleIllegalArgumentException(IllegalArgumentException exception) {
        var errorDto = ErrorResponseDTO.defaultResponse(exception.getMessage());
        return ResponseEntity.status(errorDto.status()).body(errorDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponseDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        var fieldErrors = exception.getFieldErrors()
                .stream()
                .map(fieldError -> new ParamErrorDTO(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        return ErrorResponseDTO.unprocessableEntity("Erro de validação",fieldErrors);
    }
}
