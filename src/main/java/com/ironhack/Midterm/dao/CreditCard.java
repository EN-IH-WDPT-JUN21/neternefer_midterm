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
public class CreditCard extends Account {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "credit_limit_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "credit_limit_amount"))
    })
    private Money creditLimit;

    @Column(columnDefinition = "DECIMAL(5,4)")
    private BigDecimal interestRate;

    public void setCreditLimit(Money creditLimit) {
        BigDecimal convertLimit = new BigDecimal(String.valueOf(creditLimit));
        this.creditLimit = (convertLimit.compareTo(new BigDecimal("100000")) == -1
                && convertLimit.compareTo(new BigDecimal("100")) == 1)
                ? creditLimit : new Money(new BigDecimal("100"));
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = (interestRate.compareTo(new BigDecimal("0.5")) == -1
                && interestRate.compareTo(new BigDecimal("0.1")) == 1)
                ? interestRate : new BigDecimal("0.02");
    }

    public CreditCard(
            Money balance,
            User primaryOwner,
            User secondaryOwner,
            String secretKey,
            LocalDate creationDate,
            Status status,
            Money creditLimit,
            BigDecimal interestRate) {
        super( balance, primaryOwner, secondaryOwner);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }
}

