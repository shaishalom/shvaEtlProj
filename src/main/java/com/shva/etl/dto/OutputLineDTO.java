package com.shva.etl.dto;

import java.time.LocalTime;

public class OutputLineDTO {
	private String storeId;
	private String cameraDescription;
	private String cameraNum;
	private String dateStr;
	private String location;
	private LocalTime hourTime;
	private String hourTimeStr;
	private int countIn;
	private int countOut;
	
	
	
	public OutputLineDTO(String storeId, String cameraDescription, String cameraNum, String dateStr,
			String location, LocalTime hourTime , String hourTimeStr, int countIn, int countOut) {
		super();
		this.storeId = storeId;
		this.cameraDescription = cameraDescription;
		this.cameraNum = cameraNum;
		this.dateStr = dateStr;
		this.location = location;
		this.hourTime = hourTime;
		this.hourTimeStr = hourTimeStr;
		this.countIn = countIn;
		this.countOut = countOut;
	}
	
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getCameraDescription() {
		return cameraDescription;
	}
	public void setCameraDescription(String cameraDescription) {
		this.cameraDescription = cameraDescription;
	}
	public String getCameraNum() {
		return cameraNum;
	}
	public void setCameraNum(String cameraNum) {
		this.cameraNum = cameraNum;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String entranceType) {
		this.location = entranceType;
	}
	public String getHourTimeStr() {
		return hourTimeStr;
	}
	public void setHourTimeStr(String hourTimeStr) {
		this.hourTimeStr = hourTimeStr;
	}
	public int getCountIn() {
		return countIn;
	}
	public void setCountIn(int countIn) {
		this.countIn = countIn;
	}
	public int getCountOut() {
		return countOut;
	}
	public void setCountOut(int countOut) {
		this.countOut = countOut;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OutputLineDTO [storeId=");
		builder.append(storeId);
		builder.append(", cameraDescription=");
		builder.append(cameraDescription);
		builder.append(", cameraNum=");
		builder.append(cameraNum);
		builder.append(", dateStr=");
		builder.append(dateStr);
		builder.append(", location=");
		builder.append(location);
		builder.append(", hourTime=");
		builder.append(hourTime);
		builder.append(", hourTimeStr=");
		builder.append(hourTimeStr);
		builder.append(", countIn=");
		builder.append(countIn);
		builder.append(", countOut=");
		builder.append(countOut);
		builder.append("]");
		return builder.toString();
	}

	public LocalTime getHourTime() {
		return hourTime;
	}

	public void setHourTime(LocalTime hourTime) {
		this.hourTime = hourTime;
	}
	
	
}
