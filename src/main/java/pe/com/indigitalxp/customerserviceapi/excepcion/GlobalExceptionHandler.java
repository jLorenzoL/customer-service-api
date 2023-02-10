package pe.com.indigitalxp.customerserviceapi.excepcion;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {

        return super.handleExceptionInternal(ex, this.getDefaultError(ex, status), headers, status, request);
    }

    private Map<String, Object> getDefaultError(Exception ex, HttpStatus status) {

        Map<String, Object> values = new HashMap<>();
        values.put("timestamp", new Date());
        values.put("status", status.value());
        values.put("message", ex.getCause().getCause().getMessage());
        values.put("state", Boolean.FALSE);

        return values;
    }


    @Data
    public static class ErrorValidation {
        private String error;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        Map<String, Object> values = new HashMap<>();

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        List<ErrorValidation> lstErrors = errors.stream().map(x->{
            ErrorValidation error = new ErrorValidation();
            error.setError(x);
            return error;
        }).collect(Collectors.toList());

        values.put("timestamp", new Date());
        values.put("errors", errors);
        values.put("status", status.value());
        values.put("state", Boolean.FALSE);

        return new ResponseEntity<>(values, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {

        Map<String, Object> values = new HashMap<>();
        values.put("message", ex.getMessage());
        values.put("state", Boolean.FALSE);

        return new ResponseEntity<>(values, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> constraintViolationException(ConstraintViolationException ex, WebRequest request) {

        List<String> errors = new ArrayList<>();
        ex.getConstraintViolations().forEach(cv -> errors.add(cv.getMessage()));

        Map<String, List<String>> values = new HashMap<>();
        values.put("errors", errors);

        return new ResponseEntity<>(values, HttpStatus.BAD_REQUEST);
    }
}
