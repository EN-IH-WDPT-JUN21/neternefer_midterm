package com.ironhack.Midterm.service.interfaces;

import com.ironhack.Midterm.dao.Money;

public interface Balance {

    public Money checkBalance(long account_id, long user_id);
}

