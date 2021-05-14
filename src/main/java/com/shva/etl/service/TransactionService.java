package com.shva.etl.service;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.shva.etl.converter.TransactionDTOtoTransactionEntityConverter;
import com.shva.etl.converter.TransactionEntitytoTransactionDTOConverter;
import com.shva.etl.dto.TransactionDTO;
import com.shva.etl.model.BankEntity;
import com.shva.etl.model.TransactionEntity;
import com.shva.etl.repository.BankRepository;
import com.shva.etl.repository.TransactionRepository;

/**
 * call to service that store transactions
 * @author shai
 *
 */
@Service
public class TransactionService {

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	BankRepository bankRepository;

	@Autowired
	protected ModelMapper modelMapper;
	
	@Autowired
	@Qualifier("transactionDTOtoTransactionEntityConverter")
	TransactionDTOtoTransactionEntityConverter transactionDTOtoTransactionEntityConverter;

	@Autowired
	@Qualifier("transactionEntitytoTransactionDTOConverter")
	TransactionEntitytoTransactionDTOConverter transactionEntitytoTransactionDTOConverter;
//	
	
	
	@Autowired
	
	Logger logger;



	public List<TransactionEntity> getAll() {
		List<TransactionEntity> entityList = transactionRepository.findAll();
		return entityList;
	}

	public void save(TransactionDTO transactionDTO ) throws Exception {
		TransactionEntity transactionEntity= transactionDTOtoTransactionEntityConverter.apply(transactionDTO);
		BankEntity bankEntity = bankRepository.findById(transactionDTO.getBankId()).orElse(null); 
		transactionEntity.setBankEntity(bankEntity);
		Date now= new Date();
		transactionEntity.setCreatedDate(now);
		transactionRepository.save(transactionEntity);

	}

}	

