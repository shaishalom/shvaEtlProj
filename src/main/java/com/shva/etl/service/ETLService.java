package com.shva.etl.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.shva.etl.converter.ETLDTOtoETLEntityConverter;
import com.shva.etl.converter.ETLEntitytoETLDTOConverter;
import com.shva.etl.dto.ETLDTO;
import com.shva.etl.dto.StatusDTO;
import com.shva.etl.exception.OutputStatusEnum;
import com.shva.etl.exception.ProjBusinessException;
import com.shva.etl.model.ETLEntity;
import com.shva.etl.repository.ETLRepository;

/**
 * call to service that load the etl timelines
 * @author shai
 *
 */
@Service
public class ETLService {

	@Autowired
	ETLRepository etlRepository;



	@Autowired
	protected ModelMapper modelMapper;
	
	@Autowired
	@Qualifier("etlEntitytoETLDTOConverter")
	ETLEntitytoETLDTOConverter etlEntitytoetlDTOConverter;
//	
//	
	@Autowired
	@Qualifier("etlDTOtoETLEntityConverter")
	ETLDTOtoETLEntityConverter etlDTOtoETLEntityConverter;
//	
	
	
	@Autowired
	Logger logger;



	public List<ETLDTO> getAllETL() {
		List<ETLEntity> entityList = etlRepository.findAll();
		List<ETLDTO> etlDTOList= entityList.stream().map( ent-> {
			ETLDTO etlDTO = etlEntitytoetlDTOConverter.apply(ent);
			return etlDTO;
		}).collect(Collectors.toList());
		return etlDTOList;
	}


	public ETLDTO deleteETLByDto(ETLDTO dto) throws ProjBusinessException {
		List<ETLEntity> imlEntityList = etlRepository.findByName(dto.getName());
		if(CollectionUtils.isEmpty(imlEntityList)) {
			logger.debug("not found for:{}",dto);
			return null;
		}
		ETLEntity imlEntity = imlEntityList.get(0);

		if (imlEntity!=null) {
			Long deletedId =imlEntity.getId();
			etlRepository.deleteById(imlEntity.getId());

			dto.setId(deletedId);
		} else {
			StatusDTO statusDTO = new StatusDTO(OutputStatusEnum.NO_RECORDS_FOUND,"No iml record exist for given dto:" + dto,"");
			logger.error(statusDTO.toString());
			throw new ProjBusinessException(statusDTO);
		}
		return dto;
	}
}