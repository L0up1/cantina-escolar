package dev.thiago.cantina.exception;

public class ProdutoJaExisteException extends RuntimeException {
    public ProdutoJaExisteException(String message) {
        super(message);
    }
}
