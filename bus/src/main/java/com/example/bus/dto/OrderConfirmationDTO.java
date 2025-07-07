package com.example.bus.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderConfirmationDTO {

	@JsonProperty("OrderId")
	Long orderId;
	
	@JsonProperty("PaymentStatus")
	String paymentStatus;
	
	@JsonProperty("BillAmount")
	Long billAmount;
	
	@JsonProperty("BusNumber")
	String busNumber;
	
	@JsonProperty("FromStation")
	String fromStation;
	
	@JsonProperty("ToStation")
	String toStation;
	
	@JsonProperty("DateOfDeparture")
	String dateOfDeparture;
	
	@JsonProperty("DateOfArrival")
	String dateOfArrival;
	
	@JsonProperty("TravelsName")
	String travelsName;
	
	@JsonProperty("SeatNumber")
	String seatNumber;
	
	@JsonProperty("PassengerName")
	String passengerName;
	
	@JsonProperty("PassengerAge")
	Long passengerAge;
	
	@JsonProperty("PassengerGender")
	String passengerGender;
	
}
