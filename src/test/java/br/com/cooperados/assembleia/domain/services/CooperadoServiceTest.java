package br.com.cooperados.assembleia.domain.services;

import br.com.cooperados.assembleia.domain.exceptions.CooperadoNaoEncontradoException;
import br.com.cooperados.assembleia.domain.exceptions.CpfDuplicadoException;
import br.com.cooperados.assembleia.domain.models.Cooperado;
import br.com.cooperados.assembleia.domain.repositories.CooperadoRepository;
import br.com.cooperados.assembleia.domain.services.impl.CooperadoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class CooperadoServiceTest {

    @InjectMocks
    CooperadoServiceImpl cooperadoService;

    @Mock
    CooperadoRepository cooperadoRepository;

    @Test
    void deve_cadastrar_cooperado() {
        var cooperado = cooperado();
        cooperadoService.cadastrar(cooperado);
        verify(cooperadoRepository, times(1)).save(cooperado);
    }

    @Test
    void nao_deve_cadastrar_cooperado_com_cpf_repetido() {
        var cooperado = cooperado();
        when(cooperadoRepository.findByCpf(cooperado.getCpf())).thenReturn(Optional.of(cooperado));
        CpfDuplicadoException excessao = assertThrows(CpfDuplicadoException.class, () ->
            cooperadoService.cadastrar(cooperado)
        );
        assertEquals(String.format("O cpf %s já está em uso.", cooperado.getCpf()), excessao.getMessage());
        verify(cooperadoRepository, times(1)).findByCpf(cooperado.getCpf());
    }

    @Test
    void deve_buscar_cooperado_pelo_id() {
        var cooperado = cooperado();
        when(cooperadoRepository.findById(cooperado.getId())).thenReturn(Optional.of(cooperado));
        var cooperadoRetornado = cooperadoService.buscarPeloId(cooperado.getId());
        assertTrue(Objects.nonNull(cooperadoRetornado));
        verify(cooperadoRepository, times(1)).findById(cooperado.getId());
    }

    @Test
    void deve_lancar_cooperado_nao_encontrado_para_id_invalido() {
        UUID idDoCooperado = UUID.randomUUID();
        when(cooperadoRepository.findById(idDoCooperado)).thenReturn(Optional.empty());
        CooperadoNaoEncontradoException excessao = assertThrows(CooperadoNaoEncontradoException.class, () ->
                cooperadoService.buscarPeloId(idDoCooperado)
        );
        assertEquals(String.format("Cooperado com o id %s não encontrado.", idDoCooperado), excessao.getMessage());
        verify(cooperadoRepository, times(1)).findById(idDoCooperado);
    }

    @Test
    void deve_buscar_cooperado_pelo_cpf() {
        var cooperado = cooperado();
        when(cooperadoRepository.findByCpf(cooperado.getCpf())).thenReturn(Optional.of(cooperado));
        var cooperadoRetornado = cooperadoService.buscarPeloCpf(cooperado.getCpf());
        assertTrue(Objects.nonNull(cooperadoRetornado));
        verify(cooperadoRepository, times(1)).findByCpf(cooperado.getCpf());
    }

    @Test
    void deve_lancar_cooperado_nao_encontrado_para_cpf_invalido() {
        String cpfDoCooperado = "12312312312";
        when(cooperadoRepository.findByCpf(cpfDoCooperado)).thenReturn(Optional.empty());
        CooperadoNaoEncontradoException excessao = assertThrows(CooperadoNaoEncontradoException.class, () ->
                cooperadoService.buscarPeloCpf(cpfDoCooperado)
        );
        assertEquals(String.format("Cooperado com o cpf %s não encontrado.", cpfDoCooperado), excessao.getMessage());
        verify(cooperadoRepository, times(1)).findByCpf(cpfDoCooperado);
    }

    private Cooperado cooperado() {
        var cooperado = new Cooperado();
        cooperado.setId(UUID.randomUUID());
        cooperado.setCpf("23541240075");
        cooperado.setNome("João da Silva");
        return cooperado;
    }

}
