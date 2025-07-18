package com.LakshBanking.accounts.mapper;

import com.LakshBanking.accounts.dto.AccountsDto;
import com.LakshBanking.accounts.entity.Accounts;

public class AccountsMapper {

//    accounts to AccountsDto
    public static AccountsDto mapToAccountsDto(Accounts accounts , AccountsDto accountsDto){
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        return accountsDto;
    }

    //accountsDt to accounts
    public static  Accounts mapToAccounts(AccountsDto accountsDto , Accounts accounts){
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }

}
