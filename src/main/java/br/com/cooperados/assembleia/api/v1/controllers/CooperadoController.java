package br.com.cooperados.assembleia.api.v1.controllers;

import br.com.cooperados.assembleia.api.v1.models.CooperadoRequestDTO;
import br.com.cooperados.assembleia.api.v1.models.CooperadoResponseDTO;
import br.com.cooperados.assembleia.api.v1.mappers.CooperadoMapper;
import br.com.cooperados.assembleia.domain.services.CooperadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/cooperados")
public class CooperadoController {

    private CooperadoService cooperadoService;

    @Autowired
    public CooperadoController(CooperadoService cooperadoService) {
        this.cooperadoService = cooperadoService;
    }

    @PostMapping
    public ResponseEntity<CooperadoResponseDTO> cadastrarCooperado(@RequestBody @Valid CooperadoRequestDTO cooperadoRequestDto) {
        var cooperado = CooperadoMapper.paraEntidade(cooperadoRequestDto);
        var cooperadoResponseDto = CooperadoMapper.paraResponseDto(cooperadoService.cadastrar(cooperado));
        return ResponseEntity.status(HttpStatus.CREATED).body(cooperadoResponseDto);
    }

}
