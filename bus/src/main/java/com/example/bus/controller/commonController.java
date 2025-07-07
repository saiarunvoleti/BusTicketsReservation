package com.example.bus.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bus.dto.BusSearchResultsDTO;
import com.example.bus.dto.FetchSeatsDTO;
import com.example.bus.dto.SearchBusesDTO;
import com.example.bus.dto.TravellerDetailsDTO;
import com.example.bus.service.commonService;

import jakarta.validation.Valid;

@RestController
public class commonController {

	@Autowired
	commonService commonservice;
	
	@GetMapping("/searchFilters/citiesList")
	public ResponseEntity<List<String>> createTravels(@RequestParam(value="fromCity",required=false) String fromCity)
	{
		System.out.println("In controller");
		List<String> cities = commonservice.getListOfCities(fromCity);
		ResponseEntity<List<String>> res = new ResponseEntity<>(cities,HttpStatus.CREATED);
		return res;
	}
	
	@PostMapping("/searchFilters/searchBuses")
	public ResponseEntity<List<BusSearchResultsDTO>> searchBuses(@RequestBody SearchBusesDTO searchInput)
	{
		System.out.println("In controller");
		List<BusSearchResultsDTO> buses = commonservice.getListOfBuses(searchInput);
		ResponseEntity<List<BusSearchResultsDTO>> res = new ResponseEntity<>(buses,HttpStatus.OK);
		return res;
	}
	
	@PostMapping("/searchFilters/searchBuses/busJourneyId/{busJourneyId}/fetchSeats")
	public ResponseEntity<List<FetchSeatsDTO>> searchBuses(@PathVariable("busJourneyId") String busJourneyId)
	{
		System.out.println("In controller");
		List<FetchSeatsDTO> seats = commonservice.getFetchSeats(busJourneyId);
		ResponseEntity<List<FetchSeatsDTO>> res = new ResponseEntity<>(seats,HttpStatus.OK);
		return res;
	}
	
	@PostMapping("/paymentCallbackUrl/orderNumber/{orderNumber}/paymentStatus/{paymentStatus}")
	public ResponseEntity<Void> validatePaymentDetails(@PathVariable("orderNumber") String orderNumber, @PathVariable("paymentStatus") String paymentStatus,@RequestBody String paymentValue)
	{
		System.out.println("In controller");
		commonservice.validatePaymentAndUpdate(orderNumber,paymentStatus,paymentValue);
		ResponseEntity<Void> res = new ResponseEntity<>(HttpStatus.OK);
		return res;
		
	}
	
}
