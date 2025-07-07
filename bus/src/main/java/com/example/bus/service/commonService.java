package com.example.bus.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bus.dto.BusSearchResultsDTO;
import com.example.bus.dto.FetchSeatsDTO;
import com.example.bus.dto.SearchBusesDTO;
import com.example.bus.dto.TravellerBusSearchDTO;
import com.example.bus.entity.OrderDetailsEntity;
import com.example.bus.entity.UserDetailsEntity;
import com.example.bus.repository.commonRepository;
import com.example.bus.repository.orderDetailsRepo;

@Service
public class commonService {
	
	@Autowired
	commonRepository commonRepo;
	
	@Autowired
	orderDetailsRepo orderRepo;

	public List<String> getListOfCities(String Fromcity)
	{
		String[] cities = {
				"Hyderabad",
				"Rajahmundry",
				"Bengaluru",
				"Chennai",
				"Delhi",
				"Kolkata",
				"Vishakapatnam",
				"Vijayawada",
				"Nagpur",
				"Mumbai",
				"Kochi"
		};
		
		List<String> filterCities = new ArrayList<>();
		for(String city : cities )
		{
			if(Fromcity == null)
			{
				filterCities.add(city);
			}
			else if(!Fromcity.equals(city))
			{
				filterCities.add(city);
			}
		}
		
		return filterCities;
		
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
	
	public List<BusSearchResultsDTO> getListOfBuses(SearchBusesDTO searchBuses)
	{
		String fromStation = searchBuses.getFromStation();
		String toStation = searchBuses.getToStation();
		String dateString = searchBuses.getDate();
		String isAcBus = searchBuses.getIsACBus();
		
		Date date = dateFormatting(dateString);
		
		List<BusSearchResultsDTO> result = commonRepo.searchBuses(fromStation, toStation, date,isAcBus);
		
		//now format the output
		return result;	
	}
	
	public List<FetchSeatsDTO> getFetchSeats(String busJourneyIdString)
	{
		Long busJourneyId = Long.valueOf(busJourneyIdString);
		
		List<FetchSeatsDTO> result = commonRepo.fetchSeats(busJourneyId);
		return result;
		
	}
	
	public void validatePaymentAndUpdate(String orderNumberString, String paymentStatus , String paymentValueString)
	{
		Long orderNumber = Long.valueOf(orderNumberString);
		Long paymentValue = Long.valueOf(paymentValueString);
		
		//get orderId
		OrderDetailsEntity order = orderRepo.findByOrderNumber(orderNumber);
		Long orderId = order.getOrderId();
		
		if(paymentStatus.equals("Success"))
		{
			//update the payment status and seat available to 'N'
			commonRepo.updateSelectedSeatStatusToN(orderId,paymentValue);
			
		}
		else
		{
			//update passenger details,order id to null and seat available to 'Y' 
			commonRepo.updateSelectedSeatStatusToA(orderId);
		}
		
	}
	
}
