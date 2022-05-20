package br.com.cooperados.assembleia.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.CONFLICT)
public class VotacaoEmAndamentoException extends RuntimeException {

    public VotacaoEmAndamentoException(UUID idDaPauta) {
        super(String.format("Já existe uma votacao em andamento para a pauta %s.", idDaPauta));
    }

}
