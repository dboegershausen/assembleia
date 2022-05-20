package br.com.cooperados.assembleia.api.v1.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PautaRequestDTO {

    @NotBlank(message = "Conteúdo deve ser informado.")
    private String conteudo;

}
