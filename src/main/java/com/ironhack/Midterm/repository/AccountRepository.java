package com.ironhack.Midterm.repository;

import com.ironhack.Midterm.dao.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    public Optional<Account> findBySecretKey(String secretKey);
}
