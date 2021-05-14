package com.shva.etl.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.shva.etl.dto.CountDTO;
import com.shva.etl.dto.ETLDTO;
import com.shva.etl.dto.InputFileDTO;
import com.shva.etl.dto.OutputLineDTO;
import com.shva.etl.dto.TransactionDTO;
import com.shva.etl.util.StringUtils;

/**
 * service that write the output dto objects to the transaction table (in mySQL)
 * @author Shai
 *
 */
@Component
public class WriteOutputService {

	
	public static String DEFAULT_LOCATION="all entrances";

	@Autowired
	private Environment env;
	
	@Autowired
	TransactionService transactionService;

	@Autowired	
	public Logger logger;


	public void writeOutput(List<InputFileDTO> inputFileList,ETLDTO etlDTo) throws IOException {

		InputFileDTO firstInputFileDTO = inputFileList.stream().findFirst().orElse(null);
		if (CollectionUtils.isEmpty(inputFileList) || firstInputFileDTO==null) {
			logger.error("*** no input file exists.do not update output file");
			return;
		}
		String dateOfFile = firstInputFileDTO.getDateFormatYYMMDD(); //format is Always
		String outputDir = env.getProperty("outputFolder");
		String startHourDisplatyStr = env.getProperty("startHourDisplay");
		String endHourDisplayStr = env.getProperty("endHourDisplay");
		
		int startHourDisplay = StringUtils.stringToInt(startHourDisplatyStr,9);
		int endHourDisplay = StringUtils.stringToInt(endHourDisplayStr,23);
		
		
		//create file if does not esist
		Files.createDirectories(Paths.get(outputDir));
		
		String fileOutputNameStr = outputDir + "/" + dateOfFile+".csv";
		File fileOutput = new File(fileOutputNameStr);
			
		List<OutputLineDTO> allOutputFileList = new ArrayList<OutputLineDTO>();
		inputFileList.stream().forEach(inputFile -> {
				   
					List<OutputLineDTO> fileOutputLineList = inputFile.getInputLineDTOList().stream().map(inputLine -> {
					    CountDTO aggregateCount = inputLine.getAggregatePerHourCountDTO();
						OutputLineDTO outputLineDTO = new OutputLineDTO(inputFile.getStoreId(),
																		inputFile.getCameraDescription(),
																		inputFile.getCameraNum(),
																		inputFile.getDate(),
																		DEFAULT_LOCATION,
																		inputLine.getHourTime(),
																		inputLine.getTimeStr(),
																		aggregateCount.getCountIn(),
																		aggregateCount.getCountOut());
						return outputLineDTO;
																		
					}).collect(Collectors.toList());
					
					allOutputFileList.addAll(fileOutputLineList);
		});

		List<TransactionDTO> allTransactionDtoList = new ArrayList<TransactionDTO>();
		
		inputFileList.stream().forEach(inputFile -> {
				String inputFileDate = 	inputFile.getDate();   
				inputFile.getInputLineDTOList().stream().forEach(inputLine -> {
					List<CountDTO> countDTOList = inputLine.getCountDTOList();
					int startCameraIndex=1;
					for (int i = 0; i < countDTOList.size(); i++) {
						int countIn = countDTOList.get(i).getCountIn();
						String cameraName = inputLine.getCameraNameList().get(startCameraIndex);
						startCameraIndex++;
						
						TransactionDTO transactionDtoIn = new TransactionDTO(null,etlDTo.getBankId(),"desc"+inputFile.getCameraNum() + cameraName+"_IN",inputFile.getCameraNum() + cameraName+"_IN",(double) countIn,new Date());
						TransactionDTO transactionDtoOut = new TransactionDTO(null,etlDTo.getBankId(),"desc"+inputFile.getCameraNum() + cameraName+"_OUT",inputFile.getCameraNum() + cameraName+"_OUT",(double) countIn,new Date());
						
						allTransactionDtoList.add(transactionDtoIn);
						allTransactionDtoList.add(transactionDtoOut);
						
						
					}
				});
			});
		
		

			try {
				writeToTransactionDBTable(allTransactionDtoList,startHourDisplay,endHourDisplay);
			} catch (Exception e) {
				e.printStackTrace();
				 logger.error("fail in sending to DB");
			}
	}

	
	
	private void writeToTransactionDBTable(List<TransactionDTO> transactionDToList,  int startHourDisplay,int endHourDisplay)  {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		Holder<Integer> holderCount = new Holder<>(0);
		transactionDToList.stream().forEach(tranDTO -> {
			
				 try {
					 transactionService.save(tranDTO);
				 }catch(Exception e) {
					 logger.error("fail to update record:"+tranDTO);
					 e.printStackTrace();
				 }
				 
				 holderCount.value++;
		});
		logger.info ("total lines insert to DB:" + holderCount);
		
	}
	



}
