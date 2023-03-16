package com.peopulley.core.bss.repository.custom;

import com.peopulley.core.bss.dto.response.MenuResponseDto;

import java.util.List;

public interface CustomMenuRepository {

    List<MenuResponseDto> getMenuList();
}
