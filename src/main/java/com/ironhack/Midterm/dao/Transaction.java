package com.ironhack.Midterm.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "account_id")
    private Account account;

    private BigDecimal amount;

    private LocalDateTime transactionDate;

    public Transaction(
            Account account,
            BigDecimal amount) {
        this.account = account;
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();
    }

    public Transaction(Account account,
                       BigDecimal amount,
                       LocalDateTime transactionDate) {
        this.account = account;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }
}

