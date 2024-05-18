package br.com.odontoflow.domain.professional;

import br.com.odontoflow.application.professional.ProfessionalFormDTO;
import br.com.odontoflow.domain.address.Address;
import br.com.odontoflow.domain.procedure.Procedure;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "Professional")
public class Professional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;
    @Column(name = "document", nullable = false, unique = true)
    private String document;
    @OneToOne
    private Address address;
    @Enumerated(EnumType.STRING)
    private ProfessionalType type;
    @ManyToMany
    @JoinTable(
        name = "Professional_Procedure",
        joinColumns = @JoinColumn(name = "professional_id"),
        inverseJoinColumns = @JoinColumn(name = "procedure_id"))
    private List<Procedure> procedures;

    @Deprecated(since = "Only for use of frameworks")
    public Professional() {
    }

    public Professional(ProfessionalFormDTO professionalFormDTO) {
        this.name = professionalFormDTO.name();
        this.document = professionalFormDTO.document();
        this.address = new Address(professionalFormDTO.address());
        this.type = professionalFormDTO.type();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDocument() {
        return document;
    }

    public Address getAddress() {
        return address;
    }

    public ProfessionalType getType() {
        return type;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Procedure> getProcedures() {
        return procedures;
    }

    public List<String> getProceduresNames() {
        return Optional.ofNullable(procedures)
                .map(procedureList -> procedureList.stream().map(Procedure::getName).toList())
                .orElse(List.of());
    }

    public void addProcedure(Procedure procedure) {
        this.procedures.add(procedure);
    }

    public void merge(ProfessionalFormDTO professionalFormDTO, List<Procedure> procedures) {
        this.name = professionalFormDTO.name();
        this.document = professionalFormDTO.document();
        this.type = professionalFormDTO.type();
        this.address.merge(professionalFormDTO.address());
        this.procedures = procedures;
    }
}
