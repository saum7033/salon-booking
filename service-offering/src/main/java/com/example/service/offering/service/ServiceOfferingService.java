package com.example.service.offering.service;

import com.example.service.offering.dto.CategoryDTO;
import com.example.service.offering.dto.SalonDTO;
import com.example.service.offering.dto.ServiceDTO;
import com.example.service.offering.modal.ServiceOffering;

import java.util.Set;

public interface ServiceOfferingService {
    ServiceOffering createService(SalonDTO salonDTO, ServiceDTO serviceDTO, CategoryDTO categoryDTO);

    ServiceOffering updateService(Long serviceId,ServiceOffering service) throws Exception;

    Set<ServiceOffering> getAllServiceBySalonId(Long salonId, Long categoryId);

    Set<ServiceOffering> getServicesByIds(Set<Long> ids);

    ServiceOffering getServiceById(Long id) throws Exception;
}
