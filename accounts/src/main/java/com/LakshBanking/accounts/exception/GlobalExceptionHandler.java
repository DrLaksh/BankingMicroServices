package com.LakshBanking.accounts.exception;

import com.LakshBanking.accounts.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
//adding the override method from response entity which is used by accountsController to shoe message of validation

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String,String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.forEach((error)->{
            String fieldName = ((FieldError)error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors.put(fieldName,validationMsg);
                });
        return new ResponseEntity<>(validationErrors,HttpStatus.BAD_REQUEST);
}


    @ExceptionHandler(CustomerAlreadyExistsException.class)
    //this shows what exception this method handles
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException e , WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
        webRequest.getDescription(false),
        HttpStatus.BAD_REQUEST,
        e.getMessage(),
        LocalDateTime.now()
        );
        return  new ResponseEntity<>(errorResponseDto , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    //this shows what exception this method handles
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException e , WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                e.getMessage(),
                LocalDateTime.now()
        );
        return  new ResponseEntity<>(errorResponseDto , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    //this shows what exception this method handles
    public ResponseEntity<ErrorResponseDto> handleRuntimeException(Exception e , WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage(),
                LocalDateTime.now()
        );
        return  new ResponseEntity<>(errorResponseDto , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
