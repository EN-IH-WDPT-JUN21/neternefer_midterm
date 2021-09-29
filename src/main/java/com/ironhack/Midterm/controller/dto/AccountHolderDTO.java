package com.ironhack.Midterm.controller.dto;

import com.ironhack.Midterm.dao.Address;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountHolderDTO {

    @NotNull(message = "Id cannot be null")
    private long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull(message = "Date of birth cannot be null")
    @PastOrPresent
    private LocalDate dateOfBirth;

    @NotNull(message = "Primary address cannot be null")
    private Address primaryAddress;

    @NotNull(message = "Mailing address cannot be null")
    private Address mailingAddress;
}

