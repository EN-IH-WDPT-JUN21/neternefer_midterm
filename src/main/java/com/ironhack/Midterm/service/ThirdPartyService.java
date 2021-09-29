package com.ironhack.Midterm.service;

import com.ironhack.Midterm.controller.dto.ThirdPartyDTO;
import com.ironhack.Midterm.dao.ThirdParty;
import com.ironhack.Midterm.repository.ThirdPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ThirdPartyService {

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    public ThirdParty store(ThirdPartyDTO thirdPartyDTO) {
        Optional<ThirdParty> party = thirdPartyRepository.findById(thirdPartyDTO.getId());
        if(!party.isPresent()) {
            try {
                ThirdParty thirdParty = new ThirdParty(thirdPartyDTO.getUsername(),
                        thirdPartyDTO.getPassword(), thirdPartyDTO.getHashedKey());
                thirdPartyRepository.save(thirdParty);
                return thirdParty;
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provided values not valid.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Third party with this id already exists in the system.");
        }
    }

    public void updateUsername(Long id, ThirdPartyDTO thirdPartyDTO) {
        Optional<ThirdParty> party = thirdPartyRepository.findById(id);
        if (party.isPresent()) {
            try {
                party.get().setUsername(thirdPartyDTO.getUsername());
                thirdPartyRepository.save(party.get());
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username value not valid.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Third party with this id doesn't exist in the system");
        }
    }
}

