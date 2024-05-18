package br.com.odontoflow.infrastructure.procedure;

import br.com.odontoflow.domain.procedure.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, Long> {

    Optional<Procedure> findByName(String name);

}
