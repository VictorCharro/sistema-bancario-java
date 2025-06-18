package br.com.bancovictor.exceptions;

public class ContaNaoEncontradaException extends RuntimeException{

    public ContaNaoEncontradaException(String message) {
        super(message);
    }
}
