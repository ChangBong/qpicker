package com.peopulley.core.bss.dto.request;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuRequestDto {

	@ApiModelProperty(value = "메뉴 아이디", example = "0")
	private Long menuId;

	@ApiModelProperty(value = "액션 타입")
	private String actionType;

	@ApiModelProperty(value = "메뉴 키")
	private String menuKeys;

	@ApiModelProperty(value = "메뉴 이름")
	private String menuName;

	@ApiModelProperty(value = "메뉴 타입 collapse : 하위메뉴 존재, item : url call")
	private String menuType;

	@ApiModelProperty(value = "메뉴 url")
	private String menuUrl;

	@ApiModelProperty(value = "순서", example = "0")
	private Integer sort;

	@ApiModelProperty(value = "부모 메뉴 아이디", example = "0")
	private Long childMenuId;

	@ApiModelProperty(value = "읽기 플래그", example = "Y")
	private boolean readYn;

	@ApiModelProperty(value = "등록 플래그", example = "Y")
	private boolean insertYn;

	@ApiModelProperty(value = "수정 플래그", example = "Y")
	private boolean updateYn;

	@ApiModelProperty(value = "삭제 플래그", example = "Y")
	private boolean deleteYn;

	@ApiModelProperty(value = "승인 플래그", example = "Y")
	private boolean approveYn;




}
