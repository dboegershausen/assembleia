package br.com.cooperados.assembleia.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VotacaoEncerradaException extends RuntimeException {

    public VotacaoEncerradaException(UUID idDaPauta) {
        super(String.format("A votação da pauta %s está encerrada.", idDaPauta));
    }

}
