package com.ironhack.Midterm.controller.dto;

import com.ironhack.Midterm.dao.Money;
import com.ironhack.Midterm.dao.User;
import com.ironhack.Midterm.enums.Status;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckingAccountDTO {

    @NotNull(message = "Account id cannot be null")
    private long id;

    @NotNull(message = "Account balance cannot be null")
    private Money balance;

    @NotNull(message = "Account primary owner cannot be null")
    private User primaryOwner;

    @Nullable
    private User secondaryOwner;

    private String secretKey;

    @NotNull(message = "Account creation date cannot be null")
    @PastOrPresent
    private LocalDate creationDate;

    @NotNull(message = "Account status cannot be null")
    private Status status;

    @NotNull(message = "Account monthly maintenance fee cannot be null") //do we need it in db?
    @Digits(integer = 2, fraction = 1)
    private Money monthlyMaintenanceFee;

    @NotNull(message = "Account minimum balance cannot be null")
    @Digits(integer = 3, fraction = 1) ///should they all have 4 fraction places for precision????
    private Money minimumBalance;

    public CheckingAccountDTO(Money balance,
                              User primaryOwner,
                              User secondaryOwner,
                              String secretKey,
                              LocalDate creationDate,
                              Status status,
                              Money monthlyMaintenanceFee,
                              Money minimumBalance) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.secretKey = secretKey;
        this.creationDate = creationDate;
        this.status = status;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.minimumBalance = minimumBalance;
    }
}

