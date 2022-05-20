package br.com.cooperados.assembleia.api.v1.mappers;

import br.com.cooperados.assembleia.api.v1.models.PautaRequestDTO;
import br.com.cooperados.assembleia.api.v1.models.PautaResponseDTO;
import br.com.cooperados.assembleia.domain.enums.StatusDaPauta;
import br.com.cooperados.assembleia.domain.enums.StatusDaVotacao;
import br.com.cooperados.assembleia.domain.models.Pauta;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PautaMapper {

    private PautaMapper() {}

    public static PautaResponseDTO paraResponseDto(Pauta pauta) {
        var pautaResponseDto = new PautaResponseDTO();
        BeanUtils.copyProperties(pauta, pautaResponseDto);
        if (Objects.isNull(pauta.getVotacao())) {
            pautaResponseDto.setStatus(StatusDaPauta.NAO_VOTADA);
        } else if (pauta.getVotacao().getStatus().equals(StatusDaVotacao.INICIADA)) {
            pautaResponseDto.setStatus(StatusDaPauta.EM_VOTACAO);
        } else if (pauta.getVotacao().getStatus().equals(StatusDaVotacao.FINALIZADA)) {
            pautaResponseDto.setStatus(StatusDaPauta.VOTADA);
        }
        if (Objects.nonNull(pauta.getVotacao())) {
            pautaResponseDto.setVotacao(VotacaoMapper.paraResultadoResponseDto(pauta.getVotacao()));
        }
        return pautaResponseDto;
    }

    public static Pauta paraEntidade(PautaRequestDTO pautaRequestDTO) {
        var pauta = new Pauta();
        BeanUtils.copyProperties(pautaRequestDTO, pauta);
        return pauta;
    }

    public static List<PautaResponseDTO> paraListaDeResponseDto(List<Pauta> pautas) {
        return pautas.stream().map(PautaMapper::paraResponseDto).collect(Collectors.toList());
    }

}
