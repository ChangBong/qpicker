package com.peopulley.rest.base.aspect;

import com.peopulley.core.base.util.BaseCommonUtil;
import com.peopulley.core.common.enums.ResponseCodeEnum;
import com.peopulley.rest.common.constants.PropertiesValue;
import com.peopulley.rest.common.enums.ServiceReturnMsgEnum;
import com.peopulley.rest.common.exception.CustomException;
import com.peopulley.rest.common.exception.DataNotFoundException;
import com.peopulley.rest.common.exception.ProcessErrorException;
import com.peopulley.rest.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@RestControllerAdvice
public class ErrorHandlerAdvice extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ErrorHandlerAdvice.class);

    private final MessageSource messageSource;

    private final String STATUS = "status";
    private final String MESSAGE = "message";
    private final String ERROR = "error";

    public ErrorHandlerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus httpErrorStatus, WebRequest request) {

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put(STATUS, httpErrorStatus.value());
        errorResponse.put(ERROR, httpErrorStatus.name());

        if(ex.getBindingResult().getFieldErrorCount() > 0){

            String msg = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

            try{
                msg = messageSource.getMessage(msg, null, Locale.KOREA);
            }catch (NoSuchMessageException nme){
                nme.getMessage();
            }

            errorResponse.put(MESSAGE, msg);
        }else{
            errorResponse.put(MESSAGE, ServiceReturnMsgEnum.INVALID_ARGUMENT.getMessage());
        }

        errorLogging(request, ex, null, httpErrorStatus);

        return new ResponseEntity<>(errorResponse, httpErrorStatus);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatus httpErrorStatus, WebRequest request) {

        CustomException customException;

        if (BaseCommonUtil.isStageProdProfile(PropertiesValue.profilesActive)) {
            customException = new CustomException(
                    httpErrorStatus.value(), "Exception occurred", httpErrorStatus);
        } else {
            customException = new CustomException(
                    httpErrorStatus.value()
                    , CommonUtil.isNullReturnStr(ex.getMessage(), ResponseCodeEnum.FAIL.getResultMsg())
                    , httpErrorStatus);
        }

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put(STATUS, customException.getStatus());
        errorResponse.put(MESSAGE, customException.getMessage());
        errorResponse.put(ERROR, customException.getHttpErrorStatus());

        errorLogging(request, ex, body, httpErrorStatus);

        return new ResponseEntity<>(errorResponse, httpErrorStatus);
    }

    // CustomException
    @ExceptionHandler({
            CustomException.class
            , ProcessErrorException.class
            , DataNotFoundException.class
    })
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

    private void errorLogging(WebRequest request, Exception ex, Object body, HttpStatus httpErrorStatus) {
        if (BaseCommonUtil.isStageProdProfile(PropertiesValue.profilesActive)) {
            logger.error("Exception occurred : ".concat(((ServletWebRequest) request).getRequest().getRemoteAddr()
                    .concat(" : ")
                    .concat(Objects.requireNonNull(request.getRemoteUser()))
                    .concat(" : ")
                    .concat(((ServletWebRequest) request).getRequest().getRequestURI())
                    .concat(" : ")
                    .concat(ex.getMessage())));
            // TODO 에러 확인 후 제거
            /*if(StringUtils.hasText(ex.getMessage())){
                Iterator<String> parameterNames = request.getParameterNames();
                if(parameterNames != null){
                    while(parameterNames.hasNext()){
                        String next = parameterNames.next();
                        logger.error(next + " ::: " +  request.getParameter(next));
                    }
                }
            }*/
        }else{
            logger.error("Exception occurred : ".concat(((ServletWebRequest) request).getRequest().getRemoteAddr()
                    .concat(" : ")
                    .concat(Objects.requireNonNull(request.getRemoteUser()))
                    .concat(" : ")
                    .concat(((ServletWebRequest) request).getRequest().getRequestURI())
                    .concat(" : ")
                    .concat(ex.getMessage())));

            // TODO 에러 확인 후 제거
            if(StringUtils.hasText(ex.getMessage())){
                Iterator<String> parameterNames = request.getParameterNames();
                if(parameterNames != null){
                    while(parameterNames.hasNext()){
                        String next = parameterNames.next();
                        logger.error(next + " ::: " +  request.getParameter(next));
                    }
                }
            }
        }
    }
}
