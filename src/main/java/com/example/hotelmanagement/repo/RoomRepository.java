package com.example.hotelmanagement.repo;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.hotelmanagement.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
	
	@Query("SELECT DISTINCT r.roomType FROM Room r")
	List<String> finDistinctRoomTypes();
	
	@Query("select r from Room r where r.roomType LIKE %:roomType% AND r.id NOT IN (select bk.room.id from Booking bk where "
			+ "(bk.checkInDate <= :checkOutDate) AND (bk.checkOutDate >= :checkInDate))")
	List<Room> findAvailableRoomsByDatesAndTypes(LocalDate checkInDate, LocalDate checkOutDate, String roomType);
	
	@Query("select r from Room r where r.id NOT IN(select b.room.id from Booking b)")
	List<Room> getAllAvailableRooms();
}
