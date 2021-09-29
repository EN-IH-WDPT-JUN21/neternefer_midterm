package com.ironhack.Midterm.service;

import com.ironhack.Midterm.controller.dto.SavingsAccountDTO;
import com.ironhack.Midterm.dao.Money;
import com.ironhack.Midterm.dao.SavingsAccount;
import com.ironhack.Midterm.repository.SavingsAccountRepository;
import com.ironhack.Midterm.service.interfaces.Interests;
import com.ironhack.Midterm.service.interfaces.Penalize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class SavingsAccountService implements Penalize, Interests {

    @Autowired
    SavingsAccountRepository savingsAccountRepository;

    @Autowired
    AccountService accountService;

    public SavingsAccount store(SavingsAccountDTO savingsAccountDTO) {
        if (!savingsAccountRepository.findById(savingsAccountDTO.getId()).isPresent()) {
            try {
                SavingsAccount newAccount = new SavingsAccount();
                newAccount.setBalance(savingsAccountDTO.getBalance());
                newAccount.setPrimaryOwner(savingsAccountDTO.getPrimaryOwner());
                newAccount.setSecondaryOwner(savingsAccountDTO.getSecondaryOwner());
                newAccount.setSecretKey(savingsAccountDTO.getSecretKey());
                newAccount.setCreationDate(savingsAccountDTO.getCreationDate());
                newAccount.setStatus(savingsAccountDTO.getStatus());
                newAccount.setInterestRate(savingsAccountDTO.getInterestRate());
                newAccount.setMinimumBalance(savingsAccountDTO.getMinimumBalance());
                savingsAccountRepository.save(newAccount);
                return newAccount;
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Values not valid.");
            }
        }
        return null;
    }

    public void penalize(long id) {
        SavingsAccount account = savingsAccountRepository.findById(id).get();
        BigDecimal currentBalance = account.getBalance().getAmount();
        if(currentBalance.compareTo(account.getMinimumBalance().getAmount()) == -1) {
            BigDecimal newBalance = account.getBalance().getAmount().subtract(account.getPenaltyFee());
            account.setBalance(new Money(newBalance));
            savingsAccountRepository.save(account);
        }
    }

    @Override
    public void addInterests(long id) {
        SavingsAccount account = savingsAccountRepository.findById(id).get();
        BigDecimal interestRate = account.getInterestRate();
        Money currentBalance = account.getBalance();
        LocalDate balanceUpdateDate = account.getUpdateDate();
        Money newBalance = (accountService.calculateYears(balanceUpdateDate) >= 1)
                ? new Money(currentBalance.getAmount().add(interestRate.multiply(currentBalance.getAmount())))
                : currentBalance;
        account.setBalance(newBalance);
        account.setUpdateDate(LocalDate.now());
        savingsAccountRepository.save(account);
    }
}
