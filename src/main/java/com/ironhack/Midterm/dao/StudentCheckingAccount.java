package com.ironhack.Midterm.dao;

import com.ironhack.Midterm.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "account_id")
public class StudentCheckingAccount extends Account  {

    public StudentCheckingAccount(
            Money balance,
            User primaryOwner,
            User secondaryOwner,
            String secretKey,
            LocalDate creationDate,
            Status status) {
        super(balance, primaryOwner, secondaryOwner, secretKey, creationDate, status);
    }

}

