package com.peopulley.rest.bss.controller;

import com.peopulley.core.bss.dto.response.MenuResponseDto;
import com.peopulley.core.common.enums.ResponseCodeEnum;
import com.peopulley.rest.bss.service.MenuService;
import com.peopulley.rest.common.handler.ResponseHandler;
import com.peopulley.rest.io.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Api(value = "메뉴 목록 API", tags = "메뉴 목록 API")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = {"/v1/api/", "/api/"})
public class MenuController {

	private final MenuService menuService;
	private final ResponseHandler responseHandler;

	@ApiOperation(value = "", notes = "")
	@GetMapping(value = {"/menu"})
	public ResponseEntity<CommonResponse<MenuResponseDto>> getMenuList(){
		return responseHandler.getResponseMessageAsRetrieveResult(menuService.getMenuList(), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
	}


}
