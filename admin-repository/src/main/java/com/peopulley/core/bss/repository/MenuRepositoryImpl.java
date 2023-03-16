package com.peopulley.core.bss.repository;

import com.peopulley.core.base.domain.Menu;
import com.peopulley.core.bss.dto.response.MenuDto;
import com.peopulley.core.bss.dto.response.MenuResponseDto;
import com.peopulley.core.bss.repository.custom.CustomMenuRepository;
import com.peopulley.core.common.enums.MenuEnum;
import com.peopulley.core.common.enums.StatusEnum;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.peopulley.core.base.domain.QMenu.menu;

public class MenuRepositoryImpl extends QuerydslRepositorySupport implements CustomMenuRepository {

	@Autowired
	private JPAQueryFactory jpaQueryFactory;

	public MenuRepositoryImpl(JPAQueryFactory queryFactory){
		super(Menu.class);
		this.jpaQueryFactory = queryFactory;
	}

	@Override
	public List<MenuResponseDto> getMenuList() {
		List<MenuResponseDto> parentMenu = buildMenuQuery()
				.where(
						menu.parentMenuId.eq(0L)
						, menu.useYn.eq(StatusEnum.FLAG_Y.getStatusMsg())
				)
				.orderBy(menu.sort.asc())
				.fetch();

		List<MenuResponseDto> childrenMenus = buildMenuQuery()
				.where(
						menu.parentMenuId.ne(0L)
						, menu.useYn.eq(StatusEnum.FLAG_Y.getStatusMsg())
				)
				 .fetch();

		parentMenu.stream().forEach(
				parent -> {
					parent.setChildList(
							childrenMenus.stream()
									.sorted(Comparator.comparing(MenuResponseDto::getSort))
									.filter(child -> parent.getMenuId().equals(child.getParentMenuId()))
									.map(e -> {
										return MenuDto.builder()
												.menuId(e.getMenuId())
												.menuKeys(e.getMenuKeys())
												.menuName(e.getMenuName())
												.menuType(e.getMenuType())
												.menuUrl(e
														.getMenuUrl())
												.sort(e.getSort())
												.parentMenuId(e.getParentMenuId())
												.approveYn(e.getApproveYn())
												.readYn(e.getReadYn())
												.insertYn(e.getInsertYn())
												.updateYn(e.getUpdateYn())
												.deleteYn(e.getDeleteYn())
												.useYn(e.getUseYn())
												.build();
									}).collect(Collectors.toList())
					);
				}
		);
		return parentMenu;
	}

	public JPAQuery<MenuResponseDto> buildMenuQuery(){
		return jpaQueryFactory.select(
				Projections.fields(
						MenuResponseDto.class
						, menu.menuId.as("menuId")
						, menu.menuKeys.as("menuKeys")
						, menu.menuName.as("menuName")
						, menu.menuType.as("menuType")
						, menu.menuUrl.as("menuUrl")
						, menu.parentMenuId.as("parentMenuId")
						, menu.sort.as("sort")
						, new CaseBuilder()
							.when(menu.menuType.eq(MenuEnum.MenuType.ITEM))
							.then(Expressions.asString(StatusEnum.FLAG_Y.getStatusMsg()))
							.otherwise(Expressions.asString(StatusEnum.FLAG_N.getStatusMsg()))
						.as("readYn")
						, new CaseBuilder()
								.when(menu.menuType.eq(MenuEnum.MenuType.ITEM))
								.then(Expressions.asString(StatusEnum.FLAG_Y.getStatusMsg()))
								.otherwise(Expressions.asString(StatusEnum.FLAG_N.getStatusMsg()))
						.as("insertYn")
						, new CaseBuilder()
								.when(menu.menuType.eq(MenuEnum.MenuType.ITEM))
								.then(Expressions.asString(StatusEnum.FLAG_Y.getStatusMsg()))
								.otherwise(Expressions.asString(StatusEnum.FLAG_N.getStatusMsg()))
						.as("updateYn")
						, new CaseBuilder()
								.when(menu.menuType.eq(MenuEnum.MenuType.ITEM))
								.then(Expressions.asString(StatusEnum.FLAG_Y.getStatusMsg()))
								.otherwise(Expressions.asString(StatusEnum.FLAG_N.getStatusMsg()))
						.as("deleteYn")
						, new CaseBuilder()
								.when(menu.menuType.eq(MenuEnum.MenuType.ITEM))
								.then(Expressions.asString(StatusEnum.FLAG_Y.getStatusMsg()))
								.otherwise(Expressions.asString(StatusEnum.FLAG_N.getStatusMsg()))
						.as("approveYn")
				)
		).from(menu);
	}
}
