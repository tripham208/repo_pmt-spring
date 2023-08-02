package vn.id.pmt.spring.exception.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.id.pmt.spring.constants.ApiResponseResult;
import vn.id.pmt.spring.dto.RestApiResponse;
import vn.id.pmt.spring.exception.NotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(final NotFoundException ex) {
        RestApiResponse<?> response = RestApiResponse.builder()
                .result(ApiResponseResult.ER)
                .errorMessage(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }
}