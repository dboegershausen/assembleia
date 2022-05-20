package br.com.cooperados.assembleia.domain.services.impl;

import br.com.cooperados.assembleia.domain.enums.StatusDaVotacao;
import br.com.cooperados.assembleia.domain.exceptions.CooperadoInaptoParaVotarException;
import br.com.cooperados.assembleia.domain.exceptions.VotacaoEncerradaException;
import br.com.cooperados.assembleia.domain.exceptions.VotoDuplicadoException;
import br.com.cooperados.assembleia.domain.models.InformacoesDoUsuario;
import br.com.cooperados.assembleia.domain.enums.StatusDoUsuario;
import br.com.cooperados.assembleia.domain.models.Voto;
import br.com.cooperados.assembleia.domain.repositories.VotoRepository;
import br.com.cooperados.assembleia.domain.services.InformacoesDoUsuarioService;
import br.com.cooperados.assembleia.domain.services.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class VotoServiceImpl implements VotoService {

    private VotoRepository votoRepository;

    private InformacoesDoUsuarioService informacoesDoUsuarioService;

    @Autowired
    public VotoServiceImpl(VotoRepository votoRepository, InformacoesDoUsuarioService informacoesDoUsuarioService) {
        this.votoRepository = votoRepository;
        this.informacoesDoUsuarioService = informacoesDoUsuarioService;
    }

    @Override
    public void votar(Voto voto) {
        if (voto.getVotacao().getStatus().equals(StatusDaVotacao.FINALIZADA)) {
            throw new VotacaoEncerradaException(voto.getVotacao().getPauta().getId());
        }
        InformacoesDoUsuario informacoesDoUsuario = null;
        try {
            informacoesDoUsuario = informacoesDoUsuarioService.buscar(voto.getCooperado().getCpf());
        } catch (Exception e) {
            throw new CooperadoInaptoParaVotarException(voto.getCooperado().getId());
        }
        if (Objects.isNull(informacoesDoUsuario) || informacoesDoUsuario.getStatus().equals(StatusDoUsuario.UNABLE_TO_VOTE)) {
            throw new CooperadoInaptoParaVotarException(voto.getCooperado().getId());
        }
        if (votoRepository.findByCooperadoIdAndVotacaoId(voto.getCooperado().getId(), voto.getVotacao().getId()).isPresent()) {
            throw new VotoDuplicadoException(voto.getCooperado().getId(), voto.getVotacao().getPauta().getId());
        }
        votoRepository.save(voto);
    }

}
