package com.LakshBanking.accounts.service.Impl;

import com.LakshBanking.accounts.constants.AccountConstants;
import com.LakshBanking.accounts.dto.AccountsDto;
import com.LakshBanking.accounts.dto.CustomerDto;
import com.LakshBanking.accounts.entity.Accounts;
import com.LakshBanking.accounts.entity.Customer;
import com.LakshBanking.accounts.exception.CustomerAlreadyExistsException;
import com.LakshBanking.accounts.exception.ResourceNotFoundException;
import com.LakshBanking.accounts.mapper.AccountsMapper;
import lombok.AllArgsConstructor;
import com.LakshBanking.accounts.mapper.CustomerMapper;
import org.springframework.stereotype.Service;
import com.LakshBanking.accounts.repository.AccountsRepository;
import com.LakshBanking.accounts.repository.CustomerRepository;
import com.LakshBanking.accounts.service.IAccountService;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {
    //Autowire Repositories automatically by @AllArgsConstructor

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        //Using Mapper Here ,
        Customer customer = CustomerMapper.mapToCustomer(customerDto , new Customer());
        //checking the duplicate account exception Here
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer Already Present for Given Mobile Number" + customerDto.getMobileNumber());
            //as we need to use exception in other place we need to write it again so we create GlobalException Logic
        }
//        customer.setCreatedAt(LocalDateTime.now());
//        customer.setCreatedBy("Anonymous");
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
        //Also need to deal the Exceptions , so creating exception package
    }


    //creating New Account for dealing with Account issues in createAccount method
    private Accounts createNewAccount(Customer customer){
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccountNumber = 1234000000L+new Random().nextInt(90000);
        newAccount.setAccountNumber(randomAccountNumber);
        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);
//        newAccount.setCreatedAt(LocalDateTime.now());
//        newAccount.setCreatedBy("Anonymous");
        return newAccount;
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        //we are checking by mobileNumber but there will be scenario where no mobileNumber is There so make a exception
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer","mobileNumber",mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account","mobileNumber",mobileNumber)
        );
        //Implement AccountsDto to CustomerDto to provide DTo Pattern for bind data output
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer , new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts,new AccountsDto()));
        return customerDto;
    }


    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto != null){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account","AccountNumber",accountsDto.getAccountNumber().toString())
            );
//            accounts.setUpdatedAt(LocalDateTime.now());
//            accounts.setUpdatedBy("DBA");
            AccountsMapper.mapToAccounts(accountsDto,accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    ()-> new ResourceNotFoundException("Customer","CustomerID",customerId.toString())
            );
//            customer.setUpdatedAt(LocalDateTime.now());
//            customer.setUpdatedBy("DBA");
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated=true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer","mobileNumber",mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

}
