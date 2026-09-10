package com.booking.demo.service.client;

import com.booking.demo.dto.ServiceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient("service-offering")
public interface ServiceOfferingFeignClient {

    @GetMapping("/api/service-offering/list")
    public ResponseEntity<Set<ServiceDTO>> getServicesByIds(@RequestParam Set<Long> ids);
}
