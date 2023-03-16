package com.peopulley.rest.user.service;

import com.peopulley.core.base.dto.MemberRequestDto;
import com.peopulley.core.base.dto.MemberResponseDto;
import com.peopulley.core.common.enums.ResponseCodeEnum;

import java.util.List;


public interface MemberService {

	ResponseCodeEnum insertMember(MemberRequestDto reqDto);

	ResponseCodeEnum updateMember(MemberRequestDto reqDto);

	List<MemberResponseDto> getMemberList();

	MemberResponseDto getMember(Long memberId);

}

