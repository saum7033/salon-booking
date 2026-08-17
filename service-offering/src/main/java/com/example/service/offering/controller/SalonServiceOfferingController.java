package com.example.service.offering.controller;

import com.example.service.offering.dto.CategoryDTO;
import com.example.service.offering.dto.SalonDTO;
import com.example.service.offering.dto.ServiceDTO;
import com.example.service.offering.modal.ServiceOffering;
import com.example.service.offering.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/service-offering/salon-owner")
@RequiredArgsConstructor
public class SalonServiceOfferingController {
    private final ServiceOfferingService serviceOfferingService;

    @PostMapping
    public ResponseEntity<ServiceOffering> createService(
            @RequestBody ServiceDTO serviceDTO
            ){
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(serviceDTO.getCategoryId());
        ServiceOffering serviceOffering = serviceOfferingService.createService(salonDTO,serviceDTO,categoryDTO);
        return ResponseEntity.ok(serviceOffering);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ServiceOffering> updateService(
            @PathVariable Long id,
            @RequestBody ServiceOffering serviceOffering
    )throws Exception{
        ServiceOffering serviceOfferings = serviceOfferingService.updateService(id, serviceOffering);
        return ResponseEntity.ok(serviceOfferings);
    }
}
