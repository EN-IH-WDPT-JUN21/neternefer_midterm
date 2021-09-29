package com.ironhack.Midterm.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.Midterm.enums.Status;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private Money balance;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User primaryOwner;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user1_id")
    private User secondaryOwner;

    private String secretKey;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    private Status status;

    private final BigDecimal penaltyFee = new BigDecimal("40");

    @OneToMany(fetch = FetchType.EAGER)
    private List<Transaction> transactions;

    private LocalDate updateDate;

    public Account(
            Money balance,
            User primaryOwner,
            User secondaryOwner,
            String secretKey,
            LocalDate creationDate,
            Status status) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.secretKey = secretKey;
        this.creationDate = creationDate;
        this.status = status;
        this.updateDate = creationDate;
    }

    public Account(Money balance, User primaryOwner, User secondaryOwner) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
    }

    public Optional getSecondaryOwner() {
        return Optional.ofNullable(secondaryOwner);
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }
}

