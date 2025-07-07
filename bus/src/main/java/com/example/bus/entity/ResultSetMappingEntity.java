package com.example.bus.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.ColumnResult;


@SqlResultSetMapping(
	    name = "BusJourneyInfoMapping",
	    classes = @ConstructorResult(
	        targetClass = com.example.bus.dto.BusSearchResultsDTO.class,
	        columns = {
	            @ColumnResult(name = "BusJourneyId", type = Long.class),
	            @ColumnResult(name = "TravellerName", type = String.class),
	            @ColumnResult(name = "FromStation", type = String.class),
	            @ColumnResult(name = "ToStation", type = String.class),
	            @ColumnResult(name = "DepartureDate", type = String.class),
	            @ColumnResult(name = "ArrivalDate", type = String.class),
	            @ColumnResult(name = "IsAcBus",type = String.class)
	            
	        }
	    )
	)

@SqlResultSetMapping(
	    name = "SeatsFetchMapping",
	    classes = @ConstructorResult(
	        targetClass = com.example.bus.dto.FetchSeatsDTO.class,
	        columns = {
	            @ColumnResult(name = "SeatBookingId", type = Long.class),
	            @ColumnResult(name = "SeatId", type = Long.class),
	            @ColumnResult(name = "SeatNumber", type = String.class),
	            @ColumnResult(name = "IsSeatAvailable", type = String.class),
	            @ColumnResult(name = "BusJourneyId", type = Long.class),
	            
	        }
	    )
	)

@SqlResultSetMapping(
	    name = "OrderConfirmationMapping",
	    classes = @ConstructorResult(
	        targetClass = com.example.bus.dto.OrderConfirmationDTO.class,
	        columns = {
	            @ColumnResult(name = "OrderId", type = Long.class),
	            @ColumnResult(name = "PaymentStatus", type = String.class),
	            @ColumnResult(name = "BillAmount", type = Long.class),
	            @ColumnResult(name = "BusNumber", type = String.class),
	            @ColumnResult(name = "FromStation", type = String.class),
	            @ColumnResult(name = "ToStation", type = String.class),
	            @ColumnResult(name = "DateOfDeparture", type = String.class),
	            @ColumnResult(name = "DateOfArrival", type = String.class),
	            @ColumnResult(name = "TravelsName", type = String.class),
	            @ColumnResult(name = "SeatNumber", type = String.class),
	            @ColumnResult(name = "PassengerName", type = String.class),
	            @ColumnResult(name = "PassengerAge", type = Long.class),
	            @ColumnResult(name = "PassengerGender", type = String.class),
	            
	        }
	    )
	)

@Entity(name="NormalTable")
public class ResultSetMappingEntity {
 
	@Id
	Long id;
	
}
