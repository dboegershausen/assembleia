package br.com.cooperados.assembleia.domain.services;

import br.com.cooperados.assembleia.core.clients.InformacoesDoUsuarioClient;
import br.com.cooperados.assembleia.domain.enums.StatusDoUsuario;
import br.com.cooperados.assembleia.domain.models.InformacoesDoUsuario;
import br.com.cooperados.assembleia.domain.services.impl.InformacoesDoUsuarioServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InformacoesDoUsuarioServiceTest {

    @InjectMocks
    InformacoesDoUsuarioServiceImpl informacoesDoUsuarioService;

    @Mock
    InformacoesDoUsuarioClient informacoesDoUsuarioClient;

    @Test
    void deve_buscar_informacoes_do_usuario() {
        String cpfDoUsuario = "12312312312";
        var informacoesDoUsuario = informacoesDoUsuario();
        when(informacoesDoUsuarioClient.buscar(cpfDoUsuario)).thenReturn(informacoesDoUsuario);
        InformacoesDoUsuario informacoesRetornadas = informacoesDoUsuarioService.buscar(cpfDoUsuario);
        assertTrue(Objects.nonNull(informacoesRetornadas));
        assertEquals(informacoesDoUsuario.getStatus(), informacoesRetornadas.getStatus());
        verify(informacoesDoUsuarioClient, times(1)).buscar(cpfDoUsuario);
    }

    private InformacoesDoUsuario informacoesDoUsuario() {
        var informacoesDoUsuario = new InformacoesDoUsuario();
        informacoesDoUsuario.setStatus(StatusDoUsuario.ABLE_TO_VOTE);
        return informacoesDoUsuario;
    }

}
