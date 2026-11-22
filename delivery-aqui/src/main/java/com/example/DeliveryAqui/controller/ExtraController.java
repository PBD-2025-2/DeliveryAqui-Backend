package com.example.DeliveryAqui.controller;

import com.example.DeliveryAqui.dtos.extra.ExtraPostRequest;
import com.example.DeliveryAqui.dtos.extra.ExtraPutRequest;
import com.example.DeliveryAqui.dtos.extra.ExtraResponse;
import com.example.DeliveryAqui.dtos.extra.ExtraSummaryResponse;
import com.example.DeliveryAqui.dtos.shared.PaginatedResponse;
import com.example.DeliveryAqui.enums.ExtraSortField;
import com.example.DeliveryAqui.service.ExtraService;
import com.example.DeliveryAqui.util.PaginationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/extra")
public class ExtraController {

    private final ExtraService extraService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<ExtraSummaryResponse>> findAll(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ID") ExtraSortField sortField,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(direction, sortField.getField()));
        Page<ExtraSummaryResponse> page = extraService.findAll(pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(PaginationUtils.buildPaginatedResponse(page));
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<ExtraResponse> findById(@PathVariable Long id) throws Exception {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(extraService.findById(id));
    }

    @GetMapping(path= "name/{name}")
    public ResponseEntity<PaginatedResponse<ExtraSummaryResponse>> findByName(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ID") ExtraSortField sortField,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction,
            @PathVariable String name
    ) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(direction, sortField.getField()));
        Page<ExtraSummaryResponse> page = extraService.findByName(name, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(PaginationUtils.buildPaginatedResponse(page));
    }

    @GetMapping(path= "store/{storeId}/extra-group/{id}")
    public ResponseEntity<PaginatedResponse<ExtraSummaryResponse>> findByExtraGroup(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ID") ExtraSortField sortField,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction,
            @PathVariable Long storeId,
            @PathVariable Long id
    ) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(direction, sortField.getField()));
        Page<ExtraSummaryResponse> page = extraService.findByExtraGroup(id, storeId, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(PaginationUtils.buildPaginatedResponse(page));
    }

    @PostMapping
    public ResponseEntity<ExtraResponse> post(@RequestBody @Valid ExtraPostRequest postRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(extraService.save(postRequest));
    }

    @PutMapping
    public ResponseEntity<ExtraResponse> put(@RequestParam Long id, @RequestBody @Valid ExtraPutRequest putRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(extraService.update(id, putRequest));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long id) {
        extraService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
