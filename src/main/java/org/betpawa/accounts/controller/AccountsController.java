package org.betpawa.accounts.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.micrometer.core.annotation.Timed;
import org.betpawa.accounts.model.*;
import org.betpawa.accounts.repository.AccountsRepository;
import org.betpawa.accounts.services.AccountsServiceConfig;
import org.betpawa.accounts.services.clients.CardsFeignClients;
import org.betpawa.accounts.services.clients.LoansFeignClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class AccountsController {
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsController.class);
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
    @Timed(value = "getAccountDetails.time", description = "Time taken to return Account details")
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
    @Retry(name = "retryForCustomerDetails", fallbackMethod = "myCustomerDetailsFallBack")
    public ResponseEntity<CustomerDetails> getCustomerDetails(@RequestHeader("betPawa-correlation-id")String correlationId
                                                    , @RequestBody Customer customer) {
        LOGGER.info("getCustomerDetails() method started.");
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
        List<Loans> loans = loansFeignClients.getLoansDetails(correlationId,customer);
        List<Cards> cards = cardsFeignClients.getCardDetails(customer);

        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccounts(accounts);
        customerDetails.setLoans(loans);
        customerDetails.setCards(cards);
    
        LOGGER.info("getCustomerDetails() method ended.");
        return ResponseEntity.ok().body(customerDetails);
    }

    private ResponseEntity<CustomerDetails> myCustomerDetailsFallBack(@RequestHeader("betPawa-correlation-id") String correlationId, Customer customer, Throwable t) {
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
        List<Loans> loans = loansFeignClients.getLoansDetails(correlationId,customer);

        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccounts(accounts);
        customerDetails.setLoans(loans);

        return ResponseEntity.ok().body(customerDetails);
    }
}
