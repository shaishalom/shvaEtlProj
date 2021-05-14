package com.shva.etl.model;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity (name="etlEntity")
@Table(name="etl")
public class ETLEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id",unique=true, nullable = false)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;
	
	@Column(name = "folder")
	private String folder;
	
	@Column(name = "schedule_crone")
	private String schedule_crone;

	
    @ManyToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
	private BankEntity bankEntity;	
	
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

	public BankEntity getBankEntity() {
		return bankEntity;
	}

	public void setBankEntity(BankEntity bankEntity) {
		this.bankEntity = bankEntity;
	}
	

		
    
}