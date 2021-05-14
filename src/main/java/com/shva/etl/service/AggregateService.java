package com.shva.etl.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.shva.etl.dto.CountDTO;
import com.shva.etl.dto.InputFileDTO;
import com.shva.etl.dto.InputLineDTO;

/**
 * service that aggregate data from input data
 * @author shai
 *
 */
@Component
public class AggregateService {

	@Autowired
	private Environment env;

	@Autowired
	public Logger logger;

	public void aggregatelineInInputFiles(List<InputFileDTO> InputFileList, String lineType) {

		logger.debug("AggregateService started");

		InputFileList.stream().forEach(inputFile -> {
			inputFile.getInputLineDTOList().stream().forEach(inputLine -> {

				CountDTO aggregateCountDTO = inputLine.getCountDTOList().stream().reduce(new CountDTO(),
						(aggregateCount, countDTO) -> {
							aggregateCount.addCountIn(countDTO.getCountIn());
							aggregateCount.addCountOut(countDTO.getCountOut());
							return aggregateCount;
						});
				inputLine.setAggregateCountDTO(aggregateCountDTO);
				inputLine.setAggregatePerHourCountDTO(aggregateCountDTO);
				LocalTime localTimeCurrent = LocalTime.parse(inputLine.getTimeStr(),
						DateTimeFormatter.ofPattern("HH:mm:ss"));
				inputLine.setHourTime(localTimeCurrent);
			});
		});

		if (StringUtils.equals(lineType, "minute")) {

			InputFileList.stream().forEach(inputFile -> {
				inputFile.getInputLineDTOList().stream().forEach(inputLine -> {

					String currentTime = inputLine.getTimeStr();
					LocalTime localTimeCurrent = LocalTime.parse(currentTime, DateTimeFormatter.ofPattern("HH:mm:ss"));
					int minute = localTimeCurrent.get(ChronoField.MINUTE_OF_HOUR);
					LocalTime localTimeCurrentNextHourRounded = localTimeCurrent;
					if (minute > 0) { // in case not round
						localTimeCurrentNextHourRounded = localTimeCurrent.plusHours(1L);
						localTimeCurrentNextHourRounded = localTimeCurrentNextHourRounded.truncatedTo(ChronoUnit.HOURS);
					}
					inputLine.setHourTime(localTimeCurrentNextHourRounded);

				});
			});

			InputFileList.stream().forEach(inputFile -> {
				Map<LocalTime, List<InputLineDTO>> HourToInputLineMap = inputFile.getInputLineDTOList().stream()
						.collect(Collectors.groupingBy(InputLineDTO::getHourTime));
				Map<LocalTime, InputLineDTO> HourToInputLineDTOMap = HourToInputLineMap.entrySet().stream()
						.collect(Collectors.toMap(Map.Entry::getKey, ent -> {

							InputLineDTO firstInputLine = ent.getValue().stream().findFirst().get();
							InputLineDTO inputLineDTONew = new InputLineDTO(firstInputLine.getTimeStr(),
									firstInputLine.getCameraNameList(), firstInputLine.getCountDTOList());

							CountDTO countHourDTO = new CountDTO();

							ent.getValue().stream().forEach(inputLine -> {
								countHourDTO.addCountIn(inputLine.getAggregateCountDTO().getCountIn());
								countHourDTO.addCountOut(inputLine.getAggregateCountDTO().getCountOut());
							});
							inputLineDTONew.setAggregatePerHourCountDTO(countHourDTO);
							inputLineDTONew.setHourTime(ent.getKey());
							return inputLineDTONew;

						}));

				HourToInputLineDTOMap.entrySet().stream().forEach(entry -> {
					logger.debug(entry.getKey() + "-> IN:" + entry.getValue().getAggregatePerHourCountDTO().getCountIn()
							+ ",OUT:" + entry.getValue().getAggregatePerHourCountDTO().getCountOut());
				});

				List<InputLineDTO> newInputLineList = HourToInputLineDTOMap.values().stream()
						.collect(Collectors.toList());

				Collections.sort(newInputLineList, Comparator.comparing(InputLineDTO::getHourTime));

				inputFile.setInputLineDTOList(newInputLineList);

			});

		}

	}
}
