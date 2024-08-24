package com.example.hotelmanagement.dto;

import java.time.LocalDate;

import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.*;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {
	
	private long id;
	
	private LocalDate checkInDate;
	private LocalDate checkOutDate;
	
	private int numOfAdults;
	private int numOfChildren;
	private int totalNumOfGuest;
	private String bookingConfirmationCode;
	
	private UserDTO user;
	private RoomDTO room;
}
