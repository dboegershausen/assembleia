package br.com.cooperados.assembleia.domain.models;

import br.com.cooperados.assembleia.domain.enums.ResultadoDaVotacao;
import br.com.cooperados.assembleia.domain.enums.StatusDaVotacao;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Votacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private LocalDateTime inicio;

    private LocalDateTime fim;

    @OneToOne(cascade = CascadeType.ALL)
    private Pauta pauta;

    @Enumerated(EnumType.STRING)
    private StatusDaVotacao status;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "votacao")
    private List<Voto> votos;

    @Enumerated(EnumType.STRING)
    private ResultadoDaVotacao resultado;

    @Column(name = "votos_a_favor")
    private Integer votosAFavor;

    @Column(name = "votos_contrarios")
    private Integer votosContrarios;

}
