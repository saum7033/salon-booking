package com.example.demo.service.impl;

import com.example.demo.modal.Salon;
import com.example.demo.payload.dto.SalonDTO;
import com.example.demo.payload.dto.UserDTO;
import com.example.demo.repository.SalonRepository;
import com.example.demo.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalonServiceImpl implements SalonService {
    private final SalonRepository salonRepository;

    @Override
    public Salon createSalon(SalonDTO req, UserDTO user) {
        Salon salon = new Salon();
        salon.setName(req.getName());
        salon.setAddress(req.getAddress());
        salon.setEmail(req.getEmail());
        salon.setCity(req.getCity());
        salon.setImages(req.getImages());
        salon.setOwnerId(req.getOwnerId());
        salon.setOpenTime(req.getOpenTime());
        salon.setClosedTime(req.getClosedTime());
        salon.setPhoneNumber(req.getPhoneNumber());
        return salonRepository.save(salon);
    }

    @Override
    public Salon updateSalon(SalonDTO salon, UserDTO user, Long salonId)throws Exception {
        Salon existingSalon = salonRepository.findById(salonId).orElse(null);
        if(!salon.getOwnerId().equals(user.getId())){
            throw new Exception("You don't have have permission to update tis salon");
        }
        if(existingSalon != null){
            existingSalon.setCity(salon.getCity());
            existingSalon.setName(salon.getName());
            existingSalon.setAddress(salon.getAddress());
            existingSalon.setEmail(salon.getEmail());
            existingSalon.setImages(salon.getImages());
            existingSalon.setOpenTime(salon.getOpenTime());
            existingSalon.setClosedTime(salon.getClosedTime());
            existingSalon.setOwnerId(salon.getOwnerId());
            existingSalon.setPhoneNumber(salon.getPhoneNumber());
            return salonRepository.save(existingSalon);
        }
        throw new Exception("salon not exist");
    }

    @Override
    public List<Salon> getAllSalon() {
        return salonRepository.findAll();
    }

    @Override
    public Salon getSalonById(Long salonId) throws Exception {
        Salon salon = salonRepository.findById(salonId).orElse(null);
        if (salon == null){
            throw new Exception("salon not exist");
        }
        return salon;
    }

    @Override
    public Salon getSalonByOwnerId(Long ownerId) {
        return salonRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Salon> searchSalonByCity(String city) {
        return salonRepository.searchSalon(city);
    }
}
