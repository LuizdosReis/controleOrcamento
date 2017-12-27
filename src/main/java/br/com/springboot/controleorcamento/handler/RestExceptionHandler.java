package br.com.springboot.controleorcamento.handler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.springboot.controleorcamento.error.ErrorDetails;
import br.com.springboot.controleorcamento.error.ResourceNotFoundDetails;
import br.com.springboot.controleorcamento.error.ValidationErrorDetails;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException){
		ResourceNotFoundDetails details = ResourceNotFoundDetails.Builder
			.newBuilder()
			.timestamp(new Date().getTime())
			.status(HttpStatus.NOT_FOUND.value())
			.title("Resource not found")
			.detail(resourceNotFoundException.getMessage())
			.developerMessage(resourceNotFoundException.getClass().getName())
			.build();
		
		return new ResponseEntity<>(details,HttpStatus.NOT_FOUND);
		
	}
	
	
	@Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException manvException,
                                                          HttpHeaders headers,
                                                          HttpStatus status,
                                                          WebRequest request) {
		
		List<FieldError> fieldErrors = manvException.getBindingResult().getFieldErrors();
		String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(";"));
		String field = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(";"));
		
		
		ValidationErrorDetails details = ValidationErrorDetails.Builder
			.newBuilder()
			.timestamp(new Date().getTime())
			.status(status.value())
			.title("Field Validation Error")
			.detail("Field Validation Error")
			.developerMessage(manvException.getClass().getName())
			.field(field)
			.fieldMessage(fieldMessages)
			.build();
		
		return new ResponseEntity<>(details,HttpStatus.BAD_REQUEST);
		
	}
	
	
	@Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {
		
		ErrorDetails errorDetails = ErrorDetails.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(status.value())
                .title("Internal Exception")
                .detail(ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .build();
        return new ResponseEntity<>(errorDetails, headers, status);
		
	}
}
