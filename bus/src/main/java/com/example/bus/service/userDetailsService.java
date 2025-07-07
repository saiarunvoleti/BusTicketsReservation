package com.example.bus.service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.bus.dto.OrderConfirmationDTO;
import com.example.bus.dto.SelectSeatsRequestDTO;
import com.example.bus.dto.UserDetailsDTO;
import com.example.bus.entity.OrderDetailsEntity;
import com.example.bus.entity.TravellerDetailsEntity;
import com.example.bus.entity.UserDetailsEntity;
import com.example.bus.exception.DuplicateFieldException;
import com.example.bus.exception.ForbiddenException;
import com.example.bus.repository.commonRepository;
import com.example.bus.repository.orderDetailsRepo;
import com.example.bus.repository.travellerDetailsRepo;
import com.example.bus.repository.userDetailsRepo;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

@Service
public class userDetailsService {
	
	@Autowired
	userDetailsRepo userRepo;
	
	@Autowired
	commonRepository commonRepo;
	
	@Autowired
	orderDetailsRepo orderRepo;
	
	@Autowired
	travellerDetailsRepo travelRepo;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
	
	public UserDetailsEntity getUserByEmail(String email)
	{
		return userRepo.findByMailId(email);
	}
	
	public void saveUser(UserDetailsDTO userDetails)
	{
		System.out.println("In service");
		UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
		
		userDetailsEntity.setAge(userDetails.getAge());
		userDetailsEntity.setGender(userDetails.getGender());
		userDetailsEntity.setMailId(userDetails.getMailId());
		userDetailsEntity.setMobileNumber(userDetails.getMobileNumber());
		userDetailsEntity.setUserName(userDetails.getUserName());
		userDetailsEntity.setUserType(userDetails.getUserType());
		userDetailsEntity.setPassword(passwordEncoder.encode(userDetails.getPassword()));
		
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
				userRepo.save(userDetailsEntity);
				
				if(userDetails.getUserType().equals("Traveller"))
				{
					TravellerDetailsEntity travelsEntity = new TravellerDetailsEntity();
					travelsEntity.setTravellerName(userDetails.getUserName());
					travelsEntity.setUserDetails(userDetailsEntity);
					travelRepo.save(travelsEntity);	
				}
				
				System.out.println("After save method");
			}
			catch (DataIntegrityViolationException ex) {
	            System.out.println("Inside service exception");
				// Fallback safety in case of race condition or direct DB constraint violation
				throw new DuplicateFieldException("MAIL_ID","mail id is already present");
	        }
		}	
		
	}

	public String selectSeats(String userIdString, String busJourneyId,List<SelectSeatsRequestDTO> request)
	{
		
		Long userId = Long.valueOf(userIdString);
		UserDetailsEntity user = userRepo.findByUserId(userId);
		
		//create a order first and then update all those values in seatbookingdetails
		OrderDetailsEntity orderDetails = new OrderDetailsEntity();
		
		//get orderNumber max and add +1 to it
		Long newOrderNumber = commonRepo.getNextOrderNumber();
		
		orderDetails.setOrderNumber(newOrderNumber); 
		orderDetails.setPaymentStatus("Pending");
		orderDetails.setUserDetails(user);
		
		//save the order
		OrderDetailsEntity orderDetailsCreated = orderRepo.save(orderDetails);
		
		Long orderId = orderDetailsCreated.getOrderId();
		
		for(SelectSeatsRequestDTO seat : request)
		{
			//udpate
			commonRepo.updateSelectedSeatStatus(seat,orderId);
		}

		return newOrderNumber.toString();
		
		
	}
	
	
	public List<OrderConfirmationDTO> checkPaymentStatus(String userIdString,String busJourneyId,String orderNumberString)
	{
		Long orderNumber = Long.valueOf(orderNumberString);
		OrderDetailsEntity orderDetails = orderRepo.findByOrderNumber(orderNumber);
		
		Long orderId = orderDetails.getOrderId();
		
		Long useriDVerify = orderDetails.getUserDetails().getUserId();
		Long userId = Long.valueOf(userIdString);
		if(!useriDVerify.equals(userId))
		{
			throw new ForbiddenException("User is not authorized");
		}
		
		//now get the details of all passengers,seat number, bus number, travels name,total value
		List<OrderConfirmationDTO> result = commonRepo.getOrderDetails(orderId);
		return result;
		
		
	}
	
	public List<OrderConfirmationDTO> getListOfTickets(String userIdString)
	{
		Long userId = Long.valueOf(userIdString);
		
		//now get the details of all passengers,seat number, bus number, travels name,total value
		List<OrderConfirmationDTO> result = commonRepo.getListOfTickets(userId);
		return result;
				
	}
	
	public void cancelTicket(String userIdString,String orderIdString)
	{
		Long userId = Long.valueOf(userIdString);
		Long orderId = Long.valueOf(orderIdString);
		OrderDetailsEntity orderDetails = orderRepo.findByOrderId(orderId);
		Long useriDVerify = orderDetails.getUserDetails().getUserId();
		if(!useriDVerify.equals(userId))
		{
			throw new ForbiddenException("User is not authorized");
		}
		
		commonRepo.cancelTicketAndUpdate(userId,orderId);
		
		
	}
	
	public String getTicket(List<OrderConfirmationDTO> request)
	{
		String context = "";
		
		if(request.size() > 0)
		{
			
			String travelsName = request.get(0).getTravelsName();
			String busNumber  = request.get(0).getBusNumber();
			String fromStation = request.get(0).getFromStation();
			String departureDate = request.get(0).getDateOfDeparture();
			String toStation = request.get(0).getToStation();
			String arrivalDate = request.get(0).getDateOfArrival();
			Long orderId = request.get(0).getOrderId();
			Long billAmount = request.get(0).getBillAmount();
			
			context = context +  "Ticket Id - " + orderId + "\n";
			context = context + "Travels Name - " + travelsName + "\n";
			context = context + "Bus Number - " + busNumber + "\n";
			context = context + "Pickup Point - " + fromStation + " on " + departureDate + "\n";
			context = context + "Drop Point - " + fromStation + " on " + arrivalDate + "\n";
			context = context + "Passenger details - \n";
			context = context + "Seat Number - " + "Name - "  + "age - " + "Gender \n";
			for(OrderConfirmationDTO passenger : request)
			{
				String passengerName = passenger.getPassengerName();
				String passengerGender = passenger.getPassengerGender();
				Long passengerAge = passenger.getPassengerAge();
				String passengerSeatNumber = passenger.getSeatNumber();
				
				context = context + passengerSeatNumber + " - " + passengerName +" - "  + passengerAge + " - " + passengerGender +" \n";
					
			}
			
			context = context + "Total Bill Amount - " + billAmount;
			
			try {
				// Use ByteArrayOutputStream to hold PDF data in memory
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();

	            // Create PDF writer using output stream
	            PdfWriter writer = new PdfWriter(baos);
	            PdfDocument pdf = new PdfDocument(writer);
	            Document document = new Document(pdf);

	            // Add your context string as a paragraph
	            document.add(new Paragraph(context));

	            // Close document to complete writing
	            document.close();

	            // Get PDF bytes
	            byte[] pdfBytes = baos.toByteArray();

	            // Encode to Base64
	            String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);
	            return base64Pdf;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
			
			
		}
		return null;
		
	}
	
}
