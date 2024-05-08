package br.com.odontoflow.repository;

import br.com.odontoflow.domain.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, Long> {

    Optional<Procedure> findByNome(String name);

    public boolean existsByNome(String name);
}
