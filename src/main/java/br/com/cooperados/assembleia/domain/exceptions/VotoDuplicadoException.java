package br.com.cooperados.assembleia.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.CONFLICT)
public class VotoDuplicadoException extends RuntimeException {

    public VotoDuplicadoException(UUID idDoCooperado, UUID idDaPauta) {
        super(String.format("O cooperado %s já votou na pauta %s.", idDoCooperado, idDaPauta));
    }

}
