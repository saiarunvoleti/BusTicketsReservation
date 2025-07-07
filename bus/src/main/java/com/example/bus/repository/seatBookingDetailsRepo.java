package com.example.bus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import com.example.bus.entity.BusJourneyDetailsEntity;
import com.example.bus.entity.SeatBookingDetailsEntity;

@Repository
public interface seatBookingDetailsRepo extends JpaRepository<SeatBookingDetailsEntity,Long>,
JpaSpecificationExecutor<SeatBookingDetailsEntity>, QueryByExampleExecutor<SeatBookingDetailsEntity>{

	
	
}
