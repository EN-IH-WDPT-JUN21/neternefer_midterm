package com.ironhack.Midterm.service;

import com.ironhack.Midterm.controller.dto.CreditCardDTO;
import com.ironhack.Midterm.dao.CreditCard;
import com.ironhack.Midterm.repository.CreditCardRepository;
import com.ironhack.Midterm.service.interfaces.Interests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CreditCardService implements Interests {

    @Autowired
    CreditCardRepository creditCardRepository;

    public CreditCard store(CreditCardDTO creditCardDTO) {
        if(!creditCardRepository.findById(creditCardDTO.getId()).isPresent()) {
            try {
                CreditCard newAccount = new CreditCard();
                newAccount.setBalance(creditCardDTO.getBalance());
                newAccount.setPrimaryOwner(creditCardDTO.getPrimaryOwner());
                newAccount.setSecondaryOwner(creditCardDTO.getSecondaryOwner());
                newAccount.setSecretKey(creditCardDTO.getSecretKey());
                newAccount.setCreationDate(creditCardDTO.getCreationDate());
                newAccount.setStatus(creditCardDTO.getStatus());
                newAccount.setCreditLimit(creditCardDTO.getCreditLimit());
                newAccount.setInterestRate(creditCardDTO.getInterestRate().getAmount());
                creditCardRepository.save(newAccount);
                return newAccount;

            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Values not valid.");
            }
        }
        return null;
    }

    @Override
    public void addInterests(long accountId) {

    }
}

