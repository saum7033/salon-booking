package com.booking.demo.service.client;

import com.booking.demo.dto.SalonDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("salon-service")
public interface SalonFeignClient {

    @GetMapping("/api/salon/{salonId}")
    ResponseEntity<SalonDTO> getSalonById(
            @PathVariable("salonId") Long salonId
    ) throws Exception;

    @GetMapping("/api/salon/owner/{ownerId}")
    ResponseEntity<SalonDTO> getSalonByOwnerId(
            @PathVariable("ownerId") Long ownerId
    ) throws Exception;
}