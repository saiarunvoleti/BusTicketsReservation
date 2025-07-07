package com.example.bus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import com.example.bus.entity.TravellerDetailsEntity;
import com.example.bus.entity.UserDetailsEntity;

@Repository
public interface travellerDetailsRepo extends JpaRepository<TravellerDetailsEntity,Long>,
JpaSpecificationExecutor<TravellerDetailsEntity>, QueryByExampleExecutor<TravellerDetailsEntity>{

	
	
	
}
