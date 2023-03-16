package com.peopulley.rest.base.service;

import com.peopulley.core.common.enums.ResponseCodeEnum;
import com.peopulley.core.common.enums.StatusEnum;
import com.peopulley.core.base.domain.Member;
import com.peopulley.core.base.repository.MemberRepository;
import com.peopulley.rest.common.dto.SecurityMember;
import com.peopulley.rest.common.exception.ProcessErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmailAndMemberStatus(username, StatusEnum.USER_NORMAL.getStatusCode()).orElseThrow(()
                -> new ProcessErrorException(ResponseCodeEnum.USER_NOT_FOUND.getResultMsg()));
        return new SecurityMember(member);
    }
}
