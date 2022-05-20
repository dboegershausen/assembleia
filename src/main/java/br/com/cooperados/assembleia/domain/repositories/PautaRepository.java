package br.com.cooperados.assembleia.domain.repositories;

import br.com.cooperados.assembleia.domain.models.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PautaRepository extends JpaRepository<Pauta, UUID> {
}
