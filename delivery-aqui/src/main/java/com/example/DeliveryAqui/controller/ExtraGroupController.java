package com.example.DeliveryAqui.controller;

import com.example.DeliveryAqui.dtos.extraGroup.*;
import com.example.DeliveryAqui.dtos.shared.PaginatedResponse;
import com.example.DeliveryAqui.enums.ExtraGroupSortField;
import com.example.DeliveryAqui.service.ExtraGroupService;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/extra-group")
public class ExtraGroupController {
    private final ExtraGroupService extraGroupService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<ExtraGroupSummaryResponse>> findAll(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ID") ExtraGroupSortField sortField,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(direction, sortField.getField()));
        Page<ExtraGroupSummaryResponse> page = extraGroupService.findAll(pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(PaginationUtils.buildPaginatedResponse(page));
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<ExtraGroupResponse> findById(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(extraGroupService.findById(id));
    }

    @GetMapping(path = "/name/{name}")
    public ResponseEntity<PaginatedResponse<ExtraGroupSummaryResponse>> findByName(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ID") ExtraGroupSortField sortField,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction,
            @PathVariable String name
    ) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(direction, sortField.getField()));
        Page<ExtraGroupSummaryResponse> page = extraGroupService.findByName(name, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(PaginationUtils.buildPaginatedResponse(page));
    }

    @GetMapping(path = "/store/{storeId}")
    public ResponseEntity<PaginatedResponse<ExtraGroupDetailResponse>> findByStore(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ID") ExtraGroupSortField sortField,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction,
            @PathVariable Long storeId
    ) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(direction, sortField.getField()));
        Page<ExtraGroupDetailResponse> page = extraGroupService.findByStore(storeId, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(PaginationUtils.buildPaginatedResponse(page));
    }

    @PostMapping
    public ResponseEntity<ExtraGroupResponse> post(@RequestBody @Valid ExtraGroupPostRequest postRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(extraGroupService.save(postRequest));
    }

    @PostMapping(path = "/batch")
    public ResponseEntity<List<ExtraGroupDetailResponse>>post(@RequestBody @Valid ExtraGroupBatchPostRequest postRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(extraGroupService.saveAll(postRequest));
    }

    @PutMapping
    public ResponseEntity<ExtraGroupResponse> put(@RequestParam Long id, @RequestBody @Valid ExtraGroupPutRequest putRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(extraGroupService.update(id, putRequest));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long id) {
        extraGroupService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
