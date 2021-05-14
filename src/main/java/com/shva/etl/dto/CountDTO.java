package com.shva.etl.dto;

public class CountDTO {

	public CountDTO() {
		super();
		this.countIn = 0;
		this.countOut = 0;
	}

	
	public CountDTO(int countIn, int countOut) {
		super();
		this.countIn = countIn;
		this.countOut = countOut;
	}
	private int countIn;
	private int countOut;

	public void addCountIn(int countIn) {
		this.countIn = this.countIn + countIn;
	}

	public void addCountOut(int countOut) {
		this.countOut   = this.countOut + countOut;
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
		builder.append("CountDTO [countIn=");
		builder.append(countIn);
		builder.append(", countOut=");
		builder.append(countOut);
		builder.append("]");
		return builder.toString();
	}
	
}
