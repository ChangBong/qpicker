package com.peopulley.core.bss.repository;

import com.peopulley.core.bss.domain.MenuGroup;
import com.peopulley.core.bss.repository.custom.CustomMenuGroupRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;

public class MenuGroupRepositoryImpl extends QuerydslRepositorySupport implements CustomMenuGroupRepository {

	@Autowired
	private JPAQueryFactory jpaQueryFactory;


	public MenuGroupRepositoryImpl(JPAQueryFactory jpaQueryFactory){
		super(MenuGroup.class);
		this.jpaQueryFactory = jpaQueryFactory;
	}
}
