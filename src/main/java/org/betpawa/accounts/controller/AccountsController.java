package org.betpawa.accounts.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.betpawa.accounts.model.Accounts;
import org.betpawa.accounts.model.Customer;
import org.betpawa.accounts.model.Properties;
import org.betpawa.accounts.repository.AccountsRepository;
import org.betpawa.accounts.services.AccountsServiceConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AccountsController {



    private final AccountsRepository accountsRepository;

    private final AccountsServiceConfig accountServiceConfig;

    public AccountsController(AccountsRepository accountsRepository, AccountsServiceConfig accountServiceConfig) {
        this.accountsRepository = accountsRepository;
        this.accountServiceConfig = accountServiceConfig;
    }


    @PostMapping("/myAccounts")
    public Accounts getAccountsDetails(@RequestBody Customer customer) {
        return accountsRepository.findByCustomerId(customer.getCustomerId());
    }

    @GetMapping("/accounts/properties")
    public ResponseEntity<Properties> getPropertiesDetails() throws JsonProcessingException {
//        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties.AccountsPropertiesBuilder().msg(accountServiceConfig.getMsg())
                .buildVersion(accountServiceConfig.getBuildVersion())
                .mailDetails(accountServiceConfig.getMailDetails())
                .activeBranches(accountServiceConfig.getActiveBranches())
                .build();

        return ResponseEntity.ok().body(properties);
    }
}
