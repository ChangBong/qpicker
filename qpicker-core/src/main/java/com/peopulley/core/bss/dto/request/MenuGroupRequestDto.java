package com.peopulley.core.bss.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuGroupRequestDto {

	@ApiModelProperty(value = "그룹 아이디", example = "0")
	private Long groupId;

	@ApiModelProperty(value = "그룹 이름")
	private String groupName;

	@ApiModelProperty(value = "그룹 이름")
	private String memberRole;

	@ApiModelProperty(value = "메뉴들")
	private String jsonMenu;


}
