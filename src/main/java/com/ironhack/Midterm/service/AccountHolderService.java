package com.ironhack.Midterm.service;

import com.ironhack.Midterm.controller.dto.AccountHolderDTO;
import com.ironhack.Midterm.dao.Account;
import com.ironhack.Midterm.dao.AccountHolder;
import com.ironhack.Midterm.dao.Money;
import com.ironhack.Midterm.repository.AccountHolderRepository;
import com.ironhack.Midterm.repository.AccountRepository;
import com.ironhack.Midterm.service.interfaces.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AccountHolderService implements Balance {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AccountRepository accountRepository;

    public AccountHolder store(AccountHolderDTO accountHolderDTO) {
        Optional<AccountHolder> holder = accountHolderRepository.findById(accountHolderDTO.getId());
        if(!holder.isPresent()) {
            try {
                AccountHolder accountHolder = new AccountHolder(accountHolderDTO.getUsername(),
                        accountHolderDTO.getPassword(), accountHolderDTO.getDateOfBirth(),
                        accountHolderDTO.getPrimaryAddress(), accountHolderDTO.getMailingAddress());
                accountHolderRepository.save(accountHolder);
                return accountHolder;
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provided values not valid.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account holder with this id already exists in the system.");
        }
    }

    public void updateUsername(Long id, AccountHolderDTO accountHolderDTO) {
        Optional<AccountHolder> holder = accountHolderRepository.findById(id);
        if (holder.isPresent()) {
            try {
                holder.get().setUsername(accountHolderDTO.getUsername());
                accountHolderRepository.save(holder.get());
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username value not valid.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account holder with this id doesn't exist in the system");
        }
    }

    @Override
    public Money checkBalance(long account_id, long user_id) {
        AccountHolder holder = accountHolderRepository.findById(user_id).get();
        Account account = accountRepository.findById(user_id).get();
        if(account.getPrimaryOwner() == holder || account.getSecondaryOwner().get() == holder) {
            return account.getBalance();
        }
        return null;
    }
}

