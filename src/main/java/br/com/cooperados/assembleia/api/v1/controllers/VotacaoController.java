package br.com.cooperados.assembleia.api.v1.controllers;

import br.com.cooperados.assembleia.api.v1.models.VotacaoRequestDTO;
import br.com.cooperados.assembleia.api.v1.models.VotacaoResponseDTO;
import br.com.cooperados.assembleia.api.v1.models.VotoRequestDTO;
import br.com.cooperados.assembleia.api.v1.mappers.VotacaoMapper;
import br.com.cooperados.assembleia.api.v1.mappers.VotoMapper;
import br.com.cooperados.assembleia.domain.services.CooperadoService;
import br.com.cooperados.assembleia.domain.services.PautaService;
import br.com.cooperados.assembleia.domain.services.VotacaoService;
import br.com.cooperados.assembleia.domain.services.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/v1/votacoes")
public class VotacaoController {

    private VotacaoService votacaoService;

    private VotoService votoService;

    private CooperadoService cooperadoService;

    private PautaService pautaService;

    @Autowired
    public VotacaoController(VotacaoService votacaoService, VotoService votoService, CooperadoService cooperadoService, PautaService pautaService) {
        this.votoService = votoService;
        this.votacaoService = votacaoService;
        this.cooperadoService = cooperadoService;
        this.pautaService = pautaService;
    }

    @PostMapping
    public ResponseEntity<VotacaoResponseDTO> iniciarVotacao(@RequestBody @Valid VotacaoRequestDTO votacaoRequestDTO) {
        var pauta = pautaService.buscar(UUID.fromString(votacaoRequestDTO.getPauta()));
        var votacao = VotacaoMapper.paraEntidade(votacaoRequestDTO, pauta);
        var votacaoResponseDto = VotacaoMapper.paraResponseDto(votacaoService.iniciar(votacao));
        return ResponseEntity.status(HttpStatus.CREATED).body(votacaoResponseDto);
    }

    @PostMapping("/{idDaVotacao}/votos")
    public ResponseEntity<Object> votar(@RequestBody @Valid VotoRequestDTO votoRequestDTO, @PathVariable("idDaVotacao") UUID idDaVotacao) {
        var votacao = votacaoService.buscarPeloId(idDaVotacao);
        var cooperado = cooperadoService.buscarPeloCpf(votoRequestDTO.getCpf());
        var voto = VotoMapper.paraEntidade(votoRequestDTO, cooperado, votacao);
        votoService.votar(voto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
