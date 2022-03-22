package org.betpawa.accounts.services.clients;


import org.betpawa.accounts.model.Customer;
import org.betpawa.accounts.model.Loans;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("loans")
public interface LoansFeignClients {

    @RequestMapping(method = RequestMethod.POST, value = "myLoans", consumes = "application/json")
    List<Loans> getLoansDetails(@RequestHeader("betPawa-correlation-id") String correlationId, @RequestBody Customer customer);
}
