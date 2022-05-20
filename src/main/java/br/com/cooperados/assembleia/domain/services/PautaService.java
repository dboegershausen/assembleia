package br.com.cooperados.assembleia.domain.services;

import br.com.cooperados.assembleia.domain.models.Pauta;

import java.util.List;
import java.util.UUID;

public interface PautaService {

    Pauta cadastrar(Pauta pauta);

    Pauta buscar(UUID idDaPauta);

    List<Pauta> buscarTodas();

}
