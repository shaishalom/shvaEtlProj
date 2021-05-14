package com.shva.etl.converter;

import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shva.etl.dto.ETLDTO;
import com.shva.etl.model.ETLEntity;

@Component("etlEntitytoETLDTOConverter")
public class ETLEntitytoETLDTOConverter implements Function<ETLEntity, ETLDTO> {

	@Autowired
	protected ModelMapper modelMapper;

	@Override
	public ETLDTO apply(ETLEntity etlEntity) {

		if (etlEntity == null) {
			return null;
		}
		ETLDTO etlDto = (ETLDTO) modelMapper.map(etlEntity, ETLDTO.class);
		etlDto.setBankId(etlEntity.getBankEntity().getId());
//		List<ImlDTO> itemList = quoteEntity.getItemList().stream().map(item -> {
//			ItemDTO itemDTO = modelMapper.map(item, ItemDTO.class);
//			return itemDTO;
//		}).collect(Collectors.toList());
		//quoteDto.setItemList(itemList);
		return etlDto;

	}



}

