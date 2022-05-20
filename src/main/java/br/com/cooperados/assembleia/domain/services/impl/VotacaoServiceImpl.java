package br.com.cooperados.assembleia.domain.services.impl;

import br.com.cooperados.assembleia.domain.enums.OpcaoDeVoto;
import br.com.cooperados.assembleia.domain.enums.ResultadoDaVotacao;
import br.com.cooperados.assembleia.domain.enums.StatusDaVotacao;
import br.com.cooperados.assembleia.domain.exceptions.VotacaoDuplicadaException;
import br.com.cooperados.assembleia.domain.exceptions.VotacaoEmAndamentoException;
import br.com.cooperados.assembleia.domain.exceptions.VotacaoNaoEncontradaException;
import br.com.cooperados.assembleia.domain.models.*;
import br.com.cooperados.assembleia.domain.repositories.VotacaoRepository;
import br.com.cooperados.assembleia.domain.services.VotacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VotacaoServiceImpl implements VotacaoService {

    private VotacaoRepository votacaoRepository;

    @Autowired
    public VotacaoServiceImpl(VotacaoRepository votacaoRepository) {
        this.votacaoRepository = votacaoRepository;
    }

    @Override
    public Votacao iniciar(Votacao votacao) {
        Optional<Votacao> votacaoExistente = votacaoRepository.findByPautaId(votacao.getPauta().getId());
        if (votacaoExistente.isPresent()) {
            if (votacaoExistente.get().getStatus().equals(StatusDaVotacao.INICIADA)) {
                throw new VotacaoEmAndamentoException(votacao.getPauta().getId());
            } else {
                throw new VotacaoDuplicadaException(votacao.getPauta().getId());
            }
        }
        votacao.setStatus(StatusDaVotacao.INICIADA);
        return votacaoRepository.save(votacao);
    }

    @Override
    public void encerrar(UUID idDaVotacao) {
        var votacao = buscarPeloId(idDaVotacao);
        votacao.setStatus(StatusDaVotacao.FINALIZADA);
        votacaoRepository.save(votacao);
    }

    @Override
    public Votacao apurar(UUID idDaVotacao) {
        var votacao = buscarPeloId(idDaVotacao);
        Integer numeroDeVotosAFavor = votacao.getVotos().stream().filter(voto -> OpcaoDeVoto.SIM.equals(voto.getOpcao())).collect(Collectors.toList()).size();
        Integer numeroDeVotosContrarios = votacao.getVotos().stream().filter(voto -> OpcaoDeVoto.NAO.equals(voto.getOpcao())).collect(Collectors.toList()).size();
        if (numeroDeVotosAFavor > numeroDeVotosContrarios) {
            votacao.setResultado(ResultadoDaVotacao.APROVADA);
        } else if (numeroDeVotosContrarios > numeroDeVotosAFavor) {
            votacao.setResultado(ResultadoDaVotacao.REPROVADA);
        } else {
            votacao.setResultado(ResultadoDaVotacao.EMPATADA);
        }
        votacao.setVotosAFavor(numeroDeVotosAFavor);
        votacao.setVotosContrarios(numeroDeVotosContrarios);
        return votacaoRepository.save(votacao);
    }

    @Override
    public List<Votacao> buscarVotacoesEmAndamentoComTempoEsgotado() {
        return votacaoRepository.findAllByStatus(StatusDaVotacao.INICIADA).stream().filter(votacao ->
                votacao.getFim().isBefore(LocalDateTime.now())).collect(Collectors.toList());
    }

    @Override
    public Votacao buscarPeloId(UUID idDaVotacao) {
        return votacaoRepository.findById(idDaVotacao).orElseThrow(() -> new VotacaoNaoEncontradaException(idDaVotacao));
    }

}
