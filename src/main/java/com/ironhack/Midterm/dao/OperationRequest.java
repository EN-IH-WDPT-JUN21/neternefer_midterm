package com.ironhack.Midterm.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperationRequest {

    private String name;

    private Long accountId;

    private Long secondAccountId;

    private BigDecimal amount;

    private String secretKey;

}

