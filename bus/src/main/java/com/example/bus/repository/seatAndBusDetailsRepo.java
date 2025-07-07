package com.example.bus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import com.example.bus.entity.BusDetailsEntity;
import com.example.bus.entity.SeatDetailsEntity;

@Repository
public interface seatAndBusDetailsRepo extends JpaRepository<SeatDetailsEntity,Long>,
JpaSpecificationExecutor<SeatDetailsEntity>, QueryByExampleExecutor<SeatDetailsEntity>{

	
	
}
