package com.LakshBanking.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error Response Information"
)
public class ErrorResponseDto {

    @Schema(
            description = "Api invoked by the client "
    )
    private String apiPath;

    @Schema(
            description = "Error Code wen Api called"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error Message wen Api called"
    )
    private String errorMessage;


    @Schema(
            description = "Time of Error Happened"
    )
    private LocalDateTime errorTime;

}
