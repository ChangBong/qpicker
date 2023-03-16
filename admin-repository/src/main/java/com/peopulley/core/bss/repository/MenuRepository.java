package com.peopulley.core.bss.repository;

import com.peopulley.core.base.domain.Menu;
import com.peopulley.core.bss.repository.custom.CustomMenuRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long>, CustomMenuRepository {



}
