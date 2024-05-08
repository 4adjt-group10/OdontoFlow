package br.com.odontoflow.repository;

import br.com.odontoflow.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
