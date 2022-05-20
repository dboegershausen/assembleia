package br.com.cooperados.assembleia.core.clients;

import br.com.cooperados.assembleia.domain.models.InformacoesDoUsuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "userInfo", url = "https://user-info.herokuapp.com/")
public interface InformacoesDoUsuarioClient {

    @GetMapping(value = "/users/{cpf}", produces = "application/json")
    InformacoesDoUsuario buscar(@PathVariable("cpf") String cpf);

}
