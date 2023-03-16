package com.peopulley.rest.common.dto;

import com.peopulley.core.base.domain.Member;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SecurityMember extends User {
    private static final long serialVersionUiD = 1L;

    private long memberId;
    private String memberName;
    private String email;
    private Member member;

    public SecurityMember(Member member){
        super(member.getMemberName(),"{noop}"+ member.getPassword(), AuthorityUtils.createAuthorityList(member.getMemberRole().toString()));
        setMemberId(member.getMemberId());
        setMemberName(member.getMemberName());
        setEmail(member.getEmail());
        setMember(member);
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}
