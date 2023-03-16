package com.peopulley.rest.base.service;

import com.peopulley.core.base.domain.Member;
import com.peopulley.rest.common.dto.SecurityMember;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Member> {
    @Override
    public Optional<Member> getCurrentAuditor() {
        //Security에서 인증된 사용자 정보를 가져온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(null == authentication || !authentication.isAuthenticated()){
            return null;
        }
        return Optional.of(((SecurityMember)authentication.getPrincipal()).getMember());
    }
}