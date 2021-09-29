package com.ironhack.Midterm.dao;

import com.ironhack.Midterm.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "account_id")
public class SavingsAccount extends Account {

    @Column(name = "savings_interest_rate", columnDefinition = "DECIMAL(5,4)")
    private BigDecimal interestRate;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance_amount"))
    })
    private Money minimumBalance;


    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = (interestRate.compareTo(new BigDecimal("0.5")) == -1
                && interestRate.compareTo(new BigDecimal("0.0025")) == 1)
                ? interestRate : new BigDecimal("0.0025");
    }

    public void setMinimumBalance(Money minimumBalance) {
        BigDecimal convertBalance = minimumBalance.getAmount();
        this.minimumBalance = (convertBalance.compareTo(new BigDecimal("1000.00")) == -1
                && convertBalance.compareTo(new BigDecimal("100.00")) == 1)
                ? minimumBalance : new Money(new BigDecimal("1000.00"));
    }

    public SavingsAccount(
            Money balance,
            User primaryOwner,
            User secondaryOwner,
            String secretKey,
            LocalDate creationDate,
            Status status,
            BigDecimal interestRate,
            Money minimumBalance) {
        super(balance, primaryOwner, secondaryOwner, secretKey, creationDate, status);
        setInterestRate(interestRate);
        setMinimumBalance(minimumBalance);
    }
}


