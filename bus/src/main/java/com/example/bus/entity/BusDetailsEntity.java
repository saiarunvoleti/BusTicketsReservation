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

@Entity(name = "BUS_DETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BUS_ID")
	Long busid;
	
	@Column(name = "BUS_NUMBER")
	String busNumber;
	
	@Column(name = "IS_AC_BUS")
	String isACBus;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="TRAVELS_ID")
	TravellerDetailsEntity travelDetails;
	
	@OneToMany(mappedBy="busDetails",cascade=CascadeType.ALL)
	List<SeatDetailsEntity> seatsList;
	
	@OneToMany(mappedBy="busDetails",cascade=CascadeType.ALL)
	List<BusJourneyDetailsEntity> busJourneyList;
	
}
