package br.com.cooperados.assembleia.domain.models;

import br.com.cooperados.assembleia.domain.enums.OpcaoDeVoto;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private Cooperado cooperado;

    @Enumerated(EnumType.STRING)
    private OpcaoDeVoto opcao;

    @ManyToOne
    private Votacao votacao;

}
