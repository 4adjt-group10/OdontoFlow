package br.com.odontologia.demo.domain.professional;

import br.com.odontologia.demo.dto.professional.ProfessionalFormDTO;
import br.com.odontologia.demo.domain.Address;
import jakarta.persistence.*;

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
}
