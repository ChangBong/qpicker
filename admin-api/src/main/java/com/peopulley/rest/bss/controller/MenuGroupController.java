package com.peopulley.rest.bss.controller;

import com.peopulley.core.bss.dto.request.MenuGroupRequestDto;
import com.peopulley.core.common.enums.ResponseCodeEnum;
import com.peopulley.rest.bss.service.MenuGroupService;
import com.peopulley.rest.common.handler.ResponseHandler;
import com.peopulley.rest.io.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Api(value = "권한 그룹 관리 API", tags = "권한 그룹 관리 API")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = {"/v1/api/", "/api/"})
public class MenuGroupController {

	private final MenuGroupService menuGroupService;
	private final ResponseHandler responseHandler;

	@ApiOperation(value = "등록", notes = "등록한다.")
	@PostMapping(value = {"/insertMenuGroup"})
	public ResponseEntity<CommonResponse<Void>> insertMenuGroup(@RequestBody MenuGroupRequestDto reqDto){
		ResponseCodeEnum resultCode = menuGroupService.insertMenuGroup(reqDto);
		if(ResponseCodeEnum.OK != resultCode) {
			return responseHandler.fail(resultCode.getResultCode(), resultCode.getResultMsg());
		}else{
			return responseHandler.ok();
		}
	}

	@ApiOperation(value = "수정", notes = "수정한다.")
	@PostMapping(value = {"/updateMenuGroup"})
	public ResponseEntity<CommonResponse<Void>> updateMenuGroup(@RequestBody MenuGroupRequestDto reqDto){
		ResponseCodeEnum resultCode = menuGroupService.updateMenuGroup(reqDto);
		if(ResponseCodeEnum.OK != resultCode) {
			return responseHandler.fail(resultCode.getResultCode(), resultCode.getResultMsg());
		}else{
			return responseHandler.ok();
		}
	}

}
