package com.shva.etl.dto;

import java.util.ArrayList;
import java.util.List;

public class InputFileDTO {
	
	private String storeId;
	private String cameraNum;
	private String cameraDescription;
	private String date;
	private String fileName;
	private String dateFormatYYMMDD;
	private String rootPathStr;
	
	
	private List<InputLineDTO> InputLineDTOList = new ArrayList<>();

	public List<InputLineDTO> getInputLineDTOList() {
		return InputLineDTOList;
	}

	public void setInputLineDTOList(List<InputLineDTO> inputLineDTOList) {
		InputLineDTOList = inputLineDTOList;
	}

	public String getCameraNum() {
		return cameraNum;
	}

	public void setCameraNum(String cameraNum) {
		this.cameraNum = cameraNum;
	}

	public String getCameraDescription() {
		return cameraDescription;
	}

	public void setCameraDescription(String cameraDescription) {
		this.cameraDescription = cameraDescription;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date; //format is dd/mm/yyyy
		this.dateFormatYYMMDD=date.replaceAll("(\\d+)/(\\d+)/(\\d+)", "$3-$2-$1");
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InputFileDTO [storeId=");
		builder.append(storeId);
		builder.append(", cameraNum=");
		builder.append(cameraNum);
		builder.append(", cameraDescription=");
		builder.append(cameraDescription);
		builder.append(", date=");
		builder.append(date);
		builder.append(", fileName=");
		builder.append(fileName);
		builder.append(", InputLineDTOList=");
		builder.append(InputLineDTOList);
		builder.append("]");
		return builder.toString();
	}

	public String getDateFormatYYMMDD() {
		return dateFormatYYMMDD;
	}

	public String getRootPathStr() {
		return rootPathStr;
	}

	public void setRootPathStr(String rootPathStr) {
		this.rootPathStr = rootPathStr;
	}

	
	
	
}
