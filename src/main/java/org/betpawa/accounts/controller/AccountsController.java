package org.betpawa.accounts.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.betpawa.accounts.model.*;
import org.betpawa.accounts.repository.AccountsRepository;
import org.betpawa.accounts.services.AccountsServiceConfig;
import org.betpawa.accounts.services.clients.CardsFeignClients;
import org.betpawa.accounts.services.clients.LoansFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class AccountsController {



    private final AccountsRepository accountsRepository;

    private final AccountsServiceConfig accountServiceConfig;

    private final LoansFeignClients loansFeignClients;

    private final CardsFeignClients cardsFeignClients;

    public AccountsController(AccountsRepository accountsRepository, AccountsServiceConfig accountServiceConfig, LoansFeignClients loansFeignClients, CardsFeignClients cardsFeignClients) {
        this.accountsRepository = accountsRepository;
        this.accountServiceConfig = accountServiceConfig;
        this.loansFeignClients = loansFeignClients;
        this.cardsFeignClients = cardsFeignClients;
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

    @PostMapping("/getCustomerDetails")
    @CircuitBreaker(name = "detailsForCustomerSupportApp", fallbackMethod = "myCustomerDetailsFallBack")
    public ResponseEntity<CustomerDetails> getCustomerDetails(@RequestBody Customer customer) {
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
        List<Loans> loans = loansFeignClients.getLoansDetails(customer);
        List<Cards> cards = cardsFeignClients.getCardDetails(customer);

        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccounts(accounts);
        customerDetails.setLoans(loans);
        customerDetails.setCards(cards);

        return ResponseEntity.ok().body(customerDetails);
    }

    private CustomerDetails myCustomerDetailsFallBack(Customer customer, Throwable t) {
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
        List<Loans> loans = loansFeignClients.getLoansDetails(customer);

        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccounts(accounts);
        customerDetails.setLoans(loans);

        return customerDetails;
    }
}
