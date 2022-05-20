package br.com.cooperados.assembleia.api.v1.controllers;

import br.com.cooperados.assembleia.api.v1.models.PautaRequestDTO;
import br.com.cooperados.assembleia.api.v1.models.PautaResponseDTO;
import br.com.cooperados.assembleia.api.v1.mappers.PautaMapper;
import br.com.cooperados.assembleia.domain.services.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/pautas")
public class PautaController {

    private PautaService pautaService;

    @Autowired
    public PautaController(PautaService pautaService) {
        this.pautaService = pautaService;
    }

    @PostMapping
    public ResponseEntity<PautaResponseDTO> cadastrarPauta(@RequestBody @Valid PautaRequestDTO pautaRequestDto) {
        var pauta = PautaMapper.paraEntidade(pautaRequestDto);
        var pautaResponseDto = PautaMapper.paraResponseDto(pautaService.cadastrar(pauta));
        return ResponseEntity.status(HttpStatus.CREATED).body(pautaResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<PautaResponseDTO>> buscarTodasAsPautas() {
        var pautas = pautaService.buscarTodas();
        return ResponseEntity.status(HttpStatus.OK).body(PautaMapper.paraListaDeResponseDto(pautas));
    }

    @GetMapping("/{idDaPauta}")
    public ResponseEntity<PautaResponseDTO> buscarPauta(@PathVariable(value = "idDaPauta") UUID idDaPauta) {
        var pauta = pautaService.buscar(idDaPauta);
        return ResponseEntity.status(HttpStatus.OK).body(PautaMapper.paraResponseDto(pauta));
    }

}
