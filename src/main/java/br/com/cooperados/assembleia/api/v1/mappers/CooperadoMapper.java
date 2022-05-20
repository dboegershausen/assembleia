package br.com.cooperados.assembleia.api.v1.mappers;

import br.com.cooperados.assembleia.api.v1.models.CooperadoRequestDTO;
import br.com.cooperados.assembleia.api.v1.models.CooperadoResponseDTO;
import br.com.cooperados.assembleia.domain.models.Cooperado;
import org.springframework.beans.BeanUtils;

public class CooperadoMapper {

    private CooperadoMapper() {}

    public static CooperadoResponseDTO paraResponseDto(Cooperado cooperado) {
        var cooperadoResponseDto = new CooperadoResponseDTO();
        BeanUtils.copyProperties(cooperado, cooperadoResponseDto);
        return cooperadoResponseDto;
    }

    public static Cooperado paraEntidade(CooperadoRequestDTO cooperadoRequestDTO) {
        var cooperado = new Cooperado();
        BeanUtils.copyProperties(cooperadoRequestDTO, cooperado);
        return cooperado;
    }

}
