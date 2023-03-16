package com.peopulley.rest.bss.service;

import com.peopulley.core.bss.dto.request.MenuGroupRequestDto;
import com.peopulley.core.bss.dto.response.MenuGroupResponseDto;
import com.peopulley.core.common.enums.ResponseCodeEnum;

import java.util.List;

public interface MenuGroupService {

	ResponseCodeEnum insertMenuGroup(MenuGroupRequestDto reqDto);

	ResponseCodeEnum updateMenuGroup(MenuGroupRequestDto reqDto);

	List<MenuGroupResponseDto> getMenuGroupList();

	MenuGroupResponseDto getMenuGroup(Long groupId);

}

