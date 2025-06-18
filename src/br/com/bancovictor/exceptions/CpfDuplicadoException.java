package br.com.bancovictor.exceptions;

public class CpfDuplicadoException extends RuntimeException{

    public CpfDuplicadoException(String message) {
        super(message);
    }
}
