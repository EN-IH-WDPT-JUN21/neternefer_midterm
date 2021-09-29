package com.ironhack.Midterm.services;

import com.ironhack.Midterm.dao.*;
import com.ironhack.Midterm.enums.Status;
import com.ironhack.Midterm.repository.*;
import com.ironhack.Midterm.service.AccountService;
import com.ironhack.Midterm.service.AdminService;
import com.ironhack.Midterm.service.CheckingAccountService;
import com.ironhack.Midterm.service.SavingsAccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountServiceTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    SavingsAccountService savingsAccountService;

    @Autowired
    CheckingAccountService checkingAccountService;

    @Autowired
    AdminService adminService;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    StudentCheckingAccountRepository studentCheckingAccountRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    @Autowired
    SavingsAccountRepository savingsAccountRepository;

    @Autowired
    CheckingAccountRepository checkingAccountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    private List<Address> addresses;
    private List<AccountHolder> accountHolders;
    private List<ThirdParty> thirdParty;
    private List<CheckingAccount> checkingAccounts;
    private List<SavingsAccount> savingsAccounts;
    private List<StudentCheckingAccount> studentAccounts;
    private List<CreditCard> creditCardAccounts;
    private List<Transaction> transactions;

    @BeforeEach
    void setUp() {
        addresses = addressRepository.saveAll(
                List.of(
                        new Address("Berlin", "Germany", "Neue", 12),
                        new Address("Warsaw", "Poland", "Nowa", 34),
                        new Address("London", "United Kingdom", "New", 65),
                        new Address("Madrid", "Spain", "Nova", 23))
        );
        accountHolders = accountHolderRepository.saveAll(
                List.of(
                        new AccountHolder("Mary Watson", "$2a$12$FmQaXTyTXSM2ZwcHv0xtE.z/XQkUF2uty7l1bQsPGA1Sw2oVmwpya",
                                LocalDate.of(1990, 9, 20), addresses.get(0), addresses.get(0)),
                        new AccountHolder("Peter Parker", "$2a$12$bEc1TKDEFOAizTV7ucdx4e/zE5IwCkus1Y8or.wRCNvgCaxrHCgAG",
                                LocalDate.of(1989, 12, 12), addresses.get(1), addresses.get(1)),
                        new AccountHolder("Jane Doe", "$2a$12$3pWMrr8jOqsFMuoSJQKateluTg0Jt0hgnDgUnoLURfgLRFcvGBnpi",
                                LocalDate.of(2000, 3, 4), addresses.get(3), addresses.get(3)),
                        new AccountHolder("Alice Mann", "$2a$12$K4UwvWwD6bn36JLAb5x7IuWQChtosZwd./HdQABjF3R4XLiR39MeW",
                                LocalDate.of(2002, 7, 14), addresses.get(2), addresses.get(2))
                )
        );
        thirdParty = thirdPartyRepository.saveAll(
                List.of(
                        new ThirdParty("Patti Smith", "$2a$12$9iP7Ktzbzsyubg7Lu7ZYD.ZxmIkTojZ0yUbB/4TwQ3.UXdPMb8HX.",
                                "$2a$12$9iP7Ktzbzsyubg7Lu7ZYD.ZxmIkTojZ0yUbB/4TwQ3.UXdPMb8HX."),//pass5, MyHashKey
                        new ThirdParty("Cyndi Lauper", "$2a$12$FMFW5mWdyaQ8n.wFzYz8guVQupIkE5egntMW2zOzLlPrgIXLBCfGG",
                                "$2a$12$hKtwUOcFvSJ6AC0jfmasnuJ5H/kCuj5Ff48DnSivRWIFZvILRLGy.")//pass6, MyHashKey2
                )
        );
        checkingAccounts = checkingAccountRepository.saveAll(
                List.of(
                        new CheckingAccount(new Money(new BigDecimal("100")), thirdParty.get(0), thirdParty.get(1),
                                "MyKey", LocalDate.now(), Status.ACTIVE ),
                        new CheckingAccount(new Money(new BigDecimal("90")), accountHolders.get(1),accountHolders.get(0),
                                "MyNewKey", LocalDate.of(2000, 12, 12), Status.FROZEN )
                )
        );
        savingsAccounts = savingsAccountRepository.saveAll(
                List.of(
                        new SavingsAccount(new Money(new BigDecimal("45")), accountHolders.get(0), accountHolders.get(1),
                                "SavingKey", LocalDate.of(2019, 12, 12), Status.FROZEN, new BigDecimal("0.03"),
                                new Money(new BigDecimal("250"))),
                        new SavingsAccount(new Money(new BigDecimal("999")), thirdParty.get(1), thirdParty.get(0),
                                "SavingKey2", LocalDate.now(), Status.ACTIVE, new BigDecimal("0.0025"),
                                new Money(new BigDecimal("500")))
                )
        );
        studentAccounts = studentCheckingAccountRepository.saveAll(
                List.of(
                        new StudentCheckingAccount(new Money(new BigDecimal("999")), accountHolders.get(2), accountHolders.get(0),
                                "StudentKey", LocalDate.now(), Status.ACTIVE)
                )
        );
        creditCardAccounts = creditCardRepository.saveAll(
                List.of(
                        new CreditCard(new Money(new BigDecimal("999")), accountHolders.get(1), accountHolders.get(0), "CreditKey",
                                LocalDate.of(20018, 12, 3), Status.ACTIVE,
                                new Money(new BigDecimal("100")), new BigDecimal("0.50")),
                        new CreditCard(new Money(new BigDecimal("222222")), accountHolders.get(0), null, "CreditKey2",
                                LocalDate.of(2021, 4, 6), Status.FROZEN,
                                new Money(new BigDecimal("300")), new BigDecimal("0.002"))
                )
        );
        transactions = transactionRepository.saveAll(
                List.of(
                        new Transaction(checkingAccounts.get(1), new BigDecimal("3")),
                        new Transaction(savingsAccounts.get(0), new BigDecimal("13"),
                                LocalDateTime.of(2020, 3, 12, 11, 30, 15)),
                        new Transaction(savingsAccounts.get(0), new BigDecimal("13"),
                                LocalDateTime.of(2020, 3, 12, 11, 30, 15)),
                        new Transaction(studentAccounts.get(0), new BigDecimal("40"),
                                LocalDateTime.of(2019, 9, 29, 7, 12, 3)),
                        new Transaction(creditCardAccounts.get(0), new BigDecimal("100"))
                )
        );
    }


    @Test
    void Account_checkAccountExists() {
        long id = checkingAccounts.get(0).getId();
        assertTrue(accountService.checkAccountExists(id));
    }

    @Test
    void Account_checkAccountBalanceSufficient() {
        long id = checkingAccounts.get(0).getId();
        assertTrue(accountService.checkAccountBalanceSufficient(id, new BigDecimal("50")));
        assertFalse(accountService.checkAccountBalanceSufficient(id, new BigDecimal("200000")));

    }

    @Test
    void Account_checkNameIsValid() {
        long id = checkingAccounts.get(1).getId();
        assertTrue(accountService.checkNameIsValid(id, checkingAccounts.get(1).getPrimaryOwner().getUsername()));
        assertFalse(accountService.checkNameIsValid(id, "Joe Doe"));
    }

    @Test
    void Account_addTransactions() {
        long id = savingsAccounts.get(0).getId();
        long id2 = savingsAccounts.get(1).getId();
        SavingsAccount account = savingsAccountRepository.findById(id).get();
        List<Transaction> transactions = account.getTransactions();
        long transactionsNum = transactions.size();
        assertEquals(0, transactionsNum);
        SavingsAccount receiverAccount = savingsAccountRepository.findById(id2).get();
        Transaction transaction = new Transaction(account, new BigDecimal("100"));
        transactionRepository.save(transaction);
        accountService.addTransactions(account, receiverAccount, transaction);
        assertEquals(transactionsNum + 1, account.getTransactions().size());
    }

    @Test
    void Account_addInterests_SavingsAccount() {//23
        long id = savingsAccounts.get(0).getId();
        SavingsAccount account = (SavingsAccount) accountRepository.findById(id).get();
        BigDecimal balance = account.getBalance().getAmount();
        BigDecimal rate = account.getInterestRate();
        savingsAccountService.addInterests(id);
        Money balanceAfter = new Money(balance.add(balance.multiply(rate)));
        assertEquals(new Money(new BigDecimal("46.35")).getAmount(), balanceAfter.getAmount());
    }

    @Test
    void Account_operation() {
        long id = checkingAccounts.get(0).getId();
        long id2 = checkingAccounts.get(1).getId();
        Money senderBalanceBefore = checkingAccounts.get(0).getBalance();
        Money receiverBalanceBefore = checkingAccounts.get(1).getBalance();
        OperationRequest req = new OperationRequest("Patti Smith", id, id2, BigDecimal.valueOf(100.00), "MyKey");
        Transaction tr = accountService.operation(req);
        Money senderBalanceAfter = checkingAccounts.get(0).getBalance();
        Money receiverBalanceAfter = checkingAccounts.get(1).getBalance();
        assertEquals(senderBalanceBefore.decreaseAmount(BigDecimal.valueOf(100.0)), senderBalanceAfter.getAmount());
        assertEquals(receiverBalanceBefore.increaseAmount(BigDecimal.valueOf(100.0)), receiverBalanceAfter.getAmount());
    }

    @Test
    void Account_changeStatus() {
        long id = savingsAccounts.get(0).getId();
        long id2 = checkingAccounts.get(1).getId();
        long id3 = savingsAccounts.get(1).getId();
        accountService.changeStatus(id);
        accountService.changeStatus(id2);
        accountService.changeStatus(id3);
        assertEquals(Status.FROZEN,accountRepository.findById(id).get().getStatus());
        assertEquals(Status.FROZEN,accountRepository.findById(id2).get().getStatus());
        assertEquals(Status.FROZEN,accountRepository.findById(id3).get().getStatus());
    }

    @Test
    void Account_penalize_SavingsAccount() {
        Long id = savingsAccounts.get(0).getId();
        BigDecimal penalty = savingsAccountRepository.findById(id).get().getPenaltyFee();
        BigDecimal currentBalance = savingsAccountRepository.findById(id).get().getBalance().getAmount();
        savingsAccountService.penalize(savingsAccountRepository.findById(id).get().getId());
        assertEquals(currentBalance.subtract(penalty), savingsAccountRepository.findById(id).get().getBalance().getAmount());
    }

    @Test
    void Account_penalize_CheckingAccount() {
        Long id = checkingAccounts.get(0).getId();
        BigDecimal penalty = checkingAccountRepository.findById(id).get().getPenaltyFee();
        BigDecimal currentBalance = checkingAccountRepository.findById(id).get().getBalance().getAmount();
        checkingAccountService.penalize(checkingAccountRepository.findById(id).get().getId());
        assertEquals(currentBalance.subtract(penalty), checkingAccountRepository.findById(id).get().getBalance().getAmount());
    }

    @Test
    void Account_calculateInterest() {
        Money interests = accountService.calculateInterest(new Money(new BigDecimal("1000000")), new Money(BigDecimal.valueOf(0.12)));
        assertEquals(new BigDecimal("1010000.00"), interests.getAmount());
    }

    @Test
    void Account_calculateYears() {
        LocalDate from = LocalDate.of(2000, 9, 12);
        int yearsPassed = accountService.calculateYears(from);
        assertEquals(21, yearsPassed);
    }

    @Test
    void Account_calculateMonths() {
        LocalDate from = LocalDate.of(2021, 1, 1);
        int monthsPassed = accountService.calculateMonths(from);
        assertEquals(8, monthsPassed);
    }

    @Test
    void Account_checkBalance() {
        Long id = savingsAccounts.get(0).getId();
        User user = accountRepository.findById(id).get().getPrimaryOwner();
        adminService.checkBalance(id, user.getId());
        BigDecimal currentBalance = accountRepository.findById(id).get().getBalance().getAmount();
        assertEquals(new BigDecimal("5.15"), currentBalance);
    }

    @Test
    void Account_freezeAccount() {
        adminService.checkBalance(savingsAccounts.get(0).getId(), savingsAccounts.get(0).getPrimaryOwner().getId());
        assertEquals(Status.FROZEN, savingsAccounts.get(0).getStatus());
    }
}

