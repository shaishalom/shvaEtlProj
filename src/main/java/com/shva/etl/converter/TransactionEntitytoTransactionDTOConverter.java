package com.shva.etl.converter;

import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shva.etl.dto.ETLDTO;
import com.shva.etl.model.ETLEntity;

@Component("transactionEntitytoTransactionDTOConverter")
public class TransactionEntitytoTransactionDTOConverter implements Function<ETLEntity, ETLDTO> {

	@Autowired
	protected ModelMapper modelMapper;

	@Override
	public ETLDTO apply(ETLEntity imlEntity) {

		if (imlEntity == null) {
			return null;
		}
		ETLDTO imlDto = (ETLDTO) modelMapper.map(imlEntity, ETLDTO.class);
//		List<ImlDTO> itemList = quoteEntity.getItemList().stream().map(item -> {
//			ItemDTO itemDTO = modelMapper.map(item, ItemDTO.class);
//			return itemDTO;
//		}).collect(Collectors.toList());
		//quoteDto.setItemList(itemList);
		return imlDto;

	}



}

