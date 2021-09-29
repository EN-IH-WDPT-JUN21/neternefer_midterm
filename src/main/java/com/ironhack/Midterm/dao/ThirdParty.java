package com.ironhack.Midterm.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "user_id")
public class ThirdParty extends User {

    private String hashedKey; //will this be in db or hidden from dto?

    public ThirdParty(String username, String password, String hashedKey) {
        super(username, password);
        this.hashedKey = hashedKey;
    }

}

