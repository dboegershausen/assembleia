package br.com.cooperados.assembleia.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PautaNaoEncontradaException extends RuntimeException {

    public PautaNaoEncontradaException(UUID idDaPauta) {
        super(String.format("Pauta com o id %s não encontrada.", idDaPauta));
    }

}
