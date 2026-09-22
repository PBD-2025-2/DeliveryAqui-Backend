package com.example.DeliveryAqui.repository;

import com.example.DeliveryAqui.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByCpf(String cpf);
    Person findByEmail(String email);
}
