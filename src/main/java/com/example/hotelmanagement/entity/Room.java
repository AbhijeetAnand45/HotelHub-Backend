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
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public BigDecimal getRoomPrice() {
		return roomPrice;
	}
	public void setRoomPrice(BigDecimal roomPrice) {
		this.roomPrice = roomPrice;
	}
	public String getRoomPhotoUrl() {
		return roomPhotoUrl;
	}
	public void setRoomPhotoUrl(String roomPhotoUrl) {
		this.roomPhotoUrl = roomPhotoUrl;
	}
	public String getRoomDescription() {
		return roomDescription;
	}
	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}
	public List<Booking> getBookings() {
		return bookings;
	}
	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}
	private BigDecimal roomPrice;
	private String roomPhotoUrl;
	private String roomDescription;
	@OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Booking> bookings = new ArrayList<>();
	@Override
	public String toString() {
		return "Room [id=" + id + ", roomType=" + roomType + ", roomPrice=" + roomPrice + ", roomPhotoUrl="
				+ roomPhotoUrl + ", roomDescription=" + roomDescription + "]";
	}
	
	
}
