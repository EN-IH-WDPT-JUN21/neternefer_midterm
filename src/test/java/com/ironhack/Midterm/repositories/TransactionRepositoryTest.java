package com.ironhack.Midterm.repositories;

import com.ironhack.Midterm.dao.*;
import com.ironhack.Midterm.enums.Status;
import com.ironhack.Midterm.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TransactionRepositoryTest {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CheckingAccountRepository checkingAccountRepository;

    @Autowired
    SavingsAccountRepository savingsAccountRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    StudentCheckingAccountRepository studentCheckingAccountRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    private List<Transaction> transactions;
    private List<Address> addresses;
    private List<AccountHolder> accountHolders;
    private List<ThirdParty> thirdParty;
    private List<CheckingAccount> checkingAccounts;
    private List<SavingsAccount> savingsAccounts;
    private List<StudentCheckingAccount> studentAccounts;
    private List<CreditCard> creditCardAccounts;
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
                        new CheckingAccount(new Money(new BigDecimal("3000")), thirdParty.get(0), thirdParty.get(1),
                                "MyKey", LocalDate.now(), Status.ACTIVE ),
                        new CheckingAccount(new Money(new BigDecimal("90")), accountHolders.get(1),accountHolders.get(0),
                                "MyNewKey", LocalDate.of(2000, 12, 12), Status.FROZEN )
                )
        );
        savingsAccounts = savingsAccountRepository.saveAll(
                List.of(
                        new SavingsAccount(new Money(new BigDecimal("4500")), accountHolders.get(0), accountHolders.get(1),
                                "SavingKey", LocalDate.now(), Status.FROZEN, new BigDecimal("0.0025"),
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
    void Transaction_findSimultaneousTransactions() {
        List<Transaction> simultaneousTransactions = transactionRepository.findSimultaneousTransactions();
        assertEquals(2, simultaneousTransactions.size());
    }
}

