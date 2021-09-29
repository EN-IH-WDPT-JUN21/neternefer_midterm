package com.ironhack.Midterm.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {

    private long id;

    @NotNull(message = "Admin name cannot be null")
    private String username;

    @NotNull
    private String password;
}

