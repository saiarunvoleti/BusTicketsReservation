package com.example.bus.repository;





import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import com.example.bus.entity.BusDetailsEntity;
import com.example.bus.entity.BusJourneyDetailsEntity;

@Repository
public interface busJourneyDetailsRepo extends JpaRepository<BusJourneyDetailsEntity,Long>,
JpaSpecificationExecutor<BusJourneyDetailsEntity>, QueryByExampleExecutor<BusJourneyDetailsEntity>{

	
	boolean existsByBusNumberAndDateOfDeparture(String BusNumber,Date dateOfDeparture);
	
	BusJourneyDetailsEntity findByBusJourneyId(Long busJourneyId);
	
}
