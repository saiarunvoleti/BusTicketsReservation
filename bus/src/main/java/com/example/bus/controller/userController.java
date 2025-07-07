package com.example.bus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bus.dto.FetchSeatsDTO;
import com.example.bus.dto.OrderConfirmationDTO;
import com.example.bus.dto.SelectSeatsRequestDTO;
import com.example.bus.dto.UserCredentialsDTO;
import com.example.bus.dto.UserDetailsDTO;
import com.example.bus.service.userDetailsService;

import jakarta.validation.Valid;



@RestController
public class userController {

	@Autowired
	userDetailsService userService;
	
	@PostMapping("/userId/{userId}/searchBuses/busJourneyId/{busJourneyId}/selectSeats")
	public ResponseEntity<String> searchBuses(@PathVariable("userId") String userId,@PathVariable("busJourneyId") String busJourneyId,@RequestBody List<SelectSeatsRequestDTO> request)
	{
		System.out.println("In controller");
		String orderId = userService.selectSeats(userId,busJourneyId,request);
		ResponseEntity<String> res = new ResponseEntity<>(orderId,HttpStatus.OK);
		return res;
	}
	
	@GetMapping("/userId/{userId}/searchBuses/busJourneyId/{busJourneyId}/bookingStatus/{orderNumber}")
	public ResponseEntity<List<OrderConfirmationDTO>> checkBookingStatus(@PathVariable("userId") String userId,@PathVariable("busJourneyId") String busJourneyId,@PathVariable("orderNumber") String orderNumber)
	{
		System.out.println("In controller");
		List<OrderConfirmationDTO> result = userService.checkPaymentStatus(userId,busJourneyId,orderNumber);
		ResponseEntity<List<OrderConfirmationDTO>> res = new ResponseEntity<>(result,HttpStatus.OK);
		return res;
	}
	
	@GetMapping("/userId/{userId}/searchBuses/busJourneyId/{busJourneyId}/orderDownload/{orderNumber}")
	public ResponseEntity<String> getTicketDocument(@PathVariable("userId") String userId,@PathVariable("busJourneyId") String busJourneyId,@PathVariable("orderNumber") String orderNumber)
	{
		System.out.println("In controller");
		List<OrderConfirmationDTO> result = userService.checkPaymentStatus(userId,busJourneyId,orderNumber);
		String base64 = userService.getTicket(result);
		ResponseEntity<String> res = new ResponseEntity<>(base64,HttpStatus.OK);
		return res;
	} 
	
	
	@GetMapping("/userId/{userId}/getAllTickets")
	public ResponseEntity<List<OrderConfirmationDTO>> getTicketList(@PathVariable("userId") String userId)
	{
		System.out.println("In controller");
		List<OrderConfirmationDTO> result = userService.getListOfTickets(userId);
		ResponseEntity<List<OrderConfirmationDTO>> res = new ResponseEntity<>(result,HttpStatus.OK);
		return res;
	}
	
	@DeleteMapping("/userId/{userId}/cancelTicket/{orderId}")
	public ResponseEntity<Void> cancelTicket(@PathVariable("userId") String userId, @PathVariable("orderId") String orderId)
	{
		System.out.println("In controller");
		userService.cancelTicket(userId,orderId);
		ResponseEntity<Void> res = new ResponseEntity<>(HttpStatus.OK);
		return res;
	}
	
	
}
