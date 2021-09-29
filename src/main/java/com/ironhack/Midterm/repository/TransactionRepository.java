package com.ironhack.Midterm.repository;

import com.ironhack.Midterm.dao.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT t1.* FROM transaction t1, transaction t2 \n" +
            "WHERE t1.transaction_date = t2.transaction_date \n" +
            "AND t1.id != t2.id AND t1.account_id = t2.account_id;", nativeQuery = true)
    public List<Transaction> findSimultaneousTransactions();
}

