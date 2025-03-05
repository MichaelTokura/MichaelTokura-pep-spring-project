package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
@Transactional
public class AccountService {
    AccountRepository accRepo;
    Account acc;
    AccountService accountService;
    public AccountService(AccountRepository accountRepository){
        this.accRepo = accountRepository;
    }



    public Account getAccountByUsername(String un) {
        return accRepo.getAccountByUsername(un);
    }
    public Account saveAccount(Account acc) {
        return accRepo.save(acc);
    }



    public String getUserNameString(Account acc2) {
        return acc2.getUsername();
    }
    public Account getAccountById(int l) {
        return accRepo.getById(l);
    }

    public Account checkAccount(String un, String pw) {
        Account account = getAccountByUsername(un);
        if (account.getPassword().contains(pw)){
            return account;
        }
        return null;
    }
}