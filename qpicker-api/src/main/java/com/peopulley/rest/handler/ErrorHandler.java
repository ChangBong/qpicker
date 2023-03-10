package com.peopulley.rest.handler;

import com.peopulley.rest.exception.CustomException;
import com.peopulley.rest.exception.ProcessErrorException;
import com.peopulley.rest.exception.SearchErrorException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    private final String STATUS = "status";
    private final String MESSAGE = "message";
    private final String ERROR = "error";

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatus httpErrorStatus, WebRequest request) {

        CustomException customException;

        if(!"prod".equals(System.getProperty("spring.profiles.active"))) {
            customException = new CustomException(
                    httpErrorStatus.value(), "Exception occurred", httpErrorStatus);
        }else{
            customException = new CustomException(
                    httpErrorStatus.value(), ex.getMessage(), httpErrorStatus);
        }

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put(STATUS, customException.getStatus());
        errorResponse.put(MESSAGE, customException.getMessage());
        errorResponse.put(ERROR, customException.getHttpErrorStatus());

        errorLogging(request, ex, body, httpErrorStatus);

        return new ResponseEntity<>(errorResponse, httpErrorStatus);
    }

    // CustomException
    @ExceptionHandler({SearchErrorException.class, ProcessErrorException.class})
    @ResponseBody
    public ResponseEntity<Object> handlerCustomException(Exception ex, WebRequest request) {

        CustomException customException = (CustomException) ex;

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put(STATUS, customException.getStatus());
        errorResponse.put(MESSAGE, customException.getMessage());
        errorResponse.put(ERROR, customException.getHttpErrorStatus());

        errorLogging(request, ex, null, customException.getHttpErrorStatus());

        return new ResponseEntity<>(errorResponse, customException.getHttpErrorStatus());
    }

    private void errorLogging(WebRequest request, Exception ex, Object body, HttpStatus httpErrorStatus){
        if(httpErrorStatus.is5xxServerError()) {
            logger.error("Exception occurred", ex.getMessage());
        } else {
            logger.warn("Exception occurred : " + ex.getMessage());
        }
    }

    @ExceptionHandler({WebExchangeBindException.class})
    @ResponseBody
    public ResponseEntity<Object> handlerWebExchangeBindException(WebExchangeBindException ex, WebRequest request) {
        logger.debug(ex.getMessage());
//        CustomException customException = (CustomException) ex;
//
//        Map<String, Object> errorResponse = new HashMap<>();
//        errorResponse.put(STATUS, customException.getStatus());
//        errorResponse.put(MESSAGE, customException.getMessage());
//        errorResponse.put(ERROR, customException.getHttpErrorStatus());
//
//        errorLogging(request, ex, null, customException.getHttpErrorStatus());

        return null;
    }

}
