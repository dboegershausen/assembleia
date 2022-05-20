package br.com.cooperados.assembleia.core.schedules;

import br.com.cooperados.assembleia.api.v1.mappers.VotacaoMapper;
import br.com.cooperados.assembleia.domain.services.NotificacaoService;
import br.com.cooperados.assembleia.domain.services.VotacaoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VotacaoTask {

    private static final Logger log = LoggerFactory.getLogger(VotacaoTask.class);

    private VotacaoService votacaoService;

    private NotificacaoService notificacaoService;

    @Autowired
    public VotacaoTask(VotacaoService votacaoService, NotificacaoService notificacaoService) {
        this.votacaoService = votacaoService;
        this.notificacaoService = notificacaoService;
    }

    @Scheduled(fixedDelay = 1000)
    public void encerrarVotacao() {
        var votacoesEmAndamento = votacaoService.buscarVotacoesEmAndamentoComTempoEsgotado();
        votacoesEmAndamento.stream().forEach(
                votacao -> {
                    log.info("Encerrando votacao da pauta {}.", votacao.getPauta().getId());
                    votacaoService.encerrar(votacao.getId());
                    var votacaoApurada = votacaoService.apurar(votacao.getId());
                    notificacaoService.notificar(String.format("A votação %s da pauta %s foi encerrada.", votacao.getId(), votacao.getPauta().getId()));
                }
        );
    }

}
