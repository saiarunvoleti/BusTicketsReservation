package com.example.bus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import com.example.bus.entity.BusDetailsEntity;
import com.example.bus.entity.TravellerDetailsEntity;

@Repository
public interface busDetailsRepo extends JpaRepository<BusDetailsEntity,Long>,
JpaSpecificationExecutor<BusDetailsEntity>, QueryByExampleExecutor<BusDetailsEntity>{

	boolean existsByBusNumber(String BusNumber);
	
	BusDetailsEntity findByBusNumber(String BusNumber);
	
//	List<BusDetailsEntity> findAllByTravelsId(Long id);
	
}
