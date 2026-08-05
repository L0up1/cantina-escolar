package dev.thiago.cantina.exception;

public class CategoriaNaoEncontradaException extends RuntimeException {
    public CategoriaNaoEncontradaException(String message) {
        super("Categoria não encontrada!" + message);
    }
}
