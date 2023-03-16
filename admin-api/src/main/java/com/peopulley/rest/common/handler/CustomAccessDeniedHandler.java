package com.peopulley.rest.common.handler;

import com.peopulley.core.common.enums.ResponseCodeEnum;
import com.peopulley.rest.common.constants.Constants;
import com.peopulley.rest.io.response.CommonResponse;
import com.peopulley.rest.util.CommonUtil;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Log
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {

        CommonResponse response = new CommonResponse(HttpStatus.UNAUTHORIZED.value(), ResponseCodeEnum.UNAUTHORIZED.getResultCode(), ResponseCodeEnum.UNAUTHORIZED.getResultMsg(),null);

/*
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityMember member = (SecurityMember)authentication.getPrincipal();
        Collection<GrantedAuthority> authorities = member.getAuthorities();

        if(hasRole(authorities, MemberRole.ROLE_NOT_PERMITTED.name())){
            response.setResultMsg(ServiceReturnMsgEnum.ROLE_NOT_PERMITTED.getMessage());
        }
*/

        httpServletResponse.setContentType(Constants.CONTENT_TYPE);
        httpServletResponse.getWriter().write(CommonUtil.convertObjectToJson(response));
    }

    private boolean hasRole(Collection<GrantedAuthority> authorites, String role){
        return authorites.contains(new SimpleGrantedAuthority(role));
    }

}
