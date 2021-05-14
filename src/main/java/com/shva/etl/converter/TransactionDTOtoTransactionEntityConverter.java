package com.shva.etl.converter;

import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shva.etl.dto.ETLDTO;
import com.shva.etl.dto.TransactionDTO;
import com.shva.etl.model.ETLEntity;
import com.shva.etl.model.TransactionEntity;

@Component("transactionDTOtoTransactionEntityConverter")
public class TransactionDTOtoTransactionEntityConverter implements Function<TransactionDTO, TransactionEntity> {

	@Autowired
	protected ModelMapper modelMapper;

	@Override
	public TransactionEntity apply(TransactionDTO transactionDTO) {

		if (transactionDTO == null) {
			return null;
		}
		
		TransactionEntity entity = (TransactionEntity) modelMapper.map(transactionDTO, TransactionEntity.class);
		return entity;
	}



}

