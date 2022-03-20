package org.betpawa.accounts.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class Cards {

    private long cardId;

    private int customerId;

    private String cardNumber;

    private String cardType;

    private int totalLimit;

    private int amountUsed;

    private int availableAmount;

    private LocalDate createdDate;
}
