package com.peopulley.rest.user.service;

import com.peopulley.core.base.dto.MemberRequestDto;
import com.peopulley.core.base.dto.MemberResponseDto;
import com.peopulley.core.base.repository.MemberRepository;
import com.peopulley.core.common.enums.ResponseCodeEnum;
import com.peopulley.core.base.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;

	@Transactional
	public ResponseCodeEnum insertMember(MemberRequestDto reqDto){
		ResponseCodeEnum responseCode = ResponseCodeEnum.FAIL;
		long memberId = memberRepository.save(Member.insertMember(reqDto)).getMemberId();
		if(memberId != 0){
			responseCode = ResponseCodeEnum.OK;
		}
		return responseCode;
	}

	@Transactional
	public ResponseCodeEnum updateMember(MemberRequestDto reqDto){
		Member member = memberRepository.findById(reqDto.getMemberId()).orElse(null);
		member.updateMember(reqDto);
		return ResponseCodeEnum.OK;
	}

	@Transactional(readOnly = true)
	public List<MemberResponseDto> getMemberList(){
		List resultSet = new ArrayList();
		return resultSet;
	}

	@Transactional(readOnly = true)
	public MemberResponseDto getMember(Long memberId){
		Member member = memberRepository.findById(memberId).orElse(null);
		return MemberResponseDto.from(member);
	}

}
