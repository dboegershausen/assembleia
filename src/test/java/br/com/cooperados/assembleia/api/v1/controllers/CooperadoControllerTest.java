package br.com.cooperados.assembleia.api.v1.controllers;

import br.com.cooperados.assembleia.api.v1.models.CooperadoRequestDTO;
import br.com.cooperados.assembleia.domain.models.Cooperado;
import br.com.cooperados.assembleia.domain.services.CooperadoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CooperadoControllerTest {

    @InjectMocks
    CooperadoController cooperadoController;

    @Mock
    CooperadoService cooperadoService;

    @Test
    void deve_cadastrar_cooperado() {
        var cooperado = cooperado();
        var cooperadoRequestDto = cooperadoRequestDto();
        when(cooperadoService.cadastrar(any(Cooperado.class))).thenReturn(cooperado);
        var cooperadoResponse = cooperadoController.cadastrarCooperado(cooperadoRequestDto());
        cooperado.setId(null);
        assertNotNull(cooperadoResponse.getBody());
        assertEquals(HttpStatus.CREATED, cooperadoResponse.getStatusCode());
        assertEquals(cooperadoRequestDto.getNome(), cooperadoResponse.getBody().getNome());
        assertEquals(cooperadoRequestDto.getCpf(), cooperadoResponse.getBody().getCpf());
    }

    private Cooperado cooperado() {
        var cooperado = new Cooperado();
        cooperado.setId(UUID.randomUUID());
        cooperado.setCpf("23541240075");
        cooperado.setNome("João da Silva");
        return cooperado;
    }

    private CooperadoRequestDTO cooperadoRequestDto() {
        var cooperadoRequestDto = new CooperadoRequestDTO();
        cooperadoRequestDto.setCpf("23541240075");
        cooperadoRequestDto.setNome("João da Silva");
        return cooperadoRequestDto;
    }

}
