package br.com.cooperados.assembleia.domain.services.impl;

import br.com.cooperados.assembleia.domain.exceptions.PautaNaoEncontradaException;
import br.com.cooperados.assembleia.domain.models.Pauta;
import br.com.cooperados.assembleia.domain.repositories.PautaRepository;
import br.com.cooperados.assembleia.domain.services.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PautaServiceImpl implements PautaService {

    private PautaRepository pautaRepository;

    @Autowired
    public PautaServiceImpl(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    @Override
    public Pauta cadastrar(Pauta pauta) {
        return pautaRepository.save(pauta);
    }

    @Override
    public Pauta buscar(UUID idDaPauta) {
        return pautaRepository.findById(idDaPauta)
                .orElseThrow(() -> new PautaNaoEncontradaException(idDaPauta));
    }

    @Override
    public List<Pauta> buscarTodas() {
        return pautaRepository.findAll();
    }

}
