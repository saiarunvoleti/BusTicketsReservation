package com.example.bus.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.bus.dto.BusDetailsDTO;
import com.example.bus.dto.BusJourneyDetailsDTO;
import com.example.bus.dto.OrderConfirmationDTO;
import com.example.bus.entity.BusDetailsEntity;
import com.example.bus.entity.BusJourneyDetailsEntity;
import com.example.bus.entity.SeatBookingDetailsEntity;
import com.example.bus.entity.SeatDetailsEntity;
import com.example.bus.entity.TravellerDetailsEntity;
import com.example.bus.exception.DuplicateFieldException;
import com.example.bus.exception.ForbiddenException;
import com.example.bus.repository.busDetailsRepo;
import com.example.bus.repository.busJourneyDetailsRepo;
import com.example.bus.repository.commonRepository;
import com.example.bus.repository.seatAndBusDetailsRepo;
import com.example.bus.repository.seatBookingDetailsRepo;
import com.example.bus.repository.travellerDetailsRepo;

@Service
public class busDetailsService {

	@Autowired
	travellerDetailsRepo travelRepo;
	
	@Autowired
	busDetailsRepo busRepo;
	
	@Autowired
	seatAndBusDetailsRepo seatRepo;
	
	@Autowired
	busJourneyDetailsRepo busJourneyRepo;
	
	@Autowired
	seatBookingDetailsRepo seatBookingRepo;
	
	@Autowired
	commonRepository commonRepo;
	
	public void registerBus(String travellerIdString,BusDetailsDTO busDetails)
	{
		Long travellerId = Long.valueOf(travellerIdString);
		TravellerDetailsEntity travellerEntity = travelRepo.findById(travellerId).get();
		
		BusDetailsEntity busDetailsEntity = new BusDetailsEntity();
		busDetailsEntity.setBusNumber(busDetails.getBusNumber());
		busDetailsEntity.setIsACBus(busDetails.getIsACBus());
		busDetailsEntity.setTravelDetails(travellerEntity);
		
		List<String> seats = busDetails.getSeats();
		
		
		List<SeatDetailsEntity> seatDetailsEntity = seats.stream().map((seat)->{
			SeatDetailsEntity seatEntity = new SeatDetailsEntity();
			seatEntity.setSeatNumber(seat);
			seatEntity.setBusDetails(busDetailsEntity);
			return seatEntity;
		}).collect(Collectors.toList());
		
		
		if(busRepo.existsByBusNumber(busDetails.getBusNumber()))
		{
			System.out.println("BusNumber is already present");
			throw new DuplicateFieldException("BUS_NUMBER","bus number is already present");
		}
		else
		{
			try
			{
				System.out.println("Before save method");
				seatRepo.saveAll(seatDetailsEntity);
				System.out.println("After save method");
			}
			catch (DataIntegrityViolationException ex) {
	            System.out.println("Inside service exception");
				// Fallback safety in case of race condition or direct DB constraint violation
	            throw new DuplicateFieldException("BUS_NUMBER","bus number is already present");
	        }
		}	
		
		
	}
	
	
	public void registerBusJourney(String travellerIdString,BusJourneyDetailsDTO busJourneyDetails)
	{
		System.out.println("inside service");
		Long travellerId = Long.valueOf(travellerIdString);	
		
		BusDetailsEntity busDetailsEntity = busRepo.findByBusNumber(busJourneyDetails.getBusNumber());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//verify if the bus is of same traveller
		if(busDetailsEntity!=null && busDetailsEntity.getTravelDetails().getTravellerId() == travellerId)
		{
			BusJourneyDetailsEntity busJourneyEntityDetails = new BusJourneyDetailsEntity();
			busJourneyEntityDetails.setBusDetails(busDetailsEntity);
			busJourneyEntityDetails.setBusNumber(busJourneyDetails.getBusNumber());
			try {
				busJourneyEntityDetails.setDateOfArrival(sdf.parse(busJourneyDetails.getArrivalDate()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				busJourneyEntityDetails.setDateOfDeparture(sdf.parse(busJourneyDetails.getDepartureDate()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			busJourneyEntityDetails.setFromStation(busJourneyDetails.getFromStation());
			busJourneyEntityDetails.setTicketPrice(Long.valueOf(busJourneyDetails.getTicketPrice()));
			busJourneyEntityDetails.setToStation(busJourneyDetails.getToStation());
			
			//now save busjourney details
			try
			{
				System.out.println("Before save method");
				busJourneyRepo.save(busJourneyEntityDetails);
				System.out.println("After save method");
			}
			catch (DataIntegrityViolationException ex) {
	            System.out.println("Inside service exception");
				// Fallback safety in case of race condition or direct DB constraint violation
	            throw new DuplicateFieldException("BUS_NUMBER , DATE ","bus number in this date is already present");
	        }
			
			
			//now save seatbookingstatusdetails 
			//get list of seats of bus id
			List<SeatDetailsEntity> seatDetails= busJourneyEntityDetails.getBusDetails().getSeatsList();
			
			List<SeatBookingDetailsEntity> seatBookingDetailsEntity = seatDetails.stream().map((seat) -> {
				SeatBookingDetailsEntity seatDetail = new SeatBookingDetailsEntity();
				seatDetail.setBusJourneyDetails(busJourneyEntityDetails);
				seatDetail.setSeatDetails(seat);
				return seatDetail;
			}).collect(Collectors.toList());
			
					
			try
			{
				System.out.println("Before seat booking status details");
				
				seatBookingRepo.saveAll(seatBookingDetailsEntity);
				
				System.out.println("After seat booking status details");
			}
			catch (DataIntegrityViolationException ex) {
	            System.out.println("Inside service exception");
				// Fallback safety in case of race condition or direct DB constraint violation
	            throw new DuplicateFieldException("BUS_NUMBER , DATE ","bus number in this date is already present");
	        }
			
			
		}
		else
		{
			throw new ForbiddenException("You are not authorized for this operation");
		}
	
	}
	
	
	public List<String> getBusLists(String travellerIdString)
	{
		Long travellerId = Long.valueOf(travellerIdString);
		
		//now get the details of all passengers,seat number, bus number, travels name,total value
		List<String> busnumbers = commonRepo.findAllByTravelsId(travellerId);
		return busnumbers;
		
	}
	
}
