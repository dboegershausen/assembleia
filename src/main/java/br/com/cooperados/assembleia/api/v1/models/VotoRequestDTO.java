package br.com.cooperados.assembleia.api.v1.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
public class VotoRequestDTO {

    @NotBlank(message = "Opção deve ser informada.")
    private String opcao;

    @NotBlank(message = "Cpf deve ser informado.")
    private String cpf;

    private UUID votacao;

}
