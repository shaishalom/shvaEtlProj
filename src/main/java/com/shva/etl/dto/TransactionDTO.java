package com.shva.etl.dto;

import java.util.Date;

public class TransactionDTO {

	
	private Long id;

    private Long bankId;
	 	
	private String description;

	private String name;

	private Double amount;
		
	private Date createdDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBankId() {
		return bankId;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	
	public TransactionDTO() {
		
	}

	public TransactionDTO(Long id, Long bankId, String description, String name, Double amount,
			Date createdDate) {
		super();
		this.id = id;
		this.bankId = bankId;
		this.description = description;
		this.name = name;
		this.amount = amount;
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "TransactionDTO [id=" + id + ", bankId=" + bankId + ", description=" + description + ", name="
				+ name + ", amount=" + amount + ", createdDate=" + createdDate + "]";
	}
	

}
