package com.LakshBanking.accounts.Controller;

import com.LakshBanking.accounts.constants.AccountConstants;
import com.LakshBanking.accounts.dto.CustomerDto;
import com.LakshBanking.accounts.dto.ErrorResponseDto;
import com.LakshBanking.accounts.dto.ResponseDto;
import com.LakshBanking.accounts.entity.Customer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.LakshBanking.accounts.service.IAccountService;


@RestController
@RequestMapping(path="/api/account", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
@Tag(
        name = "CRUD Rest Api for Accounts ",
        description = "This Provide CRUD Operation for Accounts with REST APIs Endpoints - CREATE , UPDATE , FETCH , DELETE "
)
public class AccountsController {

//    //This Method is Only for Testing
//    @GetMapping("sayHello")
//    public String sayHello(){
//        return "Hello World";
//    }

    //whenever we have single constructor , do it with @AllArgsConstructor
    private IAccountService iAccountService;

    @Operation(
            summary = "Create Account Operation of REST APIs",
            description = "This Rest API help to create a new Customer in Accounts "
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP status created"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
//Here we are using Service layer for this Action
        iAccountService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(AccountConstants.STATUS_201,AccountConstants.MESSAGE_201));
    }

    //adding method to Read accounts details with Get Mapping

    @Operation(
            summary = "Fetch Account Operation of REST APIs",
            description = "This Rest API help to Fetch the Customer details with mobile number "
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status OK"
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})" , message = "Mobile Number must be of 10 digits")String mobileNumber){
        CustomerDto customerDto = iAccountService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

//update operation
    @Operation(
            summary = "Update Account Operation of REST APIs",
            description = "This Rest API help to Update the Customer details except the Account Number "
    )
    @ApiResponses({@ApiResponse(
            responseCode = "200",
            description = "HTTP status OK"
    ),
            @ApiResponse(
                    responseCode = "417",
                    description = "HTTP status INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )})

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto){
        boolean isUpdated = iAccountService.updateAccount(customerDto);
        if (isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountConstants.STATUS_200,AccountConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(AccountConstants.STATUS_417,AccountConstants.MESSAGE_417_UPDATE));
        }
    }

    //Delete Method
    @Operation(
            summary = "Delete Account Operation of REST APIs",
            description = "This Rest API help to Delete the Customer  "
    )
    @ApiResponses({@ApiResponse(
            responseCode = "200",
            description = "HTTP status OK"
    ),
            @ApiResponse(
                    responseCode = "417",
                    description = "HTTP status INTERNAL SERVER ERROR",
            content = @Content(
            schema = @Schema(implementation = ErrorResponseDto.class)
    )
            )})
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})" , message = "Mobile Number must be of 10 digits")String mobileNumber){
        boolean isDeleted = iAccountService.deleteAccount(mobileNumber);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountConstants.STATUS_200,AccountConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(AccountConstants.STATUS_417,AccountConstants.MESSAGE_417_DELETE));
        }
    }
}
