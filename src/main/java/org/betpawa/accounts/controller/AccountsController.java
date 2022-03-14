package org.betpawa.accounts.controller;


import org.betpawa.accounts.model.Accounts;
import org.betpawa.accounts.model.Customer;
import org.betpawa.accounts.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController {



    private final AccountsRepository accountsRepository;

    @Autowired
    public AccountsController(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }


    @PostMapping("/accounts")
    public Accounts getAccounts(@RequestBody Customer customer) {
        return accountsRepository.findByCustomerId(customer.getCustomerId());
    }
}
