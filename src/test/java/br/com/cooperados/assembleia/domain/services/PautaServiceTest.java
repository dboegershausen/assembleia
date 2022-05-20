package br.com.cooperados.assembleia.domain.services;

import br.com.cooperados.assembleia.domain.enums.StatusDaVotacao;
import br.com.cooperados.assembleia.domain.exceptions.PautaNaoEncontradaException;
import br.com.cooperados.assembleia.domain.models.Pauta;
import br.com.cooperados.assembleia.domain.models.Votacao;
import br.com.cooperados.assembleia.domain.repositories.PautaRepository;
import br.com.cooperados.assembleia.domain.services.impl.PautaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PautaServiceTest {

    @InjectMocks
    PautaServiceImpl pautaService;

    @Mock
    PautaRepository pautaRepository;

    @Test
    void deve_cadastrar_pauta() {
        var pauta = pauta();
        pautaService.cadastrar(pauta);
        verify(pautaRepository, times(1)).save(pauta);
    }

    @Test
    void deve_buscar_cooperado_pelo_id() {
        var pauta = pauta();
        when(pautaRepository.findById(pauta.getId())).thenReturn(Optional.of(pauta));
        var pautaoRetornada = pautaService.buscar(pauta.getId());
        assertTrue(Objects.nonNull(pautaoRetornada));
        verify(pautaRepository, times(1)).findById(pauta.getId());
    }

    @Test
    void deve_lancar_pauta_nao_encontrada_para_id_invalido() {
        UUID idDaPautao = UUID.randomUUID();
        when(pautaRepository.findById(idDaPautao)).thenReturn(Optional.empty());
        PautaNaoEncontradaException excessao = assertThrows(PautaNaoEncontradaException.class, () ->
                pautaService.buscar(idDaPautao)
        );
        assertEquals(String.format("Pauta com o id %s não encontrada.", idDaPautao), excessao.getMessage());
        verify(pautaRepository, times(1)).findById(idDaPautao);
    }

    @Test
    void deve_buscar_todas_as_pautas() {
        var pautas = List.of(pautaComVotacao());
        when(pautaRepository.findAll()).thenReturn(pautas);
        var pautasRetornadas = pautaService.buscarTodas();
        assertTrue(Objects.nonNull(pautasRetornadas));
        assertEquals(1, pautasRetornadas.size());
        verify(pautaRepository, times(1)).findAll();
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

}
