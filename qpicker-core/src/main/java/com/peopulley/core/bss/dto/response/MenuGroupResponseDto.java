package com.peopulley.core.bss.dto.response;

import com.peopulley.core.bss.domain.MenuGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuGroupResponseDto {
	@ApiModelProperty(value = "그룹 아이디", example = "0")
	private Long groupId;

	@ApiModelProperty(value = "그룹 이름")
	private String groupName;

	@ApiModelProperty(value = "메뉴 JSON")
	private String jsonMenu;

	@ApiModelProperty(value = "그룹 이름")
	private String memberRole;

	public static MenuGroupResponseDto from(MenuGroup menuGroup) {
		return MenuGroupResponseDto.builder()
			.groupId(menuGroup.getGroupId())
			.groupName(menuGroup.getGroupName())
			.jsonMenu(menuGroup.getJsonMenu())
			.memberRole(menuGroup.getMemberRole())
		.build();
	}


}
