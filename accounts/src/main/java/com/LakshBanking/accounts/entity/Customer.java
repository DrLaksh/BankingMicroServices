package com.LakshBanking.accounts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
//@Table  used to define table name if not matches
@Getter
@Setter
@ToString
@NoArgsConstructor //provide empty Constructor
@AllArgsConstructor // provide constructor with all values m

public class Customer extends BaseEntity{
//adding validatiosn to fields  and primary key values and auto generations

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String name;

    private String email;

    private String mobileNumber;

}
