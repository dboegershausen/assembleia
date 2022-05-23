package br.com.cooperados.assembleia.domain.services;

import br.com.cooperados.assembleia.api.v1.models.PautaResponseDTO;

public interface NotificacaoService {

    void notificar(PautaResponseDTO mensagem);

}
