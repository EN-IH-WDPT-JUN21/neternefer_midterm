package com.ironhack.Midterm.service;

import com.ironhack.Midterm.dao.*;
import com.ironhack.Midterm.enums.Status;
import com.ironhack.Midterm.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Service
public class AccountService  {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    SavingsAccountRepository savingsAccountRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    CheckingAccountRepository checkingAccountRepository;

    @Autowired
    StudentCheckingAccountRepository studentCheckingAccountRepository;

    public void addTransactions(Account sender, Account receiver, Transaction transaction) {
        List<Transaction> senderTransactions = sender.getTransactions();
        senderTransactions.add(transaction);
        sender.setTransactions(senderTransactions);
        accountRepository.save(sender);
    }

    @Transactional //third-party can only transfer, must provide amount, accountId and secretkey of THEIR account + hashedkey in http header
    public Transaction operation(OperationRequest operationRequest) { //should provide one of names + id
        BigDecimal amount = operationRequest.getAmount();
        String secretKey = operationRequest.getSecretKey();
        Long senderAccountId = operationRequest.getAccountId();
        Account senderAccount = (senderAccountId != null) ? accountRepository.findById(senderAccountId).get() : accountRepository.findBySecretKey(secretKey).get();
        String accountOwner = operationRequest.getName();
        Long receiverAccountId = operationRequest.getSecondAccountId();
        Account receiverAccount = accountRepository.findById(receiverAccountId).get();
        try {
            if (senderAccountId != null && checkAccountExists(senderAccountId)
                    && senderAccount.getStatus() == Status.ACTIVE
                    && checkAccountExists(receiverAccountId)
                    && checkNameIsValid(senderAccountId, accountOwner)
                    && checkAccountBalanceSufficient(senderAccountId, amount)) {
                senderAccount.setBalance(new Money(senderAccount.getBalance().decreaseAmount(amount)));
                accountRepository.save(senderAccount);
                receiverAccount.setBalance(new Money(receiverAccount.getBalance().increaseAmount(amount)));
                accountRepository.save(receiverAccount);
            } else if (senderAccountId == null && accountOwner == null
                    && checkAccountExists(senderAccount.getId())
                    && checkAccountExists(receiverAccountId)
                    && checkAccountBalanceSufficient(senderAccount.getId(), amount)) {
                senderAccount.setBalance(new Money(senderAccount.getBalance().decreaseAmount(amount)));
                accountRepository.save(senderAccount);
                receiverAccount.setBalance(new Money(receiverAccount.getBalance().increaseAmount(amount)));
                accountRepository.save(receiverAccount);
            }
            Transaction newTransaction = new Transaction(senderAccount, amount, LocalDateTime.now());
            transactionRepository.save(newTransaction);
            addTransactions(senderAccount, receiverAccount, newTransaction);
            return newTransaction;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction couldn't be processed, insufficient funds or invalid Account data.");
        }
    }

    public boolean checkAccountExists(Long id) {
        if(id == null) {return false; }
        return accountRepository.findById(id).isPresent();
    }

    public boolean checkAccountBalanceSufficient(Long id, BigDecimal balanceNeeded) {
        if(checkAccountExists(id)) {
            BigDecimal currentBalance = accountRepository.findById(id).get().getBalance().getAmount();
            return currentBalance.compareTo(BigDecimal.ONE) == 1
                    && currentBalance.compareTo(balanceNeeded) == 1;
        }
        return false;
    }

    public boolean checkNameIsValid(Long id, String name) {
        User secondUser = (User) accountRepository.findById(id).get().getSecondaryOwner().get();
        return name.equals(accountRepository.findById(id).get().getPrimaryOwner().getUsername())
                || name.equals((secondUser.getUsername()));
    }

    public Money calculateInterest(Money balance, Money interestRate) {
        BigDecimal monthlyInterest = interestRate.getAmount().divide(new BigDecimal("12"));
        BigDecimal result = balance.getAmount().multiply(monthlyInterest);
        return new Money(result.add(balance.getAmount()));
    }

    public int calculateYears(LocalDate from) {
        LocalDate dateNow = LocalDate.now();
        Period period = Period.between(dateNow, from);
        int yearsPassed = Math.abs(period.getYears());
        return yearsPassed;
    }

    public int calculateMonths(LocalDate from) {
        LocalDate dateNow = LocalDate.now();
        Period period = Period.between(dateNow, from);
        int monthsPassed = Math.abs(period.getMonths());
        return monthsPassed;
    }

    public void changeStatus(long id) {
        Account account = accountRepository.findById(id).get();
        account.setStatus(Status.FROZEN);
        accountRepository.save(account);
    }
}

