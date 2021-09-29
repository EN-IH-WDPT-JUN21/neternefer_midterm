package com.ironhack.Midterm.controller.dto;

import com.ironhack.Midterm.dao.Money;
import com.ironhack.Midterm.dao.User;
import com.ironhack.Midterm.enums.Status;
import com.sun.istack.Nullable;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Optional;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    @NotNull(message = "Account id cannot be null")
    private long id;

    @NotNull(message = "Account balance cannot be null")
    private Money balance;

    @NotNull(message = "Account primary owner cannot be null")
    private User primaryOwner;

    @Nullable
    private User secondaryOwner;

    @Column(name="secret_key")
    private String secretKey;

    @NotNull(message = "Account creation date cannot be null")
    @PastOrPresent
    private LocalDate creationDate;

    @NotNull(message = "Account status cannot be null")
    private Status status;

    public Optional getSecondaryOwner() {
        return Optional.ofNullable(secondaryOwner);
    }
}

