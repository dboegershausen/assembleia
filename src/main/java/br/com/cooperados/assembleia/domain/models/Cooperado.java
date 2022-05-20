package br.com.cooperados.assembleia.domain.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Cooperado {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String cpf;

    private String nome;

    @OneToMany(mappedBy = "cooperado")
    private List<Voto> votos;

}
