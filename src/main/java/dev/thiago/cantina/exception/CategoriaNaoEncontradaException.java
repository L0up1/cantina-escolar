package dev.thiago.cantina.exception;

public class CategoriaNaoEncontradaException extends RuntimeException {
    public CategoriaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
