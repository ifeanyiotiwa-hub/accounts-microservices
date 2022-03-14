package org.betpawa.accounts.repository;

import org.betpawa.accounts.model.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository  extends CrudRepository<Accounts, Long> {
    Accounts findByCustomerId(int customerId);
}
