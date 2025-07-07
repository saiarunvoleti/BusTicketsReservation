package com.example.bus.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetailsDTO {

	@JsonProperty("UserName")
	String userName;
	@JsonProperty("MailAddress")
	@Email(message="Enter proper mail address")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Only gmail.com emails are allowed")
	String mailId;
	@JsonProperty("MobileNumber")
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
	String mobileNumber;
	@JsonProperty("Age")
	int age;
	@JsonProperty("Gender")
	String Gender;
	@JsonProperty("Type")
	String userType;
	@JsonProperty("Password")
	@NotNull
	String password;
	
}
