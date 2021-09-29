package com.ironhack.Midterm.dao;

import com.ironhack.Midterm.enums.Status;
import com.ironhack.Midterm.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SavingsAccountTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    SavingsAccountRepository savingsAccountRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    private List<Address> addresses;
    private List<AccountHolder> accountHolders;
    private List<ThirdParty> thirdParty;
    private List<SavingsAccount> savingsAccounts;

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
        savingsAccounts = savingsAccountRepository.saveAll(
                List.of(
                        new SavingsAccount(new Money(new BigDecimal("4500")), accountHolders.get(0), accountHolders.get(1),
                                "SavingKey", LocalDate.now(), Status.FROZEN, new BigDecimal("0.0025"),
                                new Money(new BigDecimal("2000"))),
                        new SavingsAccount(new Money(new BigDecimal("999")), thirdParty.get(1), thirdParty.get(0),
                                "SavingKey2", LocalDate.now(), Status.ACTIVE, new BigDecimal("0.04"),
                                new Money(new BigDecimal("500")))
                )
        );
    }

    @Test
    public void SavingsAccount_setInterestRate() {
        SavingsAccount account = savingsAccountRepository.findById(savingsAccounts.get(0).getId()).get();
        assertEquals(new BigDecimal("0.0025"), account.getInterestRate());
        account = savingsAccounts.get(1);
        assertEquals(new BigDecimal("0.04"), account.getInterestRate());
    }

    @Test
    public void SavingsAccount_setMinimumBalance() {
        SavingsAccount account = savingsAccounts.get(0);
        assertEquals(new BigDecimal("1000.00"), account.getMinimumBalance().getAmount());
        account = savingsAccounts.get(1);
        assertEquals(new BigDecimal("500.00"), account.getMinimumBalance().getAmount());
    }

    @Test
    public void SavingsAccount_setBalance() {
        SavingsAccount account = (SavingsAccount) accountRepository.findById(savingsAccounts.get(1).getId()).get();
        account.setBalance(new Money(new BigDecimal("1000")));
        assertEquals(new BigDecimal("1000.00"), account.getBalance().getAmount());
        assertEquals(LocalDate.now(), account.getUpdateDate());
    }
}

