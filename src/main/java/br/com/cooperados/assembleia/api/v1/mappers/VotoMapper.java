package br.com.cooperados.assembleia.api.v1.mappers;

import br.com.cooperados.assembleia.api.v1.models.VotoRequestDTO;
import br.com.cooperados.assembleia.domain.enums.OpcaoDeVoto;
import br.com.cooperados.assembleia.domain.models.Cooperado;
import br.com.cooperados.assembleia.domain.models.Votacao;
import br.com.cooperados.assembleia.domain.models.Voto;
import org.springframework.beans.BeanUtils;

public class VotoMapper {

    private VotoMapper() {}

    public static Voto paraEntidade(VotoRequestDTO votacaoRequestDto, Cooperado cooperado, Votacao votacao) {
        var voto = new Voto();
        BeanUtils.copyProperties(votacaoRequestDto, voto);
        voto.setCooperado(cooperado);
        voto.setVotacao(votacao);
        voto.setOpcao(OpcaoDeVoto.valueOf(votacaoRequestDto.getOpcao().toUpperCase()));
        return voto;
    }

}
