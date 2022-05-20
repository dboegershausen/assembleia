package br.com.cooperados.assembleia.domain.repositories;

import br.com.cooperados.assembleia.domain.enums.StatusDaVotacao;
import br.com.cooperados.assembleia.domain.models.Votacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VotacaoRepository extends JpaRepository<Votacao, UUID> {

    Optional<Votacao> findByPautaId(UUID pautaId);

    List<Votacao> findAllByStatus(StatusDaVotacao statusDaVotacao);

}
