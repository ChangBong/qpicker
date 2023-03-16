package com.peopulley.core.base.domain;

import com.peopulley.core.common.enums.MenuEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@org.hibernate.annotations.Table(appliesTo = "menu", comment = "메뉴 목록")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id", columnDefinition = "BIGINT UNSIGNED comment '메뉴 아이디'")
    private Long menuId;

    @Column(name = "menu_name", columnDefinition = "varchar(255) comment '메뉴 이름'")
    private String menuName;

    @Column(name = "menu_keys", columnDefinition = "varchar(255) comment '메뉴 키'")
    private String menuKeys;

    @Column(name = "menu_url", columnDefinition = "varchar(255) comment '메뉴 url'")
    private String menuUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "menu_type", columnDefinition = "varchar(255) comment '메뉴 타입 collapse : 하위메뉴 존재, item : url call'")
    private MenuEnum.MenuType menuType;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "parent_menu_id", columnDefinition = "BIGINT UNSIGNED comment '부모 메뉴 아이디'")
//    private Menu menu;
    @Column(name = "parent_menu_id", columnDefinition = "BIGINT UNSIGNED comment '부모 메뉴 아이디'")
    private Long parentMenuId;

    @Column(name = "use_yn", columnDefinition = "char(1) comment '메뉴 사용여부 Y:사용, N: 미사용'")
    private String useYn;

    @Column(name = "sort", columnDefinition = "int comment '메뉴 순서'")
    private int sort;


}
