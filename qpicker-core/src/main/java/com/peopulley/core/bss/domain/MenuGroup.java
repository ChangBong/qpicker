package com.peopulley.core.bss.domain;

import com.peopulley.core.bss.dto.request.MenuGroupRequestDto;
import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@org.hibernate.annotations.Table(appliesTo = "menu_group", comment = "권한 그룹")
public class MenuGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", columnDefinition = "BIGINT UNSIGNED comment '그룹 아이디'")
    private Long groupId;

    @Column(name = "group_name", columnDefinition = "varchar(255) comment '그룹 이름'")
    private String groupName;

//    @Enumerated(EnumType.STRING)
    @Column(name = "member_role", columnDefinition = "varchar(255) comment '그룹 이름'")
    private String memberRole;

    @Column(name = "json_menu", columnDefinition = "text comment '메뉴 JSON'")
    private String jsonMenu;


    public static MenuGroup insertMenuGroup(MenuGroupRequestDto reqDto){
        return MenuGroup.builder()
                .groupName(reqDto.getGroupName())
                .memberRole(reqDto.getMemberRole())
                .jsonMenu(reqDto.getJsonMenu())
                .build();
    }

    public void updateMenuGroup(MenuGroupRequestDto reqDto){
        this.setGroupName(reqDto.getGroupName());
        this.setJsonMenu(reqDto.getJsonMenu());
    }

}
