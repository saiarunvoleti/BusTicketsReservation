package com.example.bus.repository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.example.bus.dto.BusSearchResultsDTO;
import com.example.bus.dto.FetchSeatsDTO;
import com.example.bus.dto.OrderConfirmationDTO;
import com.example.bus.dto.SelectSeatsRequestDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class commonRepository {

	@Autowired
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<BusSearchResultsDTO> searchBuses(String fromStation,String toStation, Date date,String isAcBus)
	{
		String query = "SELECT \n"
				+ "       bj.bus_journey_id as BusJourneyId,t.TRAVELLER_NAME as TravellerName,\n"
				+ "       bj.FROM_STATION as FromStation,\n"
				+ "       bj.TO_STATION as ToStation,\n"
				+ "       TO_CHAR(bj.DATE_OF_DEPARTURE, 'YYYY-MM-DD') AS DepartureDate,\n"
				+ "       TO_CHAR(bj.DATE_OF_ARRIVAL, 'YYYY-MM-DD') AS ArrivalDate,\n"
				+ "       CAST(b.IS_AC_BUS AS VARCHAR) AS IsAcBus\n"
				+ "FROM BUS_JOURNEY_DETAILS bj\n"
				+ "JOIN BUS_DETAILS b ON b.bus_id = bj.bus_id\n"
				+ "JOIN TRAVELS_DETAILS t ON t.travels_id = b.travels_id\n"
				+ "WHERE bj.FROM_STATION = :fromCity\n"
				+ "  AND bj.TO_STATION = :toCity\n"
				+ "  AND bj.DATE_OF_DEPARTURE = :Date\n";
				
		
		if (isAcBus != null) {
			query += " AND b.IS_AC_BUS = :isAcBus";
		}
		
		query+=";" ;
		
		Query emquery = em.createNativeQuery(query,"BusJourneyInfoMapping").setParameter("fromCity", fromStation)
				.setParameter("toCity", toStation).setParameter("Date", date);
		
		if(isAcBus!=null)
		{
			emquery.setParameter("isAcBus", isAcBus);
		}
		
		List<BusSearchResultsDTO> result = emquery.getResultList();
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<FetchSeatsDTO> fetchSeats(Long busJourneyId)
	{
		String query = "select sb.seat_booking_id SeatBookingId,sb.seat_id SeatId,s.seat_number SeatNumber,sb.IS_SEAT_AVAILABLE IsSeatAvailable,sb.bus_journey_id BusJourneyId\n"
				+ "from SEAT_BOOKING_DETAILS sb \n"
				+ "join SEAT_DETAILS s on s.seat_id = sb.seat_id\n"
				+ "where bus_journey_id= :busJourneyId;" ;
		
		List<FetchSeatsDTO> result= em.createNativeQuery(query,"SeatsFetchMapping").setParameter("busJourneyId", busJourneyId).getResultList();
		
		return result;
				
	}
	
	@SuppressWarnings("unchecked")
	public Long getNextOrderNumber()
	{
		String query = "select max(ORDER_NUMBER) from ORDER_DETAILS;";
		List<Long> result= em.createNativeQuery(query).getResultList();
		if(result==null || result.size() == 0 )
		{
			return Long.valueOf("1000");
		}
		else
		{
			if(result.get(0)==null)
			{
				return Long.valueOf("1000");
			}
			return result.get(0) + Long.valueOf("1");
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Modifying
	public void updateSelectedSeatStatus(SelectSeatsRequestDTO seat,Long orderId)
	{
		String query = "update seat_booking_details\n"
				+ "set is_seat_available = 'H' , passenger_name = :passengerName , passenger_age = :passengerAge, passenger_gender = :passengerGender,order_id = :orderId\n"
				+ "where seat_booking_id = :seatBookingId and bus_journey_id = :busJourneyId and seat_id = :seatId;";
		
		//update orderid to the relevant 
		em.createNativeQuery(query)
		.setParameter("passengerName", seat.getPassengerName())
		.setParameter("passengerAge",seat.getPassengerAge())
		.setParameter("passengerGender", seat.getPassengerGender())
		.setParameter("orderId", orderId)
		.setParameter("seatBookingId", seat.getSeatBookingId())
		.setParameter("busJourneyId", seat.getBusJourneyId())
		.setParameter("seatId", seat.getSeatId())
		.executeUpdate();
					
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Modifying
	public void updateSelectedSeatStatusToN(Long orderId,Long totalValue)
	{
		String query = "update seat_booking_details\n"
				+ "set is_seat_available = 'N' \n"
				+ "where ORDER_ID = :orderId;";
		
		//update orderid to the relevant 
		em.createNativeQuery(query)
		.setParameter("orderId", orderId)
		.executeUpdate();
		
		String query2 = "update order_details\n"
				+ "set payment_status = 'Success',total_value =:totalValue \n"
				+ "where ORDER_ID = :orderId;";
		
		//update orderid to the relevant 
		em.createNativeQuery(query2)
		.setParameter("orderId", orderId)
		.setParameter("totalValue", totalValue)
		.executeUpdate();
				
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Modifying
	public void updateSelectedSeatStatusToA(Long orderId)
	{
		String query = "update seat_booking_details\n"
				+ "set is_seat_available = 'Y' , passenger_name = null , passenger_age = null, passenger_gender = null ,order_id = null \n"
				+ "where order_id = :orderId;";
		
		//update orderid to the relevant 
		em.createNativeQuery(query)
		.setParameter("orderId", orderId)
		.executeUpdate();
		
		String query2 = "update order_details\n"
				+ "set payment_status = 'Failed',total_value = 0 \n"
				+ "where ORDER_ID = :orderId;";
		
		//update orderid to the relevant 
		em.createNativeQuery(query2)
		.setParameter("orderId", orderId)
		.executeUpdate();
					
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Modifying
	public void cancelTicketAndUpdate(Long userId,Long orderId)
	{
		String query = "update order_details\n"
				+ "set payment_status = 'Refunded',total_value = 0 \n"
				+ "where ORDER_ID = :orderId;";
		
		//update orderid to the relevant 
		em.createNativeQuery(query)
		.setParameter("orderId", orderId)
		.executeUpdate();
		
		String query2 = "update seat_booking_details\n"
				+ "set is_seat_available = 'Y' , passenger_name = null , passenger_age = null, passenger_gender = null ,order_id = null \n"
				+ "where order_id = :orderId;";
		
		//update orderid to the relevant 
		em.createNativeQuery(query2)
		.setParameter("orderId", orderId)
		.executeUpdate();
		
		
	}
	
	//get order details
	@SuppressWarnings("unchecked")
	public List<OrderConfirmationDTO> getOrderDetails(Long orderId)
	{
		String query = "SELECT \n"
				+ "    o.order_id AS OrderId,\n"
				+ "    o.payment_status AS PaymentStatus,\n"
				+ "    o.total_value AS BillAmount,\n"
				+ "    bj.bus_number AS BusNumber,\n"
				+ "    bj.from_station AS FromStation,\n"
				+ "    bj.to_station AS ToStation,\n"
				+ "    TO_CHAR(bj.date_of_departure, 'YYYY-MM-DD') AS DateOfDeparture,\n"
				+ "    TO_CHAR(bj.DATE_OF_ARRIVAL, 'YYYY-MM-DD') AS DateOfArrival,\n"
				+ "    t.traveller_name AS TravelsName,\n"
				+ "    s.seat_number AS SeatNumber,\n"
				+ "    sb.passenger_name AS PassengerName, -- corrected spelling\n"
				+ "    sb.passenger_age AS PassengerAge,\n"
				+ "    sb.passenger_gender AS PassengerGender\n"
				+ "FROM order_details o\n"
				+ "JOIN seat_booking_details sb ON sb.order_id = o.order_id\n"
				+ "JOIN seat_details s ON sb.seat_id = s.seat_id\n"
				+ "JOIN bus_journey_details bj ON sb.bus_journey_id = bj.bus_journey_id -- corrected table name\n"
				+ "JOIN bus_details b ON bj.bus_id = b.bus_id\n"
				+ "JOIN travels_details t ON b.travels_id = t.travels_id\n"
				+ "WHERE o.order_id = :orderId;";
		
		List<OrderConfirmationDTO> result= em.createNativeQuery(query,"OrderConfirmationMapping").setParameter("orderId", orderId).getResultList();
		return result;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findAllByTravelsId(Long travelsId)
	{
		String query = "select distinct bus_number from bus_details where travels_id:travelsId;";
		
		List<String> result= em.createNativeQuery(query,String.class).setParameter("travelsId", travelsId).getResultList();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<OrderConfirmationDTO> getListOfTickets(Long userId)
	{
		String query = "SELECT \n"
				+ "    o.order_id AS OrderId,\n"
				+ "    o.payment_status AS PaymentStatus,\n"
				+ "    o.total_value AS BillAmount,\n"
				+ "    bj.bus_number AS BusNumber,\n"
				+ "    bj.from_station AS FromStation,\n"
				+ "    bj.to_station AS ToStation,\n"
				+ "    TO_CHAR(bj.date_of_departure, 'YYYY-MM-DD') AS DateOfDeparture,\n"
				+ "    TO_CHAR(bj.DATE_OF_ARRIVAL, 'YYYY-MM-DD') AS DateOfArrival,\n"
				+ "    t.traveller_name AS TravelsName,\n"
				+ "    s.seat_number AS SeatNumber,\n"
				+ "    sb.passenger_name AS PassengerName, -- corrected spelling\n"
				+ "    sb.passenger_age AS PassengerAge,\n"
				+ "    sb.passenger_gender AS PassengerGender\n"
				+ "FROM order_details o\n"
				+ "JOIN seat_booking_details sb ON sb.order_id = o.order_id\n"
				+ "JOIN seat_details s ON sb.seat_id = s.seat_id\n"
				+ "JOIN bus_journey_details bj ON sb.bus_journey_id = bj.bus_journey_id -- corrected table name\n"
				+ "JOIN bus_details b ON bj.bus_id = b.bus_id\n"
				+ "JOIN travels_details t ON b.travels_id = t.travels_id\n"
				+ "WHERE o.user_id = :userId\n"
				+ "ORDER BY bj.date_of_departure DESC\n"
				+ "LIMIT 5;";
		
		List<OrderConfirmationDTO> result= em.createNativeQuery(query,"OrderConfirmationMapping").setParameter("userId", userId).getResultList();
		return result;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<BusSearchResultsDTO> searchBusJourneysofTravels(Long travelsId,String fromStation,String toStation, Date date)
	{
		String query = "SELECT \n"
				+ "       bj.bus_journey_id as BusJourneyId,t.TRAVELLER_NAME as TravellerName,\n"
				+ "       bj.FROM_STATION as FromStation,\n"
				+ "       bj.TO_STATION as ToStation,\n"
				+ "       TO_CHAR(bj.DATE_OF_DEPARTURE, 'YYYY-MM-DD') AS DepartureDate,\n"
				+ "       TO_CHAR(bj.DATE_OF_ARRIVAL, 'YYYY-MM-DD') AS ArrivalDate,\n"
				+ "       CAST(b.IS_AC_BUS AS VARCHAR) AS IsAcBus\n"
				+ "FROM BUS_JOURNEY_DETAILS bj\n"
				+ "JOIN BUS_DETAILS b ON b.bus_id = bj.bus_id\n"
				+ "JOIN TRAVELS_DETAILS t ON t.travels_id = b.travels_id\n"
				+ "WHERE t.travels_id=:travelsId\n";
				
		
		if (fromStation != null) {
			query += " bj.FROM_STATION = :fromCity\n";
		}
		
		if (toStation != null) {
			query += " AND bj.TO_STATION = :toCity\n";
		}
		
		if (date != null) {
			query += " bj.DATE_OF_DEPARTURE = :Date\n";
		}
		
		query+=";" ;
		
		Query emquery = em.createNativeQuery(query,"BusJourneyInfoMapping").setParameter("travelsId", travelsId);
				
		if (fromStation != null) {
			emquery.setParameter("fromCity", fromStation);
		}
		
		if (toStation != null) {
			emquery.setParameter("toCity", toStation);
		}
		
		if (date != null) {
			emquery.setParameter("Date", date);
		}
		
		List<BusSearchResultsDTO> result = emquery.getResultList();
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Modifying
	public void cancelJoruneyAndUpdate(Long travelsId,Long busJourneyId)
	{
		String query = "SELECT distinct ORDER_ID from seat_booking_details where order_id is not null and bus_journey_id=:busJourneyId;";
		
		//update orderid to the relevant 
		List<Long> orderIds = em.createNativeQuery(query,Long.class)
		.setParameter("busJourneyId", busJourneyId).getResultList();
		
		String query2 = "update order_details\n"
				+ "set payment_status = 'Refunded',total_value = 0 \n"
				+ "where ORDER_ID in :orderIds;";
		
		//update orderid to the relevant 
		em.createNativeQuery(query2)
		.setParameter("orderIds", orderIds)
		.executeUpdate();
		
		String query3 = "delete from bus_journey_details where bus_journey_id=:busJourneyId;";
		em.createNativeQuery(query3)
		.setParameter("busJourneyId", busJourneyId)
		.executeUpdate();
		
	}
	
	
	
}
