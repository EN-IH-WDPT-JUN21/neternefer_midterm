package com.ironhack.Midterm.service;

import com.ironhack.Midterm.controller.dto.CheckingAccountDTO;
import com.ironhack.Midterm.dao.*;
import com.ironhack.Midterm.repository.CheckingAccountRepository;
import com.ironhack.Midterm.repository.StudentCheckingAccountRepository;
import com.ironhack.Midterm.service.interfaces.Penalize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
public class CheckingAccountService implements Penalize {

    @Autowired
    CheckingAccountRepository checkingAccountRepository;

    @Autowired
    StudentCheckingAccountRepository studentCheckingAccountRepository;

    @Autowired
    AccountService accountService;

    public Account store(CheckingAccountDTO checkingAccountDTO) {
        if(!checkingAccountRepository.findById(checkingAccountDTO.getId()).isPresent()
                && checkingAccountDTO.getPrimaryOwner() instanceof AccountHolder
                && accountService.calculateYears(((AccountHolder) checkingAccountDTO.getPrimaryOwner()).getDateOfBirth()) > 24) {
            try {
                CheckingAccount newAccount = new CheckingAccount();
                newAccount.setBalance(checkingAccountDTO.getBalance());
                newAccount.setPrimaryOwner(checkingAccountDTO.getPrimaryOwner());
                newAccount.setSecondaryOwner(checkingAccountDTO.getSecondaryOwner());
                newAccount.setSecretKey(checkingAccountDTO.getSecretKey());
                newAccount.setCreationDate(checkingAccountDTO.getCreationDate());
                newAccount.setStatus(checkingAccountDTO.getStatus());
                checkingAccountRepository.save(newAccount);
                return newAccount;
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Values not valid.");
            }
        } else if(!studentCheckingAccountRepository.findById(checkingAccountDTO.getId()).isPresent()
                && checkingAccountDTO.getPrimaryOwner() instanceof AccountHolder
                && accountService.calculateYears(((AccountHolder) checkingAccountDTO.getPrimaryOwner()).getDateOfBirth()) < 24) {
            StudentCheckingAccount newStudent = new StudentCheckingAccount(checkingAccountDTO.getBalance(),
                    checkingAccountDTO.getPrimaryOwner(), checkingAccountDTO.getSecondaryOwner(),
                    checkingAccountDTO.getSecretKey(), checkingAccountDTO.getCreationDate(),
                    checkingAccountDTO.getStatus());
            studentCheckingAccountRepository.save(newStudent);
            return newStudent;
        }
        return null;
    }

    public void penalize(long id) {
        CheckingAccount account = checkingAccountRepository.findById(id).get();
        BigDecimal currentBalance = account.getBalance().getAmount();
        if(currentBalance.compareTo(account.getMinimumBalance().getAmount()) == -1) {
            BigDecimal newBalance = account.getBalance().getAmount().subtract(account.getPenaltyFee());
            account.setBalance(new Money(newBalance));
            checkingAccountRepository.save(account);
        }
    }
}

