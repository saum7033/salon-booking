package com.example.service.offering.service.client;

import com.example.service.offering.dto.SalonDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("salon-service")
public interface SalonFeignClient {

    @GetMapping("/api/salon/owner/{ownerId}")
    public ResponseEntity<SalonDTO> getSalonByOwnerId(@PathVariable Long ownerId)
            throws Exception;
}
