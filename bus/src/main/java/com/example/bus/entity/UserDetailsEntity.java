package com.example.bus.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "USER_DETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="USER_ID")
	Long userId;
	@Column(name="USER_NAME")
	String userName;
	@Column(name="MAIL_ID")
	@Email(message="Enter proper mail address")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Only gmail.com emails are allowed")
	String mailId;
	@Column(name="MOBILE_NUMBER")
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
	String mobileNumber;
	@Column(name="AGE")
	int age;
	@Column(name="GENDER")
	String Gender;
	@Column(name="USER_ROLE")
	String userType;
	@Column(name="PASSWORD")
	String password;
	@OneToOne(mappedBy = "userDetails", cascade = CascadeType.ALL)
	private TravellerDetailsEntity traveller;
	
	//one to many mapping with orderDetails
	@OneToMany(mappedBy="userDetails" , cascade = CascadeType.ALL)
	private List<OrderDetailsEntity> orderDetailsList;	
	
}
