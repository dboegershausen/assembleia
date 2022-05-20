package br.com.cooperados.assembleia.api.v1.mappers;

import br.com.cooperados.assembleia.api.v1.models.VotacaoRequestDTO;
import br.com.cooperados.assembleia.domain.enums.OpcaoDeVoto;
import br.com.cooperados.assembleia.domain.enums.ResultadoDaVotacao;
import br.com.cooperados.assembleia.domain.enums.StatusDaVotacao;
import br.com.cooperados.assembleia.domain.models.Cooperado;
import br.com.cooperados.assembleia.domain.models.Pauta;
import br.com.cooperados.assembleia.domain.models.Votacao;
import br.com.cooperados.assembleia.domain.models.Voto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class VotacaoMapperTest {

    @Mock
    VotacaoMapper votacaoMapper;

    @Test
    void deve_transformar_para_entidade() {
        var votacaoRequestDto = votacaoRequestDto();
        var pauta = pauta();
        votacaoRequestDto.setPauta(pauta.getId().toString());
        var votacao = votacaoMapper.paraEntidade(votacaoRequestDto, pauta);
        assertTrue(Objects.nonNull(votacao));
        assertTrue(Objects.nonNull(votacao.getPauta()));
        assertTrue(Objects.nonNull(votacao.getInicio()));
        assertTrue(Objects.nonNull(votacao.getFim()));
        assertTrue(votacao.getFim().isAfter(votacao.getInicio()));
        assertEquals(votacao.getInicio().plusMinutes(votacaoRequestDto.getDuracao()), votacao.getFim());
    }

    @Test
    void deve_transformar_para_entidade_com_duracao_default() {
        var votacaoRequestDto = new VotacaoRequestDTO();
        var pauta = pauta();
        votacaoRequestDto.setPauta(pauta.getId().toString());
        var votacao = votacaoMapper.paraEntidade(votacaoRequestDto, pauta);
        assertTrue(Objects.nonNull(votacao));
        assertTrue(Objects.nonNull(votacao.getPauta()));
        assertTrue(Objects.nonNull(votacao.getInicio()));
        assertTrue(Objects.nonNull(votacao.getFim()));
        assertTrue(votacao.getFim().isAfter(votacao.getInicio()));
        assertEquals(votacao.getInicio().plusMinutes(1), votacao.getFim());
    }

    @Test
    void deve_transformar_para_resultado_response_dto() {
        var votacao= votacaoEncerrada();
        var votacaoResultadoResponseDto = votacaoMapper.paraResultadoResponseDto(votacao);
        assertTrue(Objects.nonNull(votacaoResultadoResponseDto));
        assertEquals(ResultadoDaVotacao.APROVADA, votacaoResultadoResponseDto.getResultado());
        assertEquals(votacao.getInicio(), votacaoResultadoResponseDto.getInicio());
        assertEquals(votacao.getFim(), votacaoResultadoResponseDto.getFim());
        assertEquals(votacao.getVotosAFavor(), votacaoResultadoResponseDto.getVotosAFavor());
        assertEquals(votacao.getVotosContrarios(), votacaoResultadoResponseDto.getVotosContrarios());
    }

    @Test
    void deve_transformar_para_response_dto() {
        var votacao= votacaoEncerrada();
        var votacaoResponseDto = votacaoMapper.paraResponseDto(votacao);
        assertTrue(Objects.nonNull(votacaoResponseDto));
        assertEquals(ResultadoDaVotacao.APROVADA, votacaoResponseDto.getResultado());
        assertEquals(StatusDaVotacao.FINALIZADA, votacaoResponseDto.getStatus());
        assertEquals(votacao.getId(), votacaoResponseDto.getId());
        assertEquals(votacao.getInicio(), votacaoResponseDto.getInicio());
        assertEquals(votacao.getFim(), votacaoResponseDto.getFim());
    }

    private Pauta pauta() {
        var pauta = new Pauta();
        pauta.setId(UUID.randomUUID());
        pauta.setConteudo("Construção nova sede");
        return pauta;
    }

    private VotacaoRequestDTO votacaoRequestDto() {
        var votacaoRequestDto = new VotacaoRequestDTO();
        votacaoRequestDto.setDuracao(2L);
        votacaoRequestDto.setPauta(UUID.randomUUID().toString());
        return votacaoRequestDto;
    }

    private Votacao votacao() {
        var votacao = new Votacao();
        votacao.setId(UUID.randomUUID());
        votacao.setPauta(pauta());
        votacao.setInicio(LocalDateTime.now());
        votacao.setFim(LocalDateTime.now().plusMinutes(2));
        return votacao;
    }

    private Votacao votacaoEncerrada() {
        var votacao = new Votacao();
        votacao.setId(UUID.randomUUID());
        votacao.setPauta(pauta());
        votacao.setInicio(LocalDateTime.now().minusMinutes(5));
        votacao.setFim(LocalDateTime.now().minusMinutes(3));
        votacao.setStatus(StatusDaVotacao.FINALIZADA);
        votacao.setVotos(List.of(votoSim()));
        votacao.setVotosAFavor(1);
        votacao.setVotosContrarios(0);
        votacao.setResultado(ResultadoDaVotacao.APROVADA);
        return votacao;
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

}
