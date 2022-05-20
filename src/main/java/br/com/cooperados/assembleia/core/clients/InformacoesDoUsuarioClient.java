package br.com.cooperados.assembleia.core.clients;

import br.com.cooperados.assembleia.domain.models.InformacoesDoUsuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "userInfo", url = "https://user-info.herokuapp.com/")
public interface InformacoesDoUsuarioClient {


    @RequestMapping(method = RequestMethod.GET, value = "/users/{cpf}", produces = "application/json")
    InformacoesDoUsuario buscar(@PathVariable("cpf") String cpf);

}
