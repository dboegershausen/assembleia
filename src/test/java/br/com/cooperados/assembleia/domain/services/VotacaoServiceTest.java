package br.com.cooperados.assembleia.domain.services;

import br.com.cooperados.assembleia.domain.enums.OpcaoDeVoto;
import br.com.cooperados.assembleia.domain.enums.ResultadoDaVotacao;
import br.com.cooperados.assembleia.domain.enums.StatusDaVotacao;
import br.com.cooperados.assembleia.domain.exceptions.*;
import br.com.cooperados.assembleia.domain.models.Cooperado;
import br.com.cooperados.assembleia.domain.models.Pauta;
import br.com.cooperados.assembleia.domain.models.Votacao;
import br.com.cooperados.assembleia.domain.models.Voto;
import br.com.cooperados.assembleia.domain.repositories.CooperadoRepository;
import br.com.cooperados.assembleia.domain.repositories.VotacaoRepository;
import br.com.cooperados.assembleia.domain.services.impl.CooperadoServiceImpl;
import br.com.cooperados.assembleia.domain.services.impl.VotacaoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VotacaoServiceTest {

    @InjectMocks
    VotacaoServiceImpl votacaoService;

    @Mock
    VotacaoRepository votacaoRepository;

    @Test
    void deve_iniciar_votacao() {
        var votacao = votacao();
        when(votacaoRepository.findByPautaId(votacao.getPauta().getId())).thenReturn(Optional.empty());
        var votacaoIniciada = votacaoService.iniciar(votacao);
        verify(votacaoRepository, times(1)).findByPautaId(votacao.getPauta().getId());
        verify(votacaoRepository, times(1)).save(votacao);
    }

    @Test
    void nao_deve_iniciar_votacao_de_pauta_ja_votada() {
        var votacao = votacao();
        votacao.setStatus(StatusDaVotacao.FINALIZADA);
        when(votacaoRepository.findByPautaId(votacao.getPauta().getId())).thenReturn(Optional.of(votacao));
        VotacaoDuplicadaException excessao = assertThrows(VotacaoDuplicadaException.class, () ->
            votacaoService.iniciar(votacao)
        );
        assertEquals(String.format("A pauta de id %s já foi votada.", votacao.getPauta().getId()), excessao.getMessage());
        verify(votacaoRepository, times(1)).findByPautaId(votacao.getPauta().getId());
    }

    @Test
    void nao_deve_iniciar_votacao_de_pauta_com_votacao_em_andamento() {
        var votacao = votacao();
        votacao.setStatus(StatusDaVotacao.INICIADA);
        when(votacaoRepository.findByPautaId(votacao.getPauta().getId())).thenReturn(Optional.of(votacao));
        VotacaoEmAndamentoException excessao = assertThrows(VotacaoEmAndamentoException.class, () ->
                votacaoService.iniciar(votacao)
        );
        assertEquals(String.format("Já existe uma votacao em andamento para a pauta %s.", votacao.getPauta().getId()), excessao.getMessage());
        verify(votacaoRepository, times(1)).findByPautaId(votacao.getPauta().getId());
    }

    @Test
    void deve_encerrar_votacao() {
        var votacao = votacao();
        votacao.setStatus(StatusDaVotacao.FINALIZADA);
        when(votacaoRepository.findById(votacao.getId())).thenReturn(Optional.of(votacao));
        votacaoService.encerrar(votacao.getId());
        verify(votacaoRepository, times(1)).save(votacao);
        verify(votacaoRepository, times(1)).findById(votacao.getId());
    }

    @Test
    void deve_apurar_votacao_empatada() {
        var votacao = votacao();
        votacao.setVotos(List.of(votoSim(), votoNao()));
        votacao.setStatus(StatusDaVotacao.FINALIZADA);
        when(votacaoRepository.findById(votacao.getId())).thenReturn(Optional.of(votacao));
        votacaoService.apurar(votacao.getId());
        verify(votacaoRepository, times(1)).save(votacao);
        verify(votacaoRepository, times(1)).findById(votacao.getId());
    }

    @Test
    void deve_apurar_votacao_aprovada() {
        var votacao = votacao();
        votacao.setVotos(List.of(votoSim()));
        votacao.setStatus(StatusDaVotacao.FINALIZADA);
        when(votacaoRepository.findById(votacao.getId())).thenReturn(Optional.of(votacao));
        votacaoService.apurar(votacao.getId());
        verify(votacaoRepository, times(1)).save(votacao);
        verify(votacaoRepository, times(1)).findById(votacao.getId());
    }

    @Test
    void deve_apurar_votacao_reprovada() {
        var votacao = votacao();
        votacao.setVotos(List.of(votoNao()));
        votacao.setStatus(StatusDaVotacao.FINALIZADA);
        when(votacaoRepository.findById(votacao.getId())).thenReturn(Optional.of(votacao));
        votacaoService.apurar(votacao.getId());
        verify(votacaoRepository, times(1)).save(votacao);
        verify(votacaoRepository, times(1)).findById(votacao.getId());
    }

    @Test
    void deve_buscar_votacao_pelo_id() {
        var votacao = votacao();
        votacao.setStatus(StatusDaVotacao.INICIADA);
        when(votacaoRepository.findById(votacao.getId())).thenReturn(Optional.of(votacao));
        votacaoService.buscarPeloId(votacao.getId());
        verify(votacaoRepository, times(1)).findById(votacao.getId());
    }

    @Test
    void deve_lancar_votacao_nao_encontrada_para_id_invalido() {
        var idDaVotacao = UUID.randomUUID();
        when(votacaoRepository.findById(idDaVotacao)).thenReturn(Optional.empty());
        VotacaoNaoEncontradaException excessao = assertThrows(VotacaoNaoEncontradaException.class, () ->
                votacaoService.buscarPeloId(idDaVotacao)
        );
        assertEquals(String.format("Votacao com o id %s não encontrada.", idDaVotacao), excessao.getMessage());
        verify(votacaoRepository, times(1)).findById(idDaVotacao);
    }

    @Test
    void deve_buscar_votacoes_com_tempo_expirado() {
        var votacao = votacao();
        votacao.setStatus(StatusDaVotacao.INICIADA);
        votacao.setFim(LocalDateTime.now().minusMinutes(2));
        when(votacaoRepository.findAllByStatus(votacao.getStatus())).thenReturn(List.of(votacao));
        List<Votacao> votacoesExpiradas = votacaoService.buscarVotacoesEmAndamentoComTempoEsgotado();
        assertEquals(1, votacoesExpiradas.size());
        verify(votacaoRepository, times(1)).findAllByStatus(votacao.getStatus());
    }

    private Votacao votacao() {
        var votacao = new Votacao();
        votacao.setId(UUID.randomUUID());
        votacao.setPauta(pauta());
        votacao.setInicio(LocalDateTime.now());
        votacao.setFim(LocalDateTime.now().plusMinutes(2));
        return votacao;
    }

    private Pauta pauta() {
        var pauta = new Pauta();
        pauta.setId(UUID.randomUUID());
        pauta.setConteudo("Construção nova sede");
        return pauta;
    }

    private Voto votoSim() {
        var voto = new Voto();
        voto.setVotacao(votacao());
        voto.setOpcao(OpcaoDeVoto.SIM);
        voto.setCooperado(cooperado());
        return voto;
    }

    private Voto votoNao() {
        var voto = new Voto();
        voto.setVotacao(votacao());
        voto.setOpcao(OpcaoDeVoto.NAO);
        voto.setCooperado(cooperado());
        return voto;
    }

    private Cooperado cooperado() {
        var cooperado = new Cooperado();
        cooperado.setId(UUID.randomUUID());
        cooperado.setCpf("23541240075");
        cooperado.setNome("João da Silva");
        return cooperado;
    }

}
