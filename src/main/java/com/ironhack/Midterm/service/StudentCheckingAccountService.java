package com.ironhack.Midterm.service;

import com.ironhack.Midterm.controller.dto.StudentCheckingAccountDTO;
import com.ironhack.Midterm.dao.StudentCheckingAccount;
import com.ironhack.Midterm.repository.StudentCheckingAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class StudentCheckingAccountService {

    @Autowired
    StudentCheckingAccountRepository studentCheckingAccountRepository;

    public StudentCheckingAccount store(StudentCheckingAccountDTO studentCheckingAccountDTO) {
        Optional<StudentCheckingAccount> student = studentCheckingAccountRepository.findById(studentCheckingAccountDTO.getId());
        if(student.isEmpty()) {
            try {
                StudentCheckingAccount newStudent = new StudentCheckingAccount(studentCheckingAccountDTO.getBalance(),
                        studentCheckingAccountDTO.getPrimaryOwner(), studentCheckingAccountDTO.getSecondaryOwner(),
                        studentCheckingAccountDTO.getSecretKey(), studentCheckingAccountDTO.getCreationDate(),
                        studentCheckingAccountDTO.getStatus());
                studentCheckingAccountRepository.save(newStudent);
                return newStudent;
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provided values not valid.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student account with this id already exists in the system.");
        }
    }
}

