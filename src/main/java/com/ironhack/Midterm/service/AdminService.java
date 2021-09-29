package com.ironhack.Midterm.service;

import com.ironhack.Midterm.dao.*;
import com.ironhack.Midterm.repository.AccountRepository;
import com.ironhack.Midterm.repository.TransactionRepository;
import com.ironhack.Midterm.service.interfaces.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements Balance {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    CreditCardService creditCardService;

    @Autowired
    CheckingAccountService checkingAccountService;

    @Autowired
    SavingsAccountService savingAccountService;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Money checkBalance(long account_id, long user_id) {
        Account account = accountRepository.findById(account_id).get();
        if(account instanceof CheckingAccount
                && account.getBalance().getAmount().compareTo(((CheckingAccount) account).getMinimumBalance().getAmount()) == -1) {
            checkingAccountService.penalize(account_id);
        } else if(account instanceof SavingsAccount
                && account.getBalance().getAmount().compareTo(((SavingsAccount) account).getMinimumBalance().getAmount()) == -1) {
            savingAccountService.penalize(account_id);
            savingAccountService.addInterests(account_id);
        } else if(account instanceof CreditCard) {
            creditCardService.addInterests(account_id);
        }
        //check if freeze needed - 24 hours transactions
        else if(transactionRepository.findSimultaneousTransactions().size() > 2) {
            accountService.changeStatus(account_id);
        }
        return account.getBalance();
    }
}

