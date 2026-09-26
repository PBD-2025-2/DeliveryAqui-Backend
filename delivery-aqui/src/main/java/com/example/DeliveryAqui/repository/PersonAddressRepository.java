package com.example.DeliveryAqui.repository;

import com.example.DeliveryAqui.model.entity.PersonAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonAddressRepository extends JpaRepository<PersonAddress, Long> {
    List<PersonAddress> findByPerson_Cpf(String cpf);
    Optional<PersonAddress> findByPerson_IdAndIsFavoriteTrue(Long personId);
    boolean existsByPerson_IdAndAddress_Id(Long personId, Long addressId);
    int countByPerson_Id(Long personId);
}
