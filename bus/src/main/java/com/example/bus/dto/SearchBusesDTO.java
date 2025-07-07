package com.example.bus.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchBusesDTO {

	@JsonProperty("FromStation")
	@NotNull
	String fromStation;
	
	@JsonProperty("ToStation")
	@NotNull
	String toStation;
	
	@JsonProperty("Date")
	@NotNull
	String date;
	
	@JsonProperty("IsAcBus")
	String isACBus;
	
}
