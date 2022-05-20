package br.com.cooperados.assembleia.api.v1.models;

import br.com.cooperados.assembleia.domain.enums.ResultadoDaVotacao;
import br.com.cooperados.assembleia.domain.enums.StatusDaVotacao;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VotacaoResponseDTO {

    private UUID id;

    private LocalDateTime inicio;

    private LocalDateTime fim;

    private StatusDaVotacao status;

    private ResultadoDaVotacao resultado;

}
