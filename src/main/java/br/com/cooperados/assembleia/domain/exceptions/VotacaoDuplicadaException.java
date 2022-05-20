package br.com.cooperados.assembleia.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.CONFLICT)
public class VotacaoDuplicadaException extends RuntimeException {

    public VotacaoDuplicadaException(UUID idDaPauta) {
        super(String.format("A pauta de id %s já foi votada.", idDaPauta));
    }

}
