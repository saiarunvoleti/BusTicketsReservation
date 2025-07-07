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
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "SEAT_DETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SEAT_ID")
	Long busid;
	
	@Column(name = "SEAT_NUMBER")
	String seatNumber;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="BUS_ID")
	BusDetailsEntity busDetails;
	
	@OneToMany(mappedBy = "seatDetails",cascade=CascadeType.ALL)
	List<SeatBookingDetailsEntity> seatBookingDetails;
	
}
