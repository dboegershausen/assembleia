package br.com.cooperados.assembleia.api.v1.mappers;

import br.com.cooperados.assembleia.api.v1.models.CooperadoRequestDTO;
import br.com.cooperados.assembleia.api.v1.models.PautaRequestDTO;
import br.com.cooperados.assembleia.domain.enums.StatusDaPauta;
import br.com.cooperados.assembleia.domain.enums.StatusDaVotacao;
import br.com.cooperados.assembleia.domain.models.Cooperado;
import br.com.cooperados.assembleia.domain.models.Pauta;
import br.com.cooperados.assembleia.domain.models.Votacao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PautaMapperTest {

    @Mock
    PautaMapper pautaMapper;

    @Test
    void deve_transformar_para_entidade() {
        var pautaRequestDto = pautaRequestDto();
        var pauta = pautaMapper.paraEntidade(pautaRequestDto);
        assertTrue(Objects.nonNull(pauta));
        assertEquals(pautaRequestDto.getConteudo(), pauta.getConteudo());
    }

    @Test
    void deve_transformar_para_response_dto_nao_votada() {
        var pauta = pauta();
        var pautaResponseDto = pautaMapper.paraResponseDto(pauta);
        assertTrue(Objects.nonNull(pautaResponseDto));
        assertTrue(Objects.isNull(pautaResponseDto.getVotacao()));
        assertEquals(pautaResponseDto.getId(), pauta.getId());
        assertEquals(pautaResponseDto.getConteudo(), pauta.getConteudo());
        assertEquals(StatusDaPauta.NAO_VOTADA, pautaResponseDto.getStatus());
    }

    @Test
    void deve_transformar_para_response_dto_em_votacao() {
        var pauta = pautaComVotacao();
        var pautaResponseDto = pautaMapper.paraResponseDto(pauta);
        assertTrue(Objects.nonNull(pautaResponseDto));
        assertTrue(Objects.nonNull(pautaResponseDto.getVotacao()));
        assertEquals(pautaResponseDto.getId(), pauta.getId());
        assertEquals(pautaResponseDto.getConteudo(), pauta.getConteudo());
        assertEquals(StatusDaPauta.EM_VOTACAO, pautaResponseDto.getStatus());
    }

    @Test
    void deve_transformar_para_response_dto_votada() {
        var pauta = pautaComVotacao();
        pauta.getVotacao().setStatus(StatusDaVotacao.FINALIZADA);
        var pautaResponseDto = pautaMapper.paraResponseDto(pauta);
        assertTrue(Objects.nonNull(pautaResponseDto));
        assertTrue(Objects.nonNull(pautaResponseDto.getVotacao()));
        assertEquals(pautaResponseDto.getId(), pauta.getId());
        assertEquals(pautaResponseDto.getConteudo(), pauta.getConteudo());
        assertEquals(StatusDaPauta.VOTADA, pautaResponseDto.getStatus());
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
