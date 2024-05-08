package br.com.odontologia.demo.repository;

import br.com.odontologia.demo.domain.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, Long> {

    Optional<Procedure> findByNome(String name);

    public boolean existsByNome(String name);
}
