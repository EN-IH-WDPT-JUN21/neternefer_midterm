package com.ironhack.Midterm.dao;

import com.ironhack.Midterm.enums.Status;
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
@PrimaryKeyJoinColumn(name = "account_id")
public class CheckingAccount extends Account {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "monthly_maintenance_fee_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "monthly_maintenance_fee_amount"))
    })
    private final Money monthlyMaintenanceFee = new Money(new BigDecimal("12"));

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance_amount"))
    })
    private final Money minimumBalance = new Money(new BigDecimal("250"));

    public CheckingAccount(Money balance, User primaryOwner, User secondaryOwner, String secretKey, LocalDate creationDate, Status status) {
        super(balance, primaryOwner, secondaryOwner, secretKey, creationDate, status);
    }
}

