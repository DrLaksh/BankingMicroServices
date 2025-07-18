package com.LakshBanking.accounts.repository;

import com.LakshBanking.accounts.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts , Long> {
    Optional<Accounts> findByCustomerId(Long customerId);

    @Transactional
    // we can roll back by this @Transactional
    @Modifying
    //Modify th data Here
    void deleteByCustomerId(Long customerId);

}
