package com.ironhack.Midterm.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThirdPartyDTO {

    @NotNull(message = "Third-party id cannot be null")
    private long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @Column(name="hashed_key")
    private String hashedKey;
}

