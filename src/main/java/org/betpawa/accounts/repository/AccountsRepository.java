package org.betpawa.accounts.repository;

import org.betpawa.accounts.model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository  extends JpaRepository<Accounts, Integer> {
    Accounts findByCustomerId(int customerId);
}
