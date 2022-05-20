package br.com.cooperados.assembleia.domain.services.impl;

import br.com.cooperados.assembleia.domain.exceptions.CooperadoNaoEncontradoException;
import br.com.cooperados.assembleia.domain.exceptions.CpfDuplicadoException;
import br.com.cooperados.assembleia.domain.models.Cooperado;
import br.com.cooperados.assembleia.domain.repositories.CooperadoRepository;
import br.com.cooperados.assembleia.domain.services.CooperadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CooperadoServiceImpl implements CooperadoService {

    private CooperadoRepository cooperadoRepository;

    @Autowired
    public CooperadoServiceImpl(CooperadoRepository cooperadoRepository) {
        this.cooperadoRepository = cooperadoRepository;
    }

    @Override
    public Cooperado cadastrar(Cooperado cooperado) {
        Optional<Cooperado> cooperadoOptional = cooperadoRepository.findByCpf(cooperado.getCpf());
        if (cooperadoOptional.isPresent()) {
            throw new CpfDuplicadoException(cooperado.getCpf());
        }
        return cooperadoRepository.save(cooperado);
    }

    @Override
    public Cooperado buscarPeloId(UUID idDoCooperado) {
        return cooperadoRepository.findById(idDoCooperado).orElseThrow(() -> new CooperadoNaoEncontradoException(idDoCooperado));
    }

    @Override
    public Cooperado buscarPeloCpf(String cpf) {
        return cooperadoRepository.findByCpf(cpf).orElseThrow(() -> new CooperadoNaoEncontradoException(cpf));
    }

}
