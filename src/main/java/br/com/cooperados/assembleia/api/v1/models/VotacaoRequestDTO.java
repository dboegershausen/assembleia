package br.com.cooperados.assembleia.api.v1.models;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class VotacaoRequestDTO {

    @Min(value = 1, message = "Duração deve ser maior que zero.")
    private Long duracao;

    @NotBlank(message = "Pauta deve ser informada.")
    private String pauta;

}
