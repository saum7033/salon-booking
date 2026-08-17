package com.example.service.offering.repository;

import com.example.service.offering.modal.ServiceOffering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ServiceRepository extends JpaRepository<ServiceOffering,Long> {

    Set<ServiceOffering> findBySalonId(Long salonId);

}
