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
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SavingsAccountDTO {

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

    @NotNull(message = "Account interest rate cannot be null")
    @DecimalMin(value = "0.0025")
    @DecimalMax(value = "0.5")
    @Digits(integer = 1, fraction = 4)
    private BigDecimal interestRate;

    @NotNull(message = "Account minimum balance cannot be null")
    @DecimalMin(value = "100.00")
    @DecimalMax(value = "1000.00")
    @Digits(integer = 4, fraction = 2)
    private Money minimumBalance;

}












