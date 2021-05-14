package com.shva.etl.task;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.shva.etl.dto.ETLDTO;
import com.shva.etl.dto.InputFileDTO;
import com.shva.etl.service.AggregateService;
import com.shva.etl.service.ETLService;
import com.shva.etl.service.ReadFileService;
import com.shva.etl.service.WriteOutputService;

@Component
public class ScheduledTasks {
	
	@Autowired
    private Logger logger;
	
	
	@Autowired
	private Environment env;

	@Autowired
	AggregateService aggregateService;

	@Autowired
	ETLService etlService;
	
	@Autowired
	ReadFileService readFileService;

	@Autowired
	WriteOutputService writeOutputService;


    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public void scheduleTaskWithFixedRate() {}

    public void scheduleTaskWithFixedDelay() {}

    public void scheduleTaskWithInitialDelay() {}

    
	public void runETLTransaction(ETLDTO etlDTO) {

		String lineType = env.getProperty("lineType");

		List<InputFileDTO> inputFileList = readFileService.readInputFile(etlDTO.getFolder());
		
		aggregateService.aggregatelineInInputFiles(inputFileList, lineType);
		try {
			writeOutputService.writeOutput(inputFileList,etlDTO);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("failed in write to file", e);
			e.printStackTrace();
		}

	}
    
    
	public void runETLTransaction() {

		String sourceInputPath = env.getProperty("inputFolder");
		String lineType = env.getProperty("lineType");
		ETLDTO firstEtlDto = etlService.getAllETL().get(0);
		List<InputFileDTO> inputFileList = readFileService.readInputFile(sourceInputPath);
		
		aggregateService.aggregatelineInInputFiles(inputFileList, lineType);
		try {
			writeOutputService.writeOutput(inputFileList,firstEtlDto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("failed in write to file", e);
			e.printStackTrace();
		}

	}

	public void invokeSchedule(List<ETLDTO> etoList) {
		
	}


}
