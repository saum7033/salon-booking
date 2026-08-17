package com.example.demo.service;

import com.example.demo.modal.Salon;
import com.example.demo.payload.dto.SalonDTO;
import com.example.demo.payload.dto.UserDTO;

import java.util.List;

public interface SalonService {
    Salon createSalon(SalonDTO salon, UserDTO user);
    Salon updateSalon(SalonDTO salon, UserDTO user,Long salonId) throws Exception;
    List<Salon> getAllSalon();
    Salon getSalonById(Long salonId) throws Exception;
    Salon getSalonByOwnerId(Long ownerId);
    List<Salon> searchSalonByCity(String city);

}
