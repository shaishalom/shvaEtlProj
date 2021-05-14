package com.shva.etl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shva.etl.model.BankEntity;
 
@Repository
public interface BankRepository
        extends JpaRepository<BankEntity, Long> {
 
	
	
}
