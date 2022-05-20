package br.com.cooperados.assembleia.domain.services;

import br.com.cooperados.assembleia.domain.models.Votacao;

import java.util.List;
import java.util.UUID;

public interface VotacaoService {

    Votacao iniciar(Votacao votacao);

    void encerrar(UUID idDaVotacao);

    Votacao apurar(UUID idDaVotacao);

    Votacao buscarPeloId(UUID idDaVotacao);

    List<Votacao> buscarVotacoesEmAndamentoComTempoEsgotado();

}
