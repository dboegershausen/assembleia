package br.com.cooperados.assembleia.domain.repositories;

import br.com.cooperados.assembleia.domain.models.Cooperado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CooperadoRepository extends JpaRepository<Cooperado, UUID> {

    Optional<Cooperado> findByCpf(String cpf);

}
