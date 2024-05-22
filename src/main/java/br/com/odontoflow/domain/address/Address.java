package br.com.odontoflow.domain.address;

import br.com.odontoflow.application.address.AddressFormDTO;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "Address")
public class Address {
//TODO: Tornar embeddable para paciente e profissional? (deixará de ser uma entidade)
    @Id
    private UUID id;
    private String street;
    private int number;
    private String neighborhood;
    private String city;
    private String state;

    @Deprecated(since = "Only for use of frameworks")
    public Address() {
    }

    public Address(AddressFormDTO formDTO) {
        this.id = UUID.randomUUID();
        this.neighborhood = formDTO.neighborhood();
        this.city = formDTO.city();
        this.state = formDTO.state();
        this.number = formDTO.number();
        this.street = formDTO.street();
    }

    public UUID getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public int getNumber() {
        return number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public void merge(AddressFormDTO formDTO) {
        this.neighborhood = formDTO.neighborhood();
        this.city = formDTO.city();
        this.state = formDTO.state();
        this.number = formDTO.number();
        this.street = formDTO.street();
    }
}
