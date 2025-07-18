package com.LakshBanking.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to Hold Customer Account Information"
)
public class CustomerDto {

    @Schema(
            description = "Name of the Customer" , example = "Customer Name"
    )
    @NotEmpty(message = "Name cant be Null")
    @Size(min = 5 , max = 30 , message = "The customer name should be 5 to 30 long ")
    private String name;

    @Schema(
            description = "Email address of the Customer", example = "customer@email.com"
    )
    @Email(message = "Email should be Valid")
    @NotEmpty(message = "Email cant be Null")
    private String email;

    @Schema(
            description = "Mobile Number of the Customer",example = "1234567890"
    )
    @Pattern(regexp = "(^$|[0-9]{10})" , message = "Mobile Number must be of 10 digits")
    @NotEmpty(message = "Mobile Number cant be Null")
    private String mobileNumber;

    @Schema(
            description = "Account Details of Customer "
    )
    private AccountsDto accountsDto;
}
