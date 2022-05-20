package br.com.cooperados.assembleia.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CooperadoInaptoParaVotarException extends RuntimeException {

    public CooperadoInaptoParaVotarException(UUID idDoCooperado) {
        super(String.format("O cooperado %s não está apto a participar da votação.", idDoCooperado));
    }

}
