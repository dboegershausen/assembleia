package br.com.cooperados.assembleia.domain.services.impl;

import br.com.cooperados.assembleia.core.clients.InformacoesDoUsuarioClient;
import br.com.cooperados.assembleia.domain.models.InformacoesDoUsuario;
import br.com.cooperados.assembleia.domain.services.InformacoesDoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InformacoesDoUsuarioServiceImpl implements InformacoesDoUsuarioService {

    private InformacoesDoUsuarioClient informacoesDoUsuarioClient;

    @Autowired
    public InformacoesDoUsuarioServiceImpl(InformacoesDoUsuarioClient informacoesDoUsuarioClient) {
        this.informacoesDoUsuarioClient = informacoesDoUsuarioClient;
    }

    @Override
    public InformacoesDoUsuario buscar(String cpf) {
        return informacoesDoUsuarioClient.buscar(cpf);
    }

}
