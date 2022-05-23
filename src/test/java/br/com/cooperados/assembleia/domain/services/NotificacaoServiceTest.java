package br.com.cooperados.assembleia.domain.services;

import br.com.cooperados.assembleia.api.v1.models.PautaResponseDTO;
import br.com.cooperados.assembleia.domain.services.impl.NotificacaoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacaoServiceTest {

    @InjectMocks
    NotificacaoServiceImpl notificacaoService;

    @Mock
    KafkaTemplate<String, Object> notificador;

    @Test
    void deve_notificar_encerramento_da_votacao() {
        var votacaoResponse = new PautaResponseDTO();
        notificacaoService.notificar(votacaoResponse);
        verify(notificador, times(1)).send(NotificacaoServiceImpl.TOPICO_VOTACAO_ENCERRADA, votacaoResponse);
    }

 }
