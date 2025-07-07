package com.example.bus.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.bus.dto.BusSearchResultsDTO;
import com.example.bus.dto.TravellerBusSearchDTO;
import com.example.bus.dto.TravellerDetailsDTO;
import com.example.bus.dto.UserDetailsDTO;
import com.example.bus.entity.BusJourneyDetailsEntity;
import com.example.bus.entity.OrderDetailsEntity;
import com.example.bus.entity.TravellerDetailsEntity;
import com.example.bus.entity.UserDetailsEntity;
import com.example.bus.exception.DuplicateFieldException;
import com.example.bus.exception.ForbiddenException;
import com.example.bus.repository.busJourneyDetailsRepo;
import com.example.bus.repository.commonRepository;
import com.example.bus.repository.travellerDetailsRepo;
import com.example.bus.repository.userDetailsRepo;

@Service
public class travellerDetailsService {

	@Autowired
	travellerDetailsRepo travellerRepo;
	
	@Autowired
	userDetailsRepo userRepo;
	
	@Autowired
	commonRepository commonRepo;
	
	@Autowired
	busJourneyDetailsRepo busJourneyRepo;
	
	public void saveTraveller(TravellerDetailsDTO travellerDetails)
	{
		System.out.println("In service");
		TravellerDetailsEntity travellerDetailsEntity = new TravellerDetailsEntity();
		
		UserDetailsDTO userDetails = travellerDetails.getUserdetails();
		
		UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
		userDetailsEntity.setAge(userDetails.getAge());
		userDetailsEntity.setGender(userDetails.getGender());
		userDetailsEntity.setMailId(userDetails.getMailId());
		userDetailsEntity.setMobileNumber(userDetails.getMobileNumber());
		userDetailsEntity.setUserName(userDetails.getUserName());
		userDetailsEntity.setUserType(userDetails.getUserType());
		
		travellerDetailsEntity.setTravellerName(userDetails.getUserName());
		travellerDetailsEntity.setUserDetails(userDetailsEntity);
		
		if(userRepo.existsByMailId(userDetails.getMailId()))
		{
			System.out.println("In mail id not present");
			throw new DuplicateFieldException("MAIL_ID","mail id is already present");
		}
		else if(userRepo.existsByMobileNumber(userDetails.getMobileNumber()))
		{
			System.out.println("In mobile number not present");
			throw new DuplicateFieldException("MOBILE_NUMBER","mobile number is already present");
		}
		else
		{
			try
			{
				System.out.println("Before save method");
				travellerRepo.save(travellerDetailsEntity);
				System.out.println("After save method");
			}
			catch (DataIntegrityViolationException ex) {
	            System.out.println("Inside service exception");
				// Fallback safety in case of race condition or direct DB constraint violation
				throw new DuplicateFieldException("MAIL_ID","mail id is already present");
	        }
		}	
		
	}
	
	public Date dateFormatting(String date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<BusSearchResultsDTO> getListOfTravellerBusJourneys(String travelsIdString, TravellerBusSearchDTO searchBuses)
	{
		String fromStation = searchBuses.getFromStation();
		String toStation = searchBuses.getToStation();
		String dateString = searchBuses.getDate();
		Long travelsId = Long.valueOf(travelsIdString);
		
		Date date = dateFormatting(dateString);
		
		List<BusSearchResultsDTO> result = commonRepo.searchBusJourneysofTravels(travelsId,fromStation, toStation, date);
		return result;
	}
	
	public void cancelJourney(String travelsIdString,String busJourneyId)
	{
		Long travelsId = Long.valueOf(travelsIdString);
		Long busJoureyId = Long.valueOf(busJourneyId);
		
		BusJourneyDetailsEntity busJourney = busJourneyRepo.findByBusJourneyId(busJoureyId);
		
		Long travelsiDVerify = busJourney.getBusDetails().getTravelDetails().getTravellerId();
		if(!travelsiDVerify.equals(travelsId))
		{
			throw new ForbiddenException("User is not authorized");
		}
		
		commonRepo.cancelJoruneyAndUpdate(travelsId,busJoureyId);
		
	}
	
}
