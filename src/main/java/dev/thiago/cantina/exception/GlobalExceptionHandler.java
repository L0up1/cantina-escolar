package dev.thiago.cantina.exception;

import dev.thiago.cantina.dto.ErroResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoriaJaExisteException.class)
    public ResponseEntity<ErroResponseDTO> tratarCategoriaJaExistente(
            CategoriaJaExisteException ex
    ) {
        ErroResponseDTO erro = new ErroResponseDTO(
                LocalDate.now(),
                HttpStatus.CONFLICT.value(),
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(erro);
    }

    @ExceptionHandler(CategoriaNaoEncontradaException.class)
    public ResponseEntity<ErroResponseDTO> tratarCategoriaNaoEncontrada(
            CategoriaNaoEncontradaException ex
    ){
        ErroResponseDTO erro = new ErroResponseDTO(
                LocalDate.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(erro);
    }

    }

