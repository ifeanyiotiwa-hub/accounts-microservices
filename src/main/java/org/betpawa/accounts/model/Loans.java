package org.betpawa.accounts.model;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@ToString
public class Loans {

    private long loanNumber;

    private int customerId;

    private LocalDate startDate;

    private String loanType;

    private int totalLoan;

    private int outstandingAmount;

    private int amountPaid;

    private LocalDate createdDate;
}
