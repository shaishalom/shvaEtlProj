package com.shva.etl.dto;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class InputLineDTO {

	
	private List<String> cameraNameList = new ArrayList<String>();
	private String timeStr;
	private List<CountDTO> countDTOList = new ArrayList<CountDTO>();
	private CountDTO aggregateCountDTO = new CountDTO(); 
	private CountDTO aggregatePerHourCountDTO = new CountDTO(); 
	private LocalTime hourTime = null; 
	
	
	
	public InputLineDTO(String timeStr, List<String> cameraNameList, List<CountDTO> countDTO) {
		super();
		this.timeStr = timeStr;
		this.cameraNameList = cameraNameList;
		this.countDTOList = countDTO;
	}
	
	public String getTimeStr() {
		return timeStr;
	}
	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}
	public List<CountDTO> getCountDTOList() {
		return countDTOList;
	}
	public void setCountDTOList(List<CountDTO> countDTOList) {
		this.countDTOList = countDTOList;
	}
	public List<String> getCameraNameList() {
		return cameraNameList;
	}
	public void setCameraNameList(List<String> cameraNameList) {
		this.cameraNameList = cameraNameList;
	}
	public void setCountDTO(List<CountDTO> countDTO) {
		this.countDTOList = countDTO;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InputLineDTO [cameraNameList=");
		builder.append(cameraNameList);
		builder.append(", time=");
		builder.append(timeStr);
		builder.append(", countDTO=");
		builder.append(countDTOList);
		builder.append(", aggregateCountDTO=");
		builder.append(aggregateCountDTO);
		builder.append(", aggregatePerHourCountDTO=");
		builder.append(aggregatePerHourCountDTO);
		builder.append("]");
		return builder.toString();
	}

	public CountDTO getAggregateCountDTO() {
		return aggregateCountDTO;
	}

	public void setAggregateCountDTO(CountDTO aggregateCountDTO) {
		this.aggregateCountDTO = aggregateCountDTO;
	}

	public LocalTime getHourTime() {
		return hourTime;
	}

	public void setHourTime(LocalTime hourTime) {
		this.hourTime = hourTime;
	}

	public CountDTO getAggregatePerHourCountDTO() {
		return aggregatePerHourCountDTO;
	}

	public void setAggregatePerHourCountDTO(CountDTO aggregatePerHourCountDTO) {
		this.aggregatePerHourCountDTO = aggregatePerHourCountDTO;
	}


}
