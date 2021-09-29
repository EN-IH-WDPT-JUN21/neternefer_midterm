package com.ironhack.Midterm.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "user_id")
public class AccountHolder extends User {

    private LocalDate dateOfBirth;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "address_id")
    private Address primaryAddress;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "address1_id")
    private Address mailingAddress;

    public AccountHolder(String username,
                         String password,
                         LocalDate dateOfBirth,
                         Address primaryAddress,
                         Address mailingAddress) {
        super(username, password);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }

    public AccountHolder(String username, String password) {
        super(username, password);
    }
}

