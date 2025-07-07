package com.example.bus.entity;


import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "BUS_JOURNEY_DETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusJourneyDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BUS_JOURNEY_ID")
	Long busJourneyId;
	
	@Column(name = "BUS_NUMBER")
	String busNumber;
	
	@Column(name = "FROM_STATION")
	String fromStation;
	
	@Column(name = "TO_STATION")
	String toStation;
	
	@Column(name = "DATE_OF_DEPARTURE")
	Date dateOfDeparture;
	
	@Column(name="DATE_OF_ARRIVAL")
	Date dateOfArrival;
	
	@Column(name="TICKET_PRICE")
	Long ticketPrice;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="BUS_ID")
	BusDetailsEntity busDetails;
	
	@OneToMany(mappedBy = "busJourneyDetails",cascade=CascadeType.ALL)
	List<SeatBookingDetailsEntity> seatBookingDetails;
	
}
