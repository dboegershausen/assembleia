package br.com.cooperados.assembleia.api.v1.models;

import lombok.Data;

import java.util.UUID;

@Data
public class CooperadoResponseDTO {

    private UUID id;

    private String cpf;

    private String nome;

}
