package com.peopulley.rest.common.filter;

import com.peopulley.core.base.util.BaseCommonUtil;
import com.peopulley.rest.base.service.UserService;
import com.peopulley.rest.common.constants.Constants;
import com.peopulley.rest.common.constants.PropertiesValue;
import com.peopulley.rest.util.CookieUtil;
import com.peopulley.rest.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserService userService;

    private final CookieUtil cookieUtil;

    private final JwtUtil jwtUtil;

    public JwtRequestFilter(JwtUtil jwtUtil, UserService userService, CookieUtil cookieUtil){
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.cookieUtil = cookieUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String membername = null;
        String jwt = jwtUtil.getAccessTokenFromHeader(httpServletRequest, Constants.AUTHORIZATION);
        UserDetails userDetails = null;

        if (BaseCommonUtil.isNotStageProdProfile(PropertiesValue.profilesActive)) {
            userDetails = userService.loadUserByUsername(jwt);
        }else {
            if (StringUtils.hasText(jwt)) {
                membername = jwtUtil.getMembername(jwt);

                if (membername != null) {
                    userDetails = userService.loadUserByUsername(membername);

                    try {
                        jwtUtil.validateToken(jwt, userDetails);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (userDetails != null) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

}
