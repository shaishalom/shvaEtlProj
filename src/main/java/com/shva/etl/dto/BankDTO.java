package com.shva.etl.dto;

public class BankDTO {

	private Long id;

	private String name;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getSchedule_crone() {
		return schedule_crone;
	}

	public void setSchedule_crone(String schedule_crone) {
		this.schedule_crone = schedule_crone;
	}

	private String description;
	private String folder;
	private String schedule_crone;
	
	public BankDTO() {
		
	}
	

}
