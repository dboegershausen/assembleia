package br.com.cooperados.assembleia.domain.services;

import br.com.cooperados.assembleia.api.v1.models.VotacaoResponseDTO;
import br.com.cooperados.assembleia.domain.enums.StatusDaVotacao;
import br.com.cooperados.assembleia.domain.services.impl.NotificacaoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificacaoServiceTest {

    @InjectMocks
    NotificacaoServiceImpl notificacaoService;

    @Mock
    KafkaTemplate<String, Object> notificador;

    @Test
    void deve_notificar_encerramento_da_votacao() {
        var votacaoResponse = votacaoResponse();
        notificacaoService.notificar(votacaoResponse);
        verify(notificador, times(1)).send(NotificacaoServiceImpl.TOPICO_VOTACAO_ENCERRADA, votacaoResponse);
    }


    private VotacaoResponseDTO votacaoResponse() {
        var votacaoResponse = new VotacaoResponseDTO();
        votacaoResponse.setId(UUID.randomUUID());
        votacaoResponse.setInicio(LocalDateTime.now());
        votacaoResponse.setFim(LocalDateTime.now().plusMinutes(2));
        votacaoResponse.setStatus(StatusDaVotacao.INICIADA);
        return votacaoResponse;
    }

}
