package br.com.odontologia.demo.repository;

import br.com.odontologia.demo.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
