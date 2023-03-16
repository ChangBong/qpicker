package com.peopulley.core.bss.repository;

import com.peopulley.core.bss.domain.MenuGroup;
import com.peopulley.core.bss.repository.custom.CustomMenuGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuGroupRepository extends JpaRepository<MenuGroup, Long>, CustomMenuGroupRepository {
}
