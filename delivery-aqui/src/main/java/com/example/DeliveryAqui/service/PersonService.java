package com.example.DeliveryAqui.service;

import com.example.DeliveryAqui.dtos.person.PersonDetailResponse;
import com.example.DeliveryAqui.dtos.person.PersonPostRequest;
import com.example.DeliveryAqui.dtos.person.PersonPutRequest;
import com.example.DeliveryAqui.dtos.person.PersonResponse;
import com.example.DeliveryAqui.mapper.PersonMapper;
import com.example.DeliveryAqui.model.entity.Person;
import com.example.DeliveryAqui.repository.CustomerRepository;
import com.example.DeliveryAqui.repository.DeliveryDriverRepository;
import com.example.DeliveryAqui.repository.PersonRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public List<PersonResponse> findAll() {

        return personRepository.findAll()
                .stream()
                .map(personMapper::entityToResponse).toList();
    }

    public PersonDetailResponse findById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + id));

        return personMapper.entityToDetailResponse(person);
    }

    public PersonDetailResponse create(PersonPostRequest request) {
        checkCpfAvailability(request.cpf(), null);
        checkEmailAvailability(request.email(), null);
        Person person = personMapper.postRequestToEntity(request);

        person.setCreatedAt(Instant.now());
        person.setUpdatedAt(Instant.now());
        personRepository.save(person);

        return personMapper.entityToDetailResponse(person);
    }

    public PersonDetailResponse update(Long id, PersonPutRequest request) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person with id " + id + " not found"));

        checkCpfAvailability(request.cpf(), person.getId());
        checkEmailAvailability(request.email(), person.getId());

        personMapper.updateEntity(request, person);
        person.setUpdatedAt(Instant.now());
        personRepository.save(person);

        return personMapper.entityToDetailResponse(person);
    }

    private void checkEmailAvailability(String newEmail, Long personId) {
        Person existingByEmail = personRepository.findByEmail(newEmail);

        if (existingByEmail == null)
            return;

        if (personId == null || !existingByEmail.getId().equals(personId))
            throw new EntityExistsException("This email already belongs to another Person");

    }

    private void checkCpfAvailability(String newCpf, Long personId) {
        Person existingByCpf = personRepository.findByCpf(newCpf);

        if (existingByCpf == null)
            return;

        if (personId == null || !existingByCpf.getId().equals(personId))
            throw new EntityExistsException("This CPF already belongs to another Person");
    }

    public void delete(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + id));

        /* -- Uncomment this when customer logic is done --
        Customer customer = customerRepository.findByPersonId(id);
        if (customer != null)
            throw new IllegalArgumentException("Cannot delete person with id " + id + ". There is a customer related to it");
         */

        personRepository.delete(person);
    }
}
