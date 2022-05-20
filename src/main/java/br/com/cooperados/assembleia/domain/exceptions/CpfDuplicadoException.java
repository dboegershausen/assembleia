package br.com.cooperados.assembleia.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CpfDuplicadoException extends RuntimeException {

    public CpfDuplicadoException(String cpf) {
        super(String.format("O cpf %s já está em uso.", cpf));
    }

}
