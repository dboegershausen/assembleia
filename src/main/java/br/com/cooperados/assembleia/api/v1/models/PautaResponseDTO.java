package br.com.cooperados.assembleia.api.v1.models;

import br.com.cooperados.assembleia.domain.enums.StatusDaPauta;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PautaResponseDTO {

    private UUID id;

    private String conteudo;

    private StatusDaPauta status;

    private ResultadoResponseDTO votacao;

}
