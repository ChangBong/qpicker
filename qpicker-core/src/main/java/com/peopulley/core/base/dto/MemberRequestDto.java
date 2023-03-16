package com.peopulley.core.base.dto;

import com.peopulley.core.common.enums.MemberRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequestDto {

	@ApiModelProperty(value = "회원 아이디", example = "0")
	private Long memberId;

	@ApiModelProperty(value = "생년월일")
	private String birth;

	@ApiModelProperty(value = "이메일")
	private String email;

	@ApiModelProperty(value = "패스워드")
	private String password;

	@ApiModelProperty(value = "회원 이름")
	private String memberName;

	@ApiModelProperty(value = "사용자 권한")
	private MemberRole memberRole;

	@ApiModelProperty(value = "회원 상태 1: 정회원 9: 탈퇴회원", example = "0")
	private Integer memberStatus;

	@ApiModelProperty(value = "프로필 이미지 Path")
	private String profileImg;


}