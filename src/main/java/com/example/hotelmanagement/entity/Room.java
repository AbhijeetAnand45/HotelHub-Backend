package com.example.hotelmanagement.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="rooms")
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String roomType;
	private BigDecimal roomPrice;
	private String roomPhotoUrl;
	private String roomDescription;
	private List<Booking> bookings = new ArrayList<>();
	@Override
	public String toString() {
		return "Room [id=" + id + ", roomType=" + roomType + ", roomPrice=" + roomPrice + ", roomPhotoUrl="
				+ roomPhotoUrl + ", roomDescription=" + roomDescription + "]";
	}
	
	
}
