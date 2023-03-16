package com.peopulley.rest.bss.service;

import com.peopulley.core.bss.domain.MenuGroup;
import com.peopulley.core.bss.dto.request.MenuGroupRequestDto;
import com.peopulley.core.bss.dto.response.MenuGroupResponseDto;
import com.peopulley.core.bss.repository.MenuGroupRepository;
import com.peopulley.core.common.enums.ResponseCodeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MenuGroupServiceImpl implements MenuGroupService {

	private final MenuGroupRepository menuGroupRepository;

	@Transactional
	public ResponseCodeEnum insertMenuGroup(MenuGroupRequestDto reqDto){
		ResponseCodeEnum responseCode = ResponseCodeEnum.FAIL;
		long groupId = menuGroupRepository.save(MenuGroup.insertMenuGroup(reqDto)).getGroupId();

		if(groupId != 0){
			responseCode = ResponseCodeEnum.OK;
		}
		return responseCode;
	}

	@Transactional
	public ResponseCodeEnum updateMenuGroup(MenuGroupRequestDto reqDto){
		MenuGroup menuGroup = menuGroupRepository.findById(reqDto.getGroupId()).orElse(null);
		menuGroup.updateMenuGroup(reqDto);
		return ResponseCodeEnum.OK;
	}

	@Transactional(readOnly = true)
	public List<MenuGroupResponseDto> getMenuGroupList(){
		List resultSet = new ArrayList();
		return resultSet;
	}

	@Transactional(readOnly = true)
	public MenuGroupResponseDto getMenuGroup(Long groupId){
		MenuGroup menuGroup = menuGroupRepository.findById(groupId).orElse(null);
		return MenuGroupResponseDto.from(menuGroup);
	}

}
