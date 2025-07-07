package com.example.bus.entity;

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


@Entity(name = "ORDER_DETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID")
	Long orderId;
	
	@Column(name = "PAYMENT_MODE")
	String paymentMode;
	
	@Column(name = "PAYMENT_STATUS")
	String paymentStatus;
	
	@Column(name = "TOTAL_VALUE")
	String totalValue;
	
	@Column(name = "ORDER_NUMBER")
	Long orderNumber;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID")
	UserDetailsEntity userDetails;
	
	@OneToOne(mappedBy="orderJourneyDetails",cascade=CascadeType.ALL)
	SeatBookingDetailsEntity seatBookingDetailsEntity;
	
}
