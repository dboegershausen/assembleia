package br.com.cooperados.assembleia.api.v1.mappers;

import br.com.cooperados.assembleia.api.v1.models.VotoRequestDTO;
import br.com.cooperados.assembleia.domain.enums.OpcaoDeVoto;
import br.com.cooperados.assembleia.domain.models.Cooperado;
import br.com.cooperados.assembleia.domain.models.Pauta;
import br.com.cooperados.assembleia.domain.models.Votacao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class VotoMapperTest {

    @Mock
    VotoMapper votoMapper;

    @Test
    void deve_transformar_para_entidade() {
        var votoRequestDto = votoRequestDto();
        var votacao = votacao();
        var cooperado = cooperado();
        votoRequestDto.setVotacao(votacao.getId());
        var voto = votoMapper.paraEntidade(votoRequestDto, cooperado, votacao);
        assertTrue(Objects.nonNull(voto));
        assertTrue(Objects.nonNull(voto.getVotacao()));
        assertTrue(Objects.nonNull(voto.getCooperado()));
        assertEquals(OpcaoDeVoto.SIM, voto.getOpcao());
    }

    private Pauta pauta() {
        var pauta = new Pauta();
        pauta.setId(UUID.randomUUID());
        pauta.setConteudo("Construção nova sede");
        return pauta;
    }

    private VotoRequestDTO votoRequestDto() {
        var votoRequestDto = new VotoRequestDTO();
        votoRequestDto.setVotacao(UUID.randomUUID());
        votoRequestDto.setCpf(cooperado().getCpf());
        votoRequestDto.setOpcao("SIM");
        return votoRequestDto;
    }

    private Votacao votacao() {
        var votacao = new Votacao();
        votacao.setId(UUID.randomUUID());
        votacao.setPauta(pauta());
        votacao.setInicio(LocalDateTime.now());
        votacao.setFim(LocalDateTime.now().plusMinutes(2));
        return votacao;
    }

    private Cooperado cooperado() {
        var cooperado = new Cooperado();
        cooperado.setId(UUID.randomUUID());
        cooperado.setCpf("23541240075");
        cooperado.setNome("João da Silva");
        return cooperado;
    }

}
