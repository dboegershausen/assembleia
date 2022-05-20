package br.com.cooperados.assembleia.api.v1.mappers;

import br.com.cooperados.assembleia.api.v1.models.CooperadoRequestDTO;
import br.com.cooperados.assembleia.domain.models.Cooperado;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CooperadoMapperTest {

    @Mock
    CooperadoMapper cooperadoMapper;

    @Test
    void deve_transformar_para_entidade() {
        var cooperadoRequestDto = cooperadoRequestDto();
        var cooperado = cooperadoMapper.paraEntidade(cooperadoRequestDto);
        assertTrue(Objects.nonNull(cooperado));
        assertEquals(cooperadoRequestDto.getCpf(), cooperado.getCpf());
        assertEquals(cooperadoRequestDto.getNome(), cooperado.getNome());
    }

    @Test
    void deve_transformar_para_response_dto() {
        var cooperado = cooperado();
        var cooperadoResponseDto = cooperadoMapper.paraResponseDto(cooperado);
        assertTrue(Objects.nonNull(cooperadoResponseDto));
        assertEquals(cooperadoResponseDto.getId(), cooperado.getId());
        assertEquals(cooperadoResponseDto.getCpf(), cooperado.getCpf());
        assertEquals(cooperadoResponseDto.getNome(), cooperado.getNome());
    }

    private Cooperado cooperado() {
        var cooperado = new Cooperado();
        cooperado.setId(UUID.randomUUID());
        cooperado.setCpf("23541240075");
        cooperado.setNome("João da Silva");
        return cooperado;
    }

    private CooperadoRequestDTO cooperadoRequestDto() {
        var cooperadoRequestDto = new CooperadoRequestDTO();
        cooperadoRequestDto.setCpf("23541240075");
        cooperadoRequestDto.setNome("João da Silva");
        return cooperadoRequestDto;
    }

}
