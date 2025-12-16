package tech.kvothe.libraryapi.exception;

import lombok.Getter;

@Getter
public class InvalidFieldException extends RuntimeException{

    String field;

    public InvalidFieldException(String message, String field) {
        super(message);
        this.field = field;
    }
}
