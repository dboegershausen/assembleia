package br.com.cooperados.assembleia.domain.services.impl;

import br.com.cooperados.assembleia.domain.services.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoServiceImpl implements NotificacaoService {

    private KafkaTemplate<String, String> notificador;

    public static final String TOPICO_VOTACAO_ENCERRADA = "votacao_encerrada";

    @Autowired
    public NotificacaoServiceImpl(KafkaTemplate<String, String> notificador) {
        this.notificador = notificador;
    }

    @Override
    public void notificar(String mensagem) {
        notificador.send(TOPICO_VOTACAO_ENCERRADA, mensagem);
    }

}
