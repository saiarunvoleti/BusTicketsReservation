package com.example.bus.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "TRAVELS_DETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravellerDetailsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="TRAVELS_ID")
	Long travellerId;
	@Column(name="TRAVELLER_NAME")
	String travellerName;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="USER_ID")
	UserDetailsEntity userDetails;
	
	@OneToMany(mappedBy = "travelDetails",cascade = CascadeType.ALL)
	List<BusDetailsEntity> busDetails;
	
}
