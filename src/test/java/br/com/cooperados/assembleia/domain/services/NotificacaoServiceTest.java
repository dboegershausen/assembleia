package br.com.cooperados.assembleia.domain.services;

import br.com.cooperados.assembleia.api.v1.models.VotacaoResponseDTO;
import br.com.cooperados.assembleia.domain.enums.StatusDaVotacao;
import br.com.cooperados.assembleia.domain.services.impl.NotificacaoServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
class NotificacaoServiceTest {

    @InjectMocks
    NotificacaoServiceImpl notificacaoService;

    @Mock
    KafkaTemplate<String, Object> notificador;

    @Test
    void deve_notificar_encerramento_da_votacao() throws JsonProcessingException {
        var votacaoResponse = votacaoResponse();
        notificacaoService.notificar("Votação encerrada.");
        verify(notificador, times(1)).send(NotificacaoServiceImpl.TOPICO_VOTACAO_ENCERRADA, "Votação encerrada.");
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
