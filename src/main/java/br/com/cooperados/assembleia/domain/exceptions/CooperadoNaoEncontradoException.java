package br.com.cooperados.assembleia.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CooperadoNaoEncontradoException extends RuntimeException {

    public CooperadoNaoEncontradoException(UUID idDoCooperado) {
        super(String.format("Cooperado com o id %s não encontrado.", idDoCooperado));
    }

    public CooperadoNaoEncontradoException(String cpf) {
        super(String.format("Cooperado com o cpf %s não encontrado.", cpf));
    }

}
