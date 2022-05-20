package br.com.cooperados.assembleia.domain.services;

import br.com.cooperados.assembleia.domain.enums.OpcaoDeVoto;
import br.com.cooperados.assembleia.domain.enums.StatusDaVotacao;
import br.com.cooperados.assembleia.domain.enums.StatusDoUsuario;
import br.com.cooperados.assembleia.domain.exceptions.*;
import br.com.cooperados.assembleia.domain.models.*;
import br.com.cooperados.assembleia.domain.repositories.VotacaoRepository;
import br.com.cooperados.assembleia.domain.repositories.VotoRepository;
import br.com.cooperados.assembleia.domain.services.impl.VotacaoServiceImpl;
import br.com.cooperados.assembleia.domain.services.impl.VotoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VotoServiceTest {

    @InjectMocks
    VotoServiceImpl votoService;

    @Mock
    VotoRepository votoRepository;

    @Mock
    InformacoesDoUsuarioService informacoesDoUsuarioService;

    @Test
    void deve_votar() {
        var voto = votoSim();
        voto.getVotacao().setStatus(StatusDaVotacao.INICIADA);
        when(votoRepository.findByCooperadoIdAndVotacaoId(voto.getCooperado().getId(), voto.getVotacao().getId())).thenReturn(Optional.empty());
        when(informacoesDoUsuarioService.buscar(voto.getCooperado().getCpf())).thenReturn(informacoesDoUsuarioHabilitado());
        votoService.votar(voto);
        verify(informacoesDoUsuarioService, times(1)).buscar(voto.getCooperado().getCpf());
        verify(votoRepository, times(1)).findByCooperadoIdAndVotacaoId(voto.getCooperado().getId(), voto.getVotacao().getId());
        verify(votoRepository, times(1)).save(voto);
    }

    @Test
    void nao_deve_votar_se_a_votacao_estiver_encerrada() {
        var voto = votoSim();
        voto.getVotacao().setStatus(StatusDaVotacao.FINALIZADA);
        VotacaoEncerradaException excessao = assertThrows(VotacaoEncerradaException.class, () ->
                votoService.votar(voto)
        );
        assertEquals(String.format("A votação da pauta %s está encerrada.", voto.getVotacao().getPauta().getId()), excessao.getMessage());
    }

    @Test
    void nao_deve_votar_se_nao_estiver_habilitado() {
        var voto = votoSim();
        voto.getVotacao().setStatus(StatusDaVotacao.INICIADA);
        when(informacoesDoUsuarioService.buscar(voto.getCooperado().getCpf())).thenReturn(informacoesDoUsuarioNaoHabilitado());
        CooperadoInaptoParaVotarException excessao = assertThrows(CooperadoInaptoParaVotarException.class, () ->
            votoService.votar(voto)
        );
        assertEquals(String.format("O cooperado %s não está apto a participar da votação.", voto.getCooperado().getId()), excessao.getMessage());
        verify(informacoesDoUsuarioService, times(1)).buscar(voto.getCooperado().getCpf());
    }

    @Test
    void nao_deve_votar_se_nao_buscar_informacoes_do_usuario() {
        var voto = votoSim();
        voto.getVotacao().setStatus(StatusDaVotacao.INICIADA);
        when(informacoesDoUsuarioService.buscar(voto.getCooperado().getCpf())).thenThrow(new RuntimeException());
        CooperadoInaptoParaVotarException excessao = assertThrows(CooperadoInaptoParaVotarException.class, () ->
                votoService.votar(voto)
        );
        assertEquals(String.format("O cooperado %s não está apto a participar da votação.", voto.getCooperado().getId()), excessao.getMessage());
        verify(informacoesDoUsuarioService, times(1)).buscar(voto.getCooperado().getCpf());
    }

    @Test
    void nao_deve_votar_repetidamente() {
        var voto = votoSim();
        voto.getVotacao().setStatus(StatusDaVotacao.INICIADA);
        when(informacoesDoUsuarioService.buscar(voto.getCooperado().getCpf())).thenReturn(informacoesDoUsuarioHabilitado());
        when(votoRepository.findByCooperadoIdAndVotacaoId(voto.getCooperado().getId(), voto.getVotacao().getId())).thenReturn(Optional.of(voto));
        VotoDuplicadoException excessao = assertThrows(VotoDuplicadoException.class, () ->
                votoService.votar(voto)
        );
        assertEquals(String.format("O cooperado %s já votou na pauta %s.", voto.getCooperado().getId(), voto.getVotacao().getPauta().getId()),
                excessao.getMessage());
        verify(votoRepository, times(1)).findByCooperadoIdAndVotacaoId(voto.getCooperado().getId(), voto.getVotacao().getId());
        verify(informacoesDoUsuarioService, times(1)).buscar(voto.getCooperado().getCpf());
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

    private InformacoesDoUsuario informacoesDoUsuarioHabilitado() {
        var informacoesDoUsuario = new InformacoesDoUsuario();
        informacoesDoUsuario.setStatus(StatusDoUsuario.ABLE_TO_VOTE);
        return informacoesDoUsuario;
    }

    private InformacoesDoUsuario informacoesDoUsuarioNaoHabilitado() {
        var informacoesDoUsuario = new InformacoesDoUsuario();
        informacoesDoUsuario.setStatus(StatusDoUsuario.UNABLE_TO_VOTE);
        return informacoesDoUsuario;
    }

}
