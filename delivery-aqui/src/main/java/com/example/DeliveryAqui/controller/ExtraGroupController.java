package com.example.DeliveryAqui.controller;

import com.example.DeliveryAqui.dtos.extraGroup.ExtraGroupPostRequest;
import com.example.DeliveryAqui.dtos.extraGroup.ExtraGroupPutRequest;
import com.example.DeliveryAqui.dtos.extraGroup.ExtraGroupResponse;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/extra-group")
public class ExtraGroupController {
    private final ExtraGroupService extraGroupService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<ExtraGroupResponse>> findAll(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ID") ExtraGroupSortField sortField,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(direction, sortField.getField()));
        Page<ExtraGroupResponse> page = extraGroupService.findAll(pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(PaginationUtils.buildPaginatedResponse(page));
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<ExtraGroupResponse> findById(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(extraGroupService.findById(id));
    }

    @GetMapping(path = "/name/{name}")
    public ResponseEntity<PaginatedResponse<ExtraGroupResponse>> findByName(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ID") ExtraGroupSortField sortField,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction,
            @PathVariable String name
    ) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(direction, sortField.getField()));
        Page<ExtraGroupResponse> page = extraGroupService.findByName(name, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(PaginationUtils.buildPaginatedResponse(page));
    }

    @PostMapping
    public ResponseEntity<ExtraGroupResponse> post(@RequestBody @Valid ExtraGroupPostRequest postResquest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(extraGroupService.save(postResquest));
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
