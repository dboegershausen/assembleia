package br.com.cooperados.assembleia.domain.models;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String conteudo;

    @OneToOne(mappedBy = "pauta")
    private Votacao votacao;

}
