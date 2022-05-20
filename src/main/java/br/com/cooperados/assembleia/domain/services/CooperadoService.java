package br.com.cooperados.assembleia.domain.services;

import br.com.cooperados.assembleia.domain.models.Cooperado;

import java.util.UUID;

public interface CooperadoService {

    Cooperado cadastrar(Cooperado cooperado);

    Cooperado buscarPeloId(UUID idDoCooperado);

    Cooperado buscarPeloCpf(String cpf);

}
