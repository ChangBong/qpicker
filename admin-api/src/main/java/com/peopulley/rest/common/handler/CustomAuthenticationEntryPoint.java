package com.peopulley.rest.common.handler;

import com.peopulley.rest.common.constants.Constants;
import com.peopulley.rest.common.enums.ServiceReturnMsgEnum;
import com.peopulley.rest.io.response.CommonResponse;
import com.peopulley.rest.util.CommonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        CommonResponse response = new CommonResponse(HttpStatus.UNAUTHORIZED.value(),"error", ServiceReturnMsgEnum.UNAUTHORIZED.getMessage(), null);

        httpServletResponse.setContentType(Constants.CONTENT_TYPE);
        httpServletResponse.getWriter().write(CommonUtil.convertObjectToJson(response));
    }
}
