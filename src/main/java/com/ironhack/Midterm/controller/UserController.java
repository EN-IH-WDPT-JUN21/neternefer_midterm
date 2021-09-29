package com.ironhack.Midterm.controller;

import com.ironhack.Midterm.controller.dto.AccountHolderDTO;
import com.ironhack.Midterm.controller.dto.ThirdPartyDTO;
import com.ironhack.Midterm.dao.AccountHolder;
import com.ironhack.Midterm.dao.ThirdParty;
import com.ironhack.Midterm.repository.AccountHolderRepository;
import com.ironhack.Midterm.repository.ThirdPartyRepository;
import com.ironhack.Midterm.service.AccountHolderService;
import com.ironhack.Midterm.service.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AccountHolderService accountHolderService;

    @Autowired
    ThirdPartyService thirdPartyService;

    @GetMapping("/users/accountHolders")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountHolder> getAccountHolders() {
        return accountHolderRepository.findAll();
    }

    @GetMapping("/users/thirdParty")
    @ResponseStatus(HttpStatus.OK)
    public List<ThirdParty> getThirdParty() {
        return thirdPartyRepository.findAll();
    }

    @GetMapping("/users/accountHolders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder getAccountHolderById(@PathVariable("id") Long id) {
        return accountHolderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account holder not found"));
    }

    @GetMapping("/users/thirdParty/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ThirdParty getThirdPartyById(@PathVariable("id") Long id) {
        return thirdPartyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account holder not found"));
    }

    @PostMapping("/users/accountHolders")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder store(@RequestBody AccountHolderDTO accountHolderDTO) {
        return accountHolderService.store(accountHolderDTO);
    }

    @PostMapping("/users/thirdParty")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty store(@RequestBody ThirdPartyDTO thirdPartyDTO) {
        return thirdPartyService.store(thirdPartyDTO);
    }

    @PatchMapping("/users/accountHolders/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUsername(@PathVariable(name = "id") Long id,
                               @RequestBody AccountHolderDTO accountHolderDTO) {
        accountHolderService.updateUsername(id, accountHolderDTO);
    }

    @PatchMapping("/users/thirdParty/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUsername(@PathVariable(name = "id") Long id,
                               @RequestBody ThirdPartyDTO thirdPartyDTO) {
        thirdPartyService.updateUsername(id, thirdPartyDTO);
    }
}

