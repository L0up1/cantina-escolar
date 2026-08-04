package dev.thiago.cantina.exception;

public class CategoriaJaExisteException extends RuntimeException {
    public CategoriaJaExisteException(String nome) {
        super("Já existe uma categoria cadastrada com o nome: " + nome);
    }
}
