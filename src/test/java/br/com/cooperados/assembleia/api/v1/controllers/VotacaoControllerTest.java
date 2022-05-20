package br.com.cooperados.assembleia.api.v1.controllers;

import br.com.cooperados.assembleia.api.v1.models.VotacaoRequestDTO;
import br.com.cooperados.assembleia.api.v1.models.VotoRequestDTO;
import br.com.cooperados.assembleia.domain.enums.OpcaoDeVoto;
import br.com.cooperados.assembleia.domain.models.Cooperado;
import br.com.cooperados.assembleia.domain.models.Pauta;
import br.com.cooperados.assembleia.domain.models.Votacao;
import br.com.cooperados.assembleia.domain.models.Voto;
import br.com.cooperados.assembleia.domain.services.CooperadoService;
import br.com.cooperados.assembleia.domain.services.PautaService;
import br.com.cooperados.assembleia.domain.services.VotacaoService;
import br.com.cooperados.assembleia.domain.services.VotoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VotacaoControllerTest {

    @InjectMocks
    VotacaoController votacaoController;

    @Mock
    private VotacaoService votacaoService;

    @Mock
    private CooperadoService cooperadoService;

    @Mock
    private PautaService pautaService;

    @Mock
    private VotoService votoService;

    @Test
    void deve_iniciar_votacao() {
        var votacao = votacao();
        var votacaoRequestDto = votacaoRequestDto();
        when(pautaService.buscar(any(UUID.class))).thenReturn(votacao.getPauta());
        when(votacaoService.iniciar(any(Votacao.class))).thenReturn(votacao);
        var votacaoResponse = votacaoController.iniciarVotacao(votacaoRequestDto);
        votacao.setId(null);
        assertNotNull(votacaoResponse.getBody());
        assertEquals(HttpStatus.CREATED, votacaoResponse.getStatusCode());
    }

    @Test
    void deve_votar() {
        var voto = votoSim();
        var votoRequestDto = votoRequestDto();
        when(votacaoService.buscarPeloId(any(UUID.class))).thenReturn(voto.getVotacao());
        when(cooperadoService.buscarPeloCpf(any(String.class))).thenReturn(voto.getCooperado());
        var votoResponse = votacaoController.votar(votoRequestDto, voto.getVotacao().getId());
        assertEquals(HttpStatus.NO_CONTENT, votoResponse.getStatusCode());
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

    private Cooperado cooperado() {
        var cooperado = new Cooperado();
        cooperado.setId(UUID.randomUUID());
        cooperado.setCpf("23541240075");
        cooperado.setNome("João da Silva");
        return cooperado;
    }

    private VotacaoRequestDTO votacaoRequestDto() {
        var votacaoRequestDto = new VotacaoRequestDTO();
        votacaoRequestDto.setDuracao(2L);
        votacaoRequestDto.setPauta(UUID.randomUUID().toString());
        return votacaoRequestDto;
    }

    private VotoRequestDTO votoRequestDto() {
        var votoRequestDto = new VotoRequestDTO();
        votoRequestDto.setVotacao(UUID.randomUUID());
        votoRequestDto.setCpf(cooperado().getCpf());
        votoRequestDto.setOpcao("SIM");
        return votoRequestDto;
    }

}
