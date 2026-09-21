package com.example.DeliveryAqui.controller;

import com.example.DeliveryAqui.dtos.person.PersonDetailResponse;
import com.example.DeliveryAqui.dtos.person.PersonPostRequest;
import com.example.DeliveryAqui.dtos.person.PersonPutRequest;
import com.example.DeliveryAqui.dtos.person.PersonResponse;
import com.example.DeliveryAqui.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public ResponseEntity<List<PersonResponse>> getAll() {
        return ResponseEntity.ok(personService.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PersonDetailResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(personService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PersonDetailResponse> create(@Valid @RequestBody PersonPostRequest request) {
        PersonDetailResponse response = personService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<PersonDetailResponse> update(@PathVariable Long id, @Valid @RequestBody PersonPutRequest request) {

        PersonDetailResponse response = personService.update(id,request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
