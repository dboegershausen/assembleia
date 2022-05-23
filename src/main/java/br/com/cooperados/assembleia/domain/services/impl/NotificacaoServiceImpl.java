package br.com.cooperados.assembleia.domain.services.impl;

import br.com.cooperados.assembleia.api.v1.models.PautaResponseDTO;
import br.com.cooperados.assembleia.domain.services.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoServiceImpl implements NotificacaoService {

    private KafkaTemplate<String, PautaResponseDTO> notificador;

    public static final String TOPICO_VOTACAO_ENCERRADA = "votacao_encerrada";

    @Autowired
    public NotificacaoServiceImpl(KafkaTemplate<String, PautaResponseDTO> notificador) {
        this.notificador = notificador;
    }

    @Override
    public void notificar(PautaResponseDTO mensagem) {
        notificador.send(TOPICO_VOTACAO_ENCERRADA, mensagem);
    }

}
