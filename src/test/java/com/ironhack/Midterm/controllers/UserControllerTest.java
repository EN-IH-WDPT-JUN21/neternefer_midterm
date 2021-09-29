package com.ironhack.Midterm.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.Midterm.controller.dto.AccountHolderDTO;
import com.ironhack.Midterm.controller.dto.ThirdPartyDTO;
import com.ironhack.Midterm.dao.AccountHolder;
import com.ironhack.Midterm.dao.Address;
import com.ironhack.Midterm.dao.ThirdParty;
import com.ironhack.Midterm.repository.AccountHolderRepository;
import com.ironhack.Midterm.repository.AddressRepository;
import com.ironhack.Midterm.repository.ThirdPartyRepository;
import com.ironhack.Midterm.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    private List<Address> addresses;
    private List<AccountHolder> accountHolders;
    private List<ThirdParty> thirdParty;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        addresses = addressRepository.saveAll(
                List.of(
                        new Address("Berlin", "Germany", "Neue", 12),
                        new Address("Warsaw", "Poland", "Nowa", 34),
                        new Address("London", "United Kingdom", "New", 65))
        );
        accountHolders = accountHolderRepository.saveAll(
                List.of(
                        new AccountHolder("Mary Watson", "$2a$12$FmQaXTyTXSM2ZwcHv0xtE.z/XQkUF2uty7l1bQsPGA1Sw2oVmwpya",
                                LocalDate.of(2000, 9, 20), addresses.get(0), addresses.get(0)),
                        new AccountHolder("Peter Parker", "$2a$12$bEc1TKDEFOAizTV7ucdx4e/zE5IwCkus1Y8or.wRCNvgCaxrHCgAG",
                                LocalDate.of(1999, 12, 12), addresses.get(1), addresses.get(1))
                )
        );
        thirdParty = thirdPartyRepository.saveAll(
                List.of(
                        new ThirdParty("Patti Smith", "$2a$12$9iP7Ktzbzsyubg7Lu7ZYD.ZxmIkTojZ0yUbB/4TwQ3.UXdPMb8HX.",
                                "$2a$12$9iP7Ktzbzsyubg7Lu7ZYD.ZxmIkTojZ0yUbB/4TwQ3.UXdPMb8HX."),//pass5, MyHashKey
                        new ThirdParty("Cyndi Lauper", "$2a$12$FMFW5mWdyaQ8n.wFzYz8guVQupIkE5egntMW2zOzLlPrgIXLBCfGG",
                                "$2a$12$hKtwUOcFvSJ6AC0jfmasnuJ5H/kCuj5Ff48DnSivRWIFZvILRLGy.")//pass6, MyHashKey2
                )
        );
    }

    @Test
    void User_findAllAccountHolders() throws Exception {
        MvcResult result = mockMvc.perform(get("/users/accountHolders")).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Mary"));
        assertTrue(result.getResponse().getContentAsString().contains("Peter"));
    }

    @Test
    void User_findAllThirdParty() throws Exception {
        MvcResult result = mockMvc.perform(get("/users/thirdParty")).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Patti"));
        assertTrue(result.getResponse().getContentAsString().contains("Cyndi"));
    }

    @Test
    void User_findAccountHolderById() throws Exception {
        Long id = accountHolders.get(0).getId();
        MvcResult result = mockMvc.perform(get("/users/accountHolders/" + id)).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Mary Watson"));
    }

    @Test
    void User_findThirdPartyById() throws Exception {
        Long id = thirdParty.get(1).getId();
        MvcResult result = mockMvc.perform(get("/users/thirdParty/" + id)).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Cyndi Lauper"));
    }

    @Test
    void store_newAccountHolder() throws Exception {
        AccountHolderDTO accountHolderDTO;
        accountHolderDTO = new AccountHolderDTO();
        accountHolderDTO.setId(5L);
        accountHolderDTO.setUsername("Pauline Var");
        accountHolderDTO.setPassword("$2a$12$D4pnqhNFpyBv2IvXbl7muueAtLCsrINOVelwrq.xwnT7Qa0pmQc4G");
        String body = objectMapper.writeValueAsString(accountHolderDTO);
        MvcResult result = mockMvc.perform(post("/users/accountHolders").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Pauline Var"));
    }

    @Test
    void store_newThirdParty() throws Exception {
        ThirdPartyDTO thirdPartyDTO;
        thirdPartyDTO = new ThirdPartyDTO();
        thirdPartyDTO.setId(5L);
        thirdPartyDTO.setUsername("Mark Twain");
        thirdPartyDTO.setPassword("$2a$12$D4pnqhNFpyBv2IvXbl7muueAtLCsrINOVelwrq.xwnT7Qa0pmQc4G");
        String body = objectMapper.writeValueAsString(thirdPartyDTO);
        MvcResult result = mockMvc.perform(post("/users/thirdParty").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Mark Twain"));
    }

    @Test
    void User_updateUsernameAccountHolder() throws Exception {
        Long id = accountHolders.get(0).getId();
        AccountHolder holder;
        AccountHolderDTO accountHolderDTO;
        accountHolderDTO = new AccountHolderDTO();
        accountHolderDTO.setUsername("Mariana Fara");
        String body = objectMapper.writeValueAsString(accountHolderDTO);
        mockMvc.perform(patch("/users/accountHolders/" + id).content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent()).andReturn();
        holder = accountHolderRepository.findById(id).get();
        assertEquals("Mariana Fara", holder.getUsername());
    }

    @Test
    void User_updateUsernameThirdParty() throws Exception {
        Long id = thirdParty.get(1).getId();
        ThirdParty party;
        ThirdPartyDTO thirdPartyDTO;
        thirdPartyDTO = new ThirdPartyDTO();
        thirdPartyDTO.setUsername("Edgar Allan Poe");
        String body = objectMapper.writeValueAsString(thirdPartyDTO);
        mockMvc.perform(patch("/users/thirdParty/" + id).content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent()).andReturn();
        party = thirdPartyRepository.findById(id).get();
        assertEquals("Edgar Allan Poe", party.getUsername());
    }
}

