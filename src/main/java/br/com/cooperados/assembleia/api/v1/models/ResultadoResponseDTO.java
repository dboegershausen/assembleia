package br.com.cooperados.assembleia.api.v1.models;

import br.com.cooperados.assembleia.domain.enums.ResultadoDaVotacao;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultadoResponseDTO {

    private LocalDateTime inicio;

    private LocalDateTime fim;

    private ResultadoDaVotacao resultado;

    private Integer votosAFavor;

    private Integer votosContrarios;

}
