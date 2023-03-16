package com.peopulley.core.base.repository;

import com.peopulley.core.base.domain.Member;
import com.peopulley.core.base.repository.custom.CustomMemberRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;


public class MemberRepositoryImpl extends QuerydslRepositorySupport implements CustomMemberRepository {

	@Autowired
	private JPAQueryFactory jpaQueryFactory;

	public MemberRepositoryImpl(JPAQueryFactory queryFactory){
		super(Member.class);
		this.jpaQueryFactory = queryFactory;
	}


}
