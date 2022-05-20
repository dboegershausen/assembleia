package br.com.cooperados.assembleia.api.v1.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CooperadoRequestDTO {

    @NotBlank(message = "Cpf deve ser informado.")
    private String cpf;

    @NotBlank(message = "Nome deve ser informado.")
    private String nome;

}
