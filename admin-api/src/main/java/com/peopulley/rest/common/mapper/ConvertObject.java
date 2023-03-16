package com.peopulley.rest.common.mapper;

import org.modelmapper.ModelMapper;

public class ConvertObject {
	
	@SuppressWarnings("unchecked")
	public <E> Object convObj(Object getObj, Class<?> parseObject) {
		ModelMapper modelMapper = new ModelMapper();
		E res = null;
		res = (E) modelMapper.map(getObj, parseObject);
		return res;
	}
}
