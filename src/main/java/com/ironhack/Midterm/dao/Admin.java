package com.ironhack.Midterm.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Admin extends User {

    public Admin(String username, String password) {
        super(username, password);
    }
}

