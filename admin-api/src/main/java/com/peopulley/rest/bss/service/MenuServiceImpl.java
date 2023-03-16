package com.peopulley.rest.bss.service;

import com.peopulley.core.bss.dto.response.MenuResponseDto;
import com.peopulley.core.bss.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MenuServiceImpl implements MenuService {

	private final MenuRepository menuRepository;

	@Transactional(readOnly = true)
	public List<MenuResponseDto> getMenuList(){
		return menuRepository.getMenuList();
	}

}
