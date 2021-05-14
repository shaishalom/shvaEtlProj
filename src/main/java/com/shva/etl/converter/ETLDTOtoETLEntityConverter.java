package com.shva.etl.converter;

import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shva.etl.dto.ETLDTO;
import com.shva.etl.model.ETLEntity;


@Component("etlDTOtoETLEntityConverter")
public class ETLDTOtoETLEntityConverter implements Function<ETLDTO, ETLEntity> {

	@Autowired
	protected ModelMapper modelMapper;

	@Override
	public ETLEntity apply(ETLDTO imlDTO) {

		if (imlDTO == null) {
			return null;
		}
		
		ETLEntity entity = (ETLEntity) modelMapper.map(imlDTO, ETLEntity.class);
		
		return entity;
	}



}

