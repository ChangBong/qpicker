package com.peopulley.rest.user.controller;

import com.peopulley.core.common.enums.ResponseCodeEnum;
import com.peopulley.core.base.dto.MemberRequestDto;
import com.peopulley.core.base.dto.MemberResponseDto;
import com.peopulley.rest.common.handler.ResponseHandler;
import com.peopulley.rest.io.response.CommonResponse;
import com.peopulley.rest.user.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Api(value = "사용자 API", tags = "사용자 API")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = {"/v1/api/", "/api/"})
public class MemberController {

	private final MemberService memberService;
	private final ResponseHandler responseHandler;


	@ApiOperation(value = "사용자 저장.", notes = "사용자를 저장한다.", nickname = "user")
	@PostMapping(value = {"/insertMember"})
	public ResponseEntity<CommonResponse<Void>> insertMember(@RequestBody MemberRequestDto reqDto){
		ResponseCodeEnum resultCode = memberService.insertMember(reqDto);
		if(ResponseCodeEnum.OK != resultCode) {
			return responseHandler.fail(resultCode.getResultCode(), resultCode.getResultMsg());
		}else{
			return responseHandler.ok();
		}
	}

	@ApiOperation(value = "사용자 수정", notes = "사용자를 수정한다.")
	@PostMapping(value = {"/updateMember"})
	public ResponseEntity<CommonResponse<Void>> updateMember(@RequestBody MemberRequestDto reqDto){
		ResponseCodeEnum resultCode = memberService.updateMember(reqDto);
		if(ResponseCodeEnum.OK != resultCode) {
			return responseHandler.fail(resultCode.getResultCode(), resultCode.getResultMsg());
		}else{
			return responseHandler.ok();
		}
	}

	@ApiOperation(value = "목록 조회", notes = "목록을 조회한다.")
	@GetMapping(value = {"/getMemberList"})
	public ResponseEntity<CommonResponse<MemberResponseDto>> getMemberList(){
		return responseHandler.getResponseMessageAsRetrieveResult(memberService.getMemberList(), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
	}

	@ApiOperation(value = "상세 조회", notes = "상세 조회를 한다.")
	@GetMapping(value = {"/getMember/{memberId}"})
	public ResponseEntity<CommonResponse<MemberResponseDto>> getMember(@PathVariable long memberId){
		return responseHandler.getResponseMessageAsRetrieveResult(memberService.getMember(memberId), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
	}

}
