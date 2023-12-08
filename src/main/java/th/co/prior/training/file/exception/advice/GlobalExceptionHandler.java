package th.co.prior.training.file.exception.advice;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import th.co.prior.training.file.exception.MyException;
import th.co.prior.training.file.exception.MyNormalException;
import th.co.prior.training.file.model.ResponseModel;

import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @PostConstruct
    public void init(){
        log.info("GlobalExceptionHandler init");
    }

    @ExceptionHandler({IOException.class, FileNotFoundException.class})
    public ResponseEntity<ResponseModel<Void>> handlerIOException(Exception ioException) {
        ResponseModel<Void> result = new ResponseModel<>();
        result.setCode(500);
        result.setMessage(ioException.getMessage());
        ResponseEntity<ResponseModel<Void>> x = new ResponseEntity<>(result, HttpStatus.valueOf(500));
        return x;
    }

}
