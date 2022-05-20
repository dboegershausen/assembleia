package br.com.cooperados.assembleia.domain.repositories;

import br.com.cooperados.assembleia.domain.models.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VotoRepository extends JpaRepository<Voto, UUID> {

    Optional<Voto> findByCooperadoIdAndVotacaoId(UUID idDoCooperado, UUID idDaVotacao);

}
