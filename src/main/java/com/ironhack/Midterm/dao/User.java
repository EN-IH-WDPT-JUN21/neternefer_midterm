package com.ironhack.Midterm.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    @OneToMany
    private List<Account> accounts;

    private String username;

    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Role> roles;

    public User(String userName) {
        this.username = userName;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(List<Account> accounts, String username, String password, Set<Role> roles) {
        this.accounts = accounts;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}

