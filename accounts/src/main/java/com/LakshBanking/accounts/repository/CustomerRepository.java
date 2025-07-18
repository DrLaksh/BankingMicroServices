package com.LakshBanking.accounts.repository;

import com.LakshBanking.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //derived name Method
    Optional<Customer> findByMobileNumber(String mobileNumber);

}
