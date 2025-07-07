package com.example.bus.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "SEAT_BOOKING_DETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatBookingDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SEAT_BOOKING_ID")
	Long seatBookingStatusid;
	
	@Column(name = "IS_SEAT_AVAILABLE")
	String isSeatAvailable = "Y";
	
	@Column(name = "BOOKED_DATE")
	String bookedDate;
	
	@Column(name = "PASSENGER_NAME")
	String passengerName;
	
	@Column(name = "PASSENGER_GENDER")
	String passengerGender;
	
	@Column(name = "PASSENGER_AGE")
	String passengerAge;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="BUS_JOURNEY_ID")
	BusJourneyDetailsEntity busJourneyDetails;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="SEAT_ID")
	SeatDetailsEntity seatDetails;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="ORDER_ID")
	OrderDetailsEntity orderJourneyDetails;
	
}
