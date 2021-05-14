package com.shva.etl.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.shva.etl.dto.CountDTO;
import com.shva.etl.dto.InputFileDTO;
import com.shva.etl.dto.InputLineDTO;
import com.shva.etl.predicate.LegalFilePredicate;
import com.shva.etl.util.StringUtils;

/**
 * reading from files and store it in Data Objects
 * @author Shai
 *
 */
@Component
public class ReadFileService {

	@Autowired
	private Environment env;

	@Autowired	
	public Logger logger;
	
	
	
	public List<InputFileDTO> readInputFile(String sourceFolder) {

		FileWriter fstream = null;
		AtomicInteger count = new AtomicInteger(0);
		List<InputFileDTO> inputFileDTOList = new ArrayList<>();


		String runDateTime = env.getProperty("runDateTime");
		final LegalFilePredicate legalFilePredicate = new LegalFilePredicate(runDateTime);

		try (Stream<Path> walk = Files.walk(Paths.get(sourceFolder))) {

			
			walk.filter(legalFilePredicate).collect(Collectors.toList()).
			forEach(curFile -> 
				{
					readInputFile(curFile ,count,inputFileDTOList ,legalFilePredicate);
				}
			);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("failed in read",e);
		}

		logger.info("ReadFileService-> total input files:" + count.get());
		logger.info("ReadFileService->total element in List:" + inputFileDTOList.size());
		return inputFileDTOList;
	}

	public void readInputFile(Path path,  AtomicInteger count, List<InputFileDTO> inputFileDTOList, 
			LegalFilePredicate legalFilePredicate) {

		FileInputStream fis;
		try {


			logger.debug("readFile: " + path.toString());

			fis = new FileInputStream(path.toFile());
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));

			String filename = path.toString();
			String rootPath = path.getParent().getParent().getParent().toString();

			String readline;
			List<String> cameraNameList = new ArrayList<>();
			List<InputLineDTO> inputLineList = new ArrayList<>();
			boolean isStartReadingTime = false;
			InputFileDTO inputFileDTO = new InputFileDTO();
			while ((readline = in.readLine()) != null) {
				if (readline.contains("- From")) {
					String cameraDescription = StringUtils.getStringBetween(readline, "From", "\\(").trim();
					String storeId = StringUtils.getStringBetween(readline, "\\([cC]ode", "\\)").trim();
					inputFileDTO.setCameraDescription(cameraDescription);
					inputFileDTO.setStoreId(storeId);
					String cameraNumber = env.getProperty(storeId);
					if (cameraNumber==null) {
						cameraNumber = storeId;
						logger.debug("cameraNumber not set for camera:" + cameraNumber +" take " + storeId + " as cameraNumber");
					}
					inputFileDTO.setCameraNum(cameraNumber); //set the storeId as counter //TODO

					String dateStr = StringUtils.getStringBetween(readline, "For date", "$").trim();
					inputFileDTO.setDate(dateStr);
					
					
					inputFileDTO.setFileName(filename);
					inputFileDTO.setRootPathStr(rootPath);
					
					// handle first Line
				} else if (readline.startsWith("Time")) {
					String[] columnElements = readline.split("\t", -1);
					cameraNameList.add("TIME");
					for (int i = 1; i < columnElements.length; i = i + 2) {
						String cameraName = columnElements[i].replace("_OUT", "");
						cameraNameList.add(cameraName);
					}

				} else if (readline.contains("Counts	Counts")) {
					isStartReadingTime = true;

				} else if (readline.startsWith("00:00:00")) {
					handleCountLine(readline, cameraNameList, inputLineList);
					isStartReadingTime = false;
					inputFileDTO.setInputLineDTOList(inputLineList);
					inputFileDTOList.add(inputFileDTO);

				} else if (isStartReadingTime) {
					handleCountLine(readline, cameraNameList, inputLineList);
				}

			}
			in.close();
			count.addAndGet(1);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("failed in readInputFile",e);
		}

	}

	public static void handleCountLine(String readline, List<String> cameraNameList, List<InputLineDTO> inputLineList) {
		CountDTO countDTO;
		List<CountDTO> countDTOList;
		InputLineDTO inputLineDTO;
		// take the times
		String[] countElements = readline.split("\t", -1);
		String time = countElements[0];
		countDTOList = new ArrayList<CountDTO>();
		for (int i = 1; i < countElements.length; i = i + 2) {
			int outCount = Integer.parseInt(countElements[i].trim());
			int inCount = Integer.parseInt(countElements[i + 1].trim());
			countDTO = new CountDTO(inCount, outCount);
			countDTOList.add(countDTO);
		}
		inputLineDTO = new InputLineDTO(time, cameraNameList, countDTOList);
		inputLineList.add(inputLineDTO);
	}
}
