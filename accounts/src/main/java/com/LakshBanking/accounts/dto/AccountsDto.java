package com.LakshBanking.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Schema(
        name = "Accounts",
        description ="Schema to hold Account Information"
)
public class AccountsDto {

    @Schema(
            description = "Account Number of Customer" , example = "1234000000"
    )
    @Pattern(regexp = "(^$|[0-9]{10})" , message = "Account Number must be of 10 digits")
    @NotEmpty(message = "Account Number cant be Null")
    private Long accountNumber;

    @Schema(
            description = "Account Type of the Customer",example = "Savings"
    )
    @NotEmpty(message = "Account Type cant be Null")
    private String accountType;

    @Schema(
            description = "Branch Address of the Account",example = "Address"
    )
    @NotEmpty(message = "Branch Address cant be Null")
    private String branchAddress;


}
