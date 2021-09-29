package com.ironhack.Midterm.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;

    private String city;

    private String country;

    private String street;

    @Column(name = "street_number")
    private int streetNumber;

    @OneToMany
    @JoinColumn(name = "address_id")
    private Set<AccountHolder> users;

    public Address(String city, String country, String street, int streetNumber) {
        this.city = city;
        this.country = country;
        this.street = street;
        this.streetNumber = streetNumber;
    }
}

