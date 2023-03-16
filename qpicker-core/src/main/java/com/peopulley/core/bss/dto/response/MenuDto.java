package com.peopulley.core.bss.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.peopulley.core.common.enums.MenuEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuDto {

	@JsonIgnore
	@ApiModelProperty(value = "메뉴 아이디", example = "0")
	private Long menuId;

	@ApiModelProperty(value = "메뉴 키")
	private String menuKeys;

	@ApiModelProperty(value = "메뉴 이름")
	private String menuName;

	@ApiModelProperty(value = "메뉴 타입 collapse : 하위메뉴 존재, item : url call")
	private MenuEnum.MenuType menuType;

	@ApiModelProperty(value = "메뉴 url")
	private String menuUrl;

	@ApiModelProperty(value = "순서", example = "0")
	private Integer sort;

	@JsonIgnore
	@ApiModelProperty(value = "부모 메뉴 아이디", example = "0")
	private Long parentMenuId;

	@ApiModelProperty(value = "읽기 플래그", example = "Y")
	private String readYn;

	@ApiModelProperty(value = "등록 플래그", example = "Y")
	private String insertYn;

	@ApiModelProperty(value = "수정 플래그", example = "Y")
	private String updateYn;

	@ApiModelProperty(value = "삭제 플래그", example = "Y")
	private String deleteYn;

	@ApiModelProperty(value = "승인 플래그", example = "Y")
	private String approveYn;

	@JsonIgnore
	@ApiModelProperty(value = "사용여부", example = "Y")
	private String useYn;




}
