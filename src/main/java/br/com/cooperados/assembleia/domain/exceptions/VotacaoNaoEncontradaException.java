package br.com.cooperados.assembleia.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VotacaoNaoEncontradaException extends RuntimeException {

    public VotacaoNaoEncontradaException(UUID idDaVotacao) {
        super(String.format("Votacao com o id %s não encontrada.", idDaVotacao));
    }

}
