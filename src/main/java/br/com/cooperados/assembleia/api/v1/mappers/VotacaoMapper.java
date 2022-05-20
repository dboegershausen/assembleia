package br.com.cooperados.assembleia.api.v1.mappers;

import br.com.cooperados.assembleia.api.v1.models.ResultadoResponseDTO;
import br.com.cooperados.assembleia.api.v1.models.VotacaoRequestDTO;
import br.com.cooperados.assembleia.api.v1.models.VotacaoResponseDTO;
import br.com.cooperados.assembleia.domain.models.Pauta;
import br.com.cooperados.assembleia.domain.models.Votacao;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.Objects;

public class VotacaoMapper {

    private VotacaoMapper() {}

    public static VotacaoResponseDTO paraResponseDto(Votacao votacao) {
        var votacaoResponseDto = new VotacaoResponseDTO();
        BeanUtils.copyProperties(votacao, votacaoResponseDto);
        return votacaoResponseDto;
    }

    public static ResultadoResponseDTO paraResultadoResponseDto(Votacao votacao) {
        var resultadoResponseDto = new ResultadoResponseDTO();
        BeanUtils.copyProperties(votacao, resultadoResponseDto);
        return resultadoResponseDto;
    }

    public static Votacao paraEntidade(VotacaoRequestDTO votacaoRequestDTO, Pauta pauta) {
        var votacao = new Votacao();
        BeanUtils.copyProperties(votacaoRequestDTO, votacao);
        votacao.setInicio(LocalDateTime.now());
        if (Objects.isNull(votacaoRequestDTO.getDuracao())) {
            votacao.setFim(votacao.getInicio().plusMinutes(1));
        } else {
            votacao.setFim(votacao.getInicio().plusMinutes(votacaoRequestDTO.getDuracao()));
        }
        votacao.setPauta(pauta);
        return votacao;
    }

}
