package com.example.service.offering.service.impl;

import com.example.service.offering.dto.CategoryDTO;
import com.example.service.offering.dto.SalonDTO;
import com.example.service.offering.dto.ServiceDTO;
import com.example.service.offering.modal.ServiceOffering;
import com.example.service.offering.repository.ServiceRepository;
import com.example.service.offering.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceOfferingServiceImpl implements ServiceOfferingService {

    private final ServiceRepository serviceRepository;
    @Override
    public ServiceOffering createService(SalonDTO salonDTO, ServiceDTO serviceDTO, CategoryDTO categoryDTO) {
        ServiceOffering serviceOffering = new ServiceOffering();
        serviceOffering.setImage(serviceDTO.getImage());
        serviceOffering.setSalonId(salonDTO.getId());
        serviceOffering.setName(serviceDTO.getName());
        serviceOffering.setDescription(serviceDTO.getDescription());
        serviceOffering.setCategoryId(categoryDTO.getId());
        serviceOffering.setPrice(serviceDTO.getPrice());
        serviceOffering.setDuration(serviceDTO.getDuration());
        return serviceRepository.save(serviceOffering);
    }

    @Override
    public ServiceOffering updateService(Long serviceId, ServiceOffering service) throws Exception {
        ServiceOffering serviceOffering = serviceRepository.findById(serviceId).orElse(null);
        if (serviceOffering == null){
            throw new Exception("service not exist with the Id" + serviceId);
        }
        serviceOffering.setImage(service.getImage());
        serviceOffering.setName(service.getName());
        serviceOffering.setDescription(service.getDescription());
        serviceOffering.setPrice(service.getPrice());
        serviceOffering.setDuration(service.getDuration());
        return serviceRepository.save(serviceOffering);
    }

    @Override
    public Set<ServiceOffering> getAllServiceBySalonId(Long salonId, Long categoryId) {
        Set<ServiceOffering> services = serviceRepository.findBySalonId(salonId);
        if(categoryId != null){
            services = services.stream().filter((service) -> service.getCategoryId()!=null &&
                    service.getCategoryId() == categoryId).collect(Collectors.toSet());
        }
        return services;
    }

    @Override
    public Set<ServiceOffering> getServicesByIds(Set<Long> ids) {
        List<ServiceOffering> services = serviceRepository.findAllById(ids);
        return new HashSet<>(services);
    }

    @Override
    public ServiceOffering getServiceById(Long id) throws Exception {
        ServiceOffering serviceOffering = serviceRepository.findById(id).orElse(null);
        if(serviceOffering == null){
            throw new Exception("service not exist withe the id" + id);
        }
        return serviceOffering;
    }
}
