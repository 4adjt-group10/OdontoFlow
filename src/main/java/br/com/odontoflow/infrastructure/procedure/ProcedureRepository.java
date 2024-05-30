package br.com.odontoflow.infrastructure.procedure;

import br.com.odontoflow.domain.procedure.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, UUID> {

    Optional<Procedure> findByName(String name);

    @Query(value = """
            SELECT * FROM Procedure p 
                JOIN Professional_Procedure pp ON p.id = pp.procedure_id
            WHERE p.id = :id 
            AND pp.professional_id = :professionalId
    """, nativeQuery = true)
    Optional<Procedure> findByIdAndProfessionalId(UUID id, UUID professionalId);
}
