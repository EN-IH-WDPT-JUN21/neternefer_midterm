package com.ironhack.Midterm.controller;

import com.ironhack.Midterm.controller.dto.CheckingAccountDTO;
import com.ironhack.Midterm.controller.dto.CreditCardDTO;
import com.ironhack.Midterm.controller.dto.SavingsAccountDTO;
import com.ironhack.Midterm.controller.dto.StudentCheckingAccountDTO;
import com.ironhack.Midterm.dao.*;
import com.ironhack.Midterm.repository.CheckingAccountRepository;
import com.ironhack.Midterm.repository.CreditCardRepository;
import com.ironhack.Midterm.repository.SavingsAccountRepository;
import com.ironhack.Midterm.repository.StudentCheckingAccountRepository;
import com.ironhack.Midterm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    CheckingAccountService checkService;

    @Autowired
    StudentCheckingAccountService studentService;

    @Autowired
    SavingsAccountService saveService;

    @Autowired
    CreditCardService creditService;

    @Autowired
    AccountHolderService accountHolderService;

    @Autowired
    AdminService adminService;

    @Autowired
    CheckingAccountRepository checkingAccountRepository;

    @Autowired
    StudentCheckingAccountRepository studentCheckingAccountRepository;

    @Autowired
    SavingsAccountRepository savingsAccountRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

    //GET

    @GetMapping("/accounts/checking")
    @ResponseStatus(HttpStatus.OK)
    public List<CheckingAccount> getCheckingAccounts(){ return checkingAccountRepository.findAll(); }

    @GetMapping("/accounts/saving")
    @ResponseStatus(HttpStatus.OK)
    public List<SavingsAccount> getSavingsAccounts(){
        return savingsAccountRepository.findAll();
    }

    @GetMapping("/accounts/student")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentCheckingAccount> getStudentAccountById(){
        return studentCheckingAccountRepository.findAll();
    }

    @GetMapping("/accounts/credit")
    @ResponseStatus(HttpStatus.OK)
    public List<CreditCard> getCreditAccounts(){
        return creditCardRepository.findAll();
    }

    //GET BY ID

    @GetMapping("/accounts/checking/{check_id}")
    @ResponseStatus(HttpStatus.OK)
    public CheckingAccount getCheckingAccountById(@PathVariable("check_id") long check_id){
        return checkingAccountRepository.findById(check_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
    }

    @GetMapping("/accounts/saving/{save_id}")
    @ResponseStatus(HttpStatus.OK)
    public SavingsAccount getSavingsAccountById(@PathVariable("save_id") long save_id){
        return savingsAccountRepository.findById(save_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
    }

    @GetMapping("/accounts/student/{student_id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentCheckingAccount getStudentAccountById(@PathVariable("student_id") long student_id){
        return studentCheckingAccountRepository.findById(student_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
    }

    @GetMapping("/accounts/credit/{credit_id}")
    @ResponseStatus(HttpStatus.OK)
    public CreditCard getCreditAccountById(@PathVariable("credit_id") long credit_id){
        return creditCardRepository.findById(credit_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
    }

    @GetMapping(name = "accounts/balance/{id}/{user_id}", value = "user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void checkBalanceUser(@PathVariable("id") long id,
                                 @PathVariable("user_id") long user_id) {
        accountHolderService.checkBalance(id, user_id);
    }

    //POST

    @PostMapping("accounts/checking")
    @ResponseStatus(HttpStatus.CREATED)
    public Account store(@RequestBody @Valid CheckingAccountDTO checkingAccountDTO) {
        return checkService.store(checkingAccountDTO);
    }

    @PostMapping("accounts/saving")
    @ResponseStatus(HttpStatus.CREATED)
    public SavingsAccount store(@RequestBody @Valid SavingsAccountDTO savingsAccountDTO) {
        return saveService.store(savingsAccountDTO);
    }

    @PostMapping("accounts/student")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentCheckingAccount store(@RequestBody @Valid StudentCheckingAccountDTO studentCheckingAccountDTO) {
        return studentService.store(studentCheckingAccountDTO);
    }

    @PostMapping("accounts/credit")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard store(@RequestBody @Valid CreditCardDTO creditCardDTO) {
        return creditService.store(creditCardDTO);
    }

    // PATCH

    @PatchMapping(name = "accounts/balance/{id}/{admin_id}", value = "admin")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void checkBalanceAdmin(@PathVariable("id") long id,
                                  @PathVariable("admin_id") long admin_id) {
        adminService.checkBalance(id,admin_id);
    }

    @PatchMapping("accounts/transfer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Transaction transfer(@RequestBody OperationRequest operationRequest) {
        return accountService.operation(operationRequest);
    }
}

