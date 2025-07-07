package com.example.bus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bus.dto.BusDetailsDTO;
import com.example.bus.dto.BusJourneyDetailsDTO;
import com.example.bus.dto.BusSearchResultsDTO;
import com.example.bus.dto.SearchBusesDTO;
import com.example.bus.dto.TravellerBusSearchDTO;
import com.example.bus.dto.TravellerDetailsDTO;
import com.example.bus.dto.UserDetailsDTO;
import com.example.bus.service.busDetailsService;
import com.example.bus.service.travellerDetailsService;
import com.example.bus.utils.UtilClass;

import jakarta.validation.Valid;

@RestController
public class travellerController {
	
	@Autowired
	travellerDetailsService travellerService;
	
	@Autowired
	busDetailsService busService;
	
	@Autowired
	UtilClass utilClass;
	
//	@PostMapping("/createTraveller")
//	public ResponseEntity<String> createTravels(@Valid @RequestBody TravellerDetailsDTO travellerDetails)
//	{
//		System.out.println("In controller");
//		travellerService.saveTraveller(travellerDetails);
//		ResponseEntity<String> res = new ResponseEntity<>("Success",HttpStatus.CREATED);
//		return res;
//	}
	
	@PostMapping("/traveller/{travelsId}/registerBus")
	public ResponseEntity<String> registerBus(@PathVariable("travelsId") String travelsId,@RequestBody BusDetailsDTO busDetails)
	{	
		System.out.println("In controller");
		busService.registerBus(travelsId, busDetails);
		ResponseEntity<String> res = new ResponseEntity<>("Success",HttpStatus.CREATED);
		return res;
	}
	
	@PostMapping("/traveller/{travelsId}/registerBusJourney")
	public ResponseEntity<String> registerBusJoruney(@PathVariable("travelsId") String travelsId,@RequestBody BusJourneyDetailsDTO busJoruneyDetails)
	{
		System.out.println("In controller");
		busService.registerBusJourney(travelsId, busJoruneyDetails);
		ResponseEntity<String> res = new ResponseEntity<>("Success",HttpStatus.CREATED);
		return res;
	}
	
	@GetMapping("/traveller/{travelsId}/getBusNumbers")
	public ResponseEntity<String> registerBusJoruney(@PathVariable("travelsId") String travelsId)
	{
		System.out.println("In controller");
		busService.getBusLists(travelsId);
		ResponseEntity<String> res = new ResponseEntity<>("Success",HttpStatus.CREATED);
		return res;
	}
	
	@PostMapping("/traveller/{travelsId}/searchBusJourneys")
	public ResponseEntity<List<BusSearchResultsDTO>> searchBuses(@PathVariable("travelsId") String travelsId, @RequestBody TravellerBusSearchDTO searchInput)
	{
		System.out.println("In controller");
		List<BusSearchResultsDTO> buses = travellerService.getListOfTravellerBusJourneys(travelsId,searchInput);
		ResponseEntity<List<BusSearchResultsDTO>> res = new ResponseEntity<>(buses,HttpStatus.OK);
		return res;
	}
	
	@DeleteMapping("/traveller/{travelsId}/cancelJourney/{journeyId}")
	public ResponseEntity<Void> cancelTicket(@PathVariable("travelsId") String travelsId, @PathVariable("journeyId") String busJourneyId)
	{
		System.out.println("In controller");
		travellerService.cancelJourney(travelsId,busJourneyId);
		ResponseEntity<Void> res = new ResponseEntity<>(HttpStatus.OK);
		return res;
	}
	
}
