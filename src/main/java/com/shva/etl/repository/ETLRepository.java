package com.shva.etl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shva.etl.model.ETLEntity;
 
@Repository
public interface ETLRepository
        extends JpaRepository<ETLEntity, Long> {
 
	public List<ETLEntity> findByName(String name);
	
	
}
