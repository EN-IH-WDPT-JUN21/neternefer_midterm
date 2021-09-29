package com.ironhack.Midterm.controller.dto;

import com.ironhack.Midterm.dao.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    @NotNull
    private long id;

    @NotNull
    private Account account;

    @NotNull
    private BigDecimal amount;

    @NotNull(message = "Transaction date cannot be null")
    @PastOrPresent
    private Timestamp transactionDate = Timestamp.valueOf(LocalDateTime.now());

    public TransactionDTO(Account account, BigDecimal amount, Timestamp transactionDate) {
        this.account = account;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public LocalDateTime getTransactionDate() {
        return this.transactionDate.toLocalDateTime();
    }
}

