package br.com.springboot.controleorcamento.handler;

import br.com.springboot.controleorcamento.error.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error(ex.getMessage());

        ResourceNotFoundDetails details = ResourceNotFoundDetails.builder()
                .date(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Resource not found")
                .detail(ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .build();

        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);

    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        log.error(ex.getMessage());

        BindingResult result = ex.getBindingResult();

        List<FieldError> fieldErrors = result.getFieldErrors()
                .stream().map(fieldError -> new FieldError(
                        fieldError.getField(),
                        fieldError.getDefaultMessage(),
                        fieldError.getCode(),
                        fieldError.getRejectedValue())
                ).collect(toList());

        List<GlobalError> globalErrors = result.getGlobalErrors().stream()
                .map(objectError -> new GlobalError(
                                objectError.getCode()
                        )
                ).collect(toList());


        ValidationErrorDetails details = ValidationErrorDetails.builder()
                .date(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Field Validation Errors")
                .detail("Field Validation Errors")
                .developerMessage(ex.getClass().getName())
                .fieldErrors(fieldErrors)
                .globalErrors(globalErrors)
                .build();

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             @Nullable Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {

        log.error(ex.getMessage());

        ErrorDetails details = ErrorDetails.builder()
                .date(LocalDateTime.now())
                .status(status.value())
                .title("Internal Exception")
                .detail(ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .build();

        return new ResponseEntity<>(details, headers, status);
    }


}
