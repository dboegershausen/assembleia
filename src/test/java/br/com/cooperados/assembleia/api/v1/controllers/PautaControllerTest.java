package br.com.cooperados.assembleia.api.v1.controllers;

import br.com.cooperados.assembleia.api.v1.models.PautaRequestDTO;
import br.com.cooperados.assembleia.domain.enums.StatusDaVotacao;
import br.com.cooperados.assembleia.domain.models.Cooperado;
import br.com.cooperados.assembleia.domain.models.Pauta;
import br.com.cooperados.assembleia.domain.models.Votacao;
import br.com.cooperados.assembleia.domain.services.PautaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PautaControllerTest {

    @InjectMocks
    PautaController pautaController;

    @Mock
    PautaService pautaService;

    @Test
    void deve_cadastrar_pauta() {
        var pauta = pauta();
        var pautaRequestDto = pautaRequestDto();
        when(pautaService.cadastrar(any(Pauta.class))).thenReturn(pauta);
        var pautaResponse = pautaController.cadastrarPauta(pautaRequestDto());
        pauta.setId(null);
        assertNotNull(pautaResponse.getBody());
        assertEquals(HttpStatus.CREATED, pautaResponse.getStatusCode());
        assertEquals(pautaRequestDto.getConteudo(), pautaResponse.getBody().getConteudo());
    }

    @Test
    void deve_buscar_todas_as_pautas() {
        var pautas = List.of(pauta(), pautaComVotacao());
        when(pautaService.buscarTodas()).thenReturn(pautas);
        var pautasResponse = pautaController.buscarTodasAsPautas();
        assertEquals(2, pautasResponse.getBody().size());
        assertEquals(HttpStatus.OK, pautasResponse.getStatusCode());
    }

    @Test
    void deve_buscar_a_pauta_pelo_id() {
        var pauta = pauta();
        when(pautaService.buscar(any(UUID.class))).thenReturn(pauta);
        var pautaResponse = pautaController.buscarPauta(pauta.getId());
        assertNotNull(pautaResponse.getBody());
        assertEquals(HttpStatus.OK, pautaResponse.getStatusCode());
    }

    private Cooperado cooperado() {
        var cooperado = new Cooperado();
        cooperado.setId(UUID.randomUUID());
        cooperado.setCpf("23541240075");
        cooperado.setNome("João da Silva");
        return cooperado;
    }

    private Pauta pauta() {
        var pauta = new Pauta();
        pauta.setId(UUID.randomUUID());
        pauta.setConteudo("Construção nova sede");
        return pauta;
    }

    private Pauta pautaComVotacao() {
        var pauta = pauta();
        var votacao = new Votacao();
        votacao.setId(UUID.randomUUID());
        votacao.setPauta(pauta);
        votacao.setStatus(StatusDaVotacao.INICIADA);
        pauta.setVotacao(votacao);
        return pauta;
    }

    private PautaRequestDTO pautaRequestDto() {
        var pautaRequestDto = new PautaRequestDTO();
        pautaRequestDto.setConteudo("Construção nova sede");
        return pautaRequestDto;
    }

}
