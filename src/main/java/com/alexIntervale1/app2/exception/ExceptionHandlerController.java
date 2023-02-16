package com.alexIntervale1.app2.exception;

import com.alexIntervale1.app2.model.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(CustomAppException.class)
    public ErrorMessage customAppException(CustomAppException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.OK.value(),
                ex.getMessage(),
                request.getDescription(false));

        return message;
    }

    @ExceptionHandler(Exception.class)
    public ErrorMessage allAppException(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.OK.value(),
                ex.getMessage(),
                request.getDescription(false));

        return message;
    }
}
