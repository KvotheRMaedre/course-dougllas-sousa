package tech.kvothe.libraryapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ParamError")
public record ParamErrorDTO (String param, String error){
}
