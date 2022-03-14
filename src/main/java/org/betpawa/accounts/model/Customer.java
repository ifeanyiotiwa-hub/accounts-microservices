package org.betpawa.accounts.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
public class Customer {

    @Id
    @Column(name = "custom_id")
    private int customerId;
    private String name;
    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "created_date")
    private LocalDate createdDate;
}
