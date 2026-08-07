package dev.thiago.cantina.exception;

public class CategoriaJaExisteException extends RuntimeException {
    public CategoriaJaExisteException(String mensagem) {
        super(mensagem);
    }
}
