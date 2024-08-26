package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.dto.Response;
import com.example.hotelmanagement.dto.RoomDTO;
import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.exception.HandleException;
import com.example.hotelmanagement.repo.BookingRepository;
import com.example.hotelmanagement.repo.RoomRepository;
import com.example.hotelmanagement.service.interfac.IRoomService;
import com.example.hotelmanagement.utils.Utils;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService implements IRoomService {

	@Autowired
	private RoomRepository roomRepository;
//	@Autowired
//	private BookingRepository bookingRepository;

//    @Override
//    public Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {
//        Response response = new Response();
//
//        try {
//            String imageUrl = awsS3Service.saveImageToS3(photo);
//            Room room = new Room();
//            room.setRoomPhotHandlel(imageUrl);
//            room.setRoomType(roomType);
//            room.setRoomPrice(roomPrice);
//            room.setRoomDescription(description);
//            Room savedRoom = roomRepository.save(room);
//            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(savedRoom);
//            response.setStatusCode(200);
//            response.setMessage("successful");
//            response.setRoom(roomDTO);
//
//        } catch (Exception e) {
//            response.setStatusCode(500);
//            response.setMessage("Error saving a room " + e.getMessage());
//        }
//        return response;
//    }
	
//  @Override
//  public Response updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice, MultipartFile photo) {
//      Response response = new Response();
//
//      try {
//          String imageUrl = null;
//          if (photo != null && !photo.isEmpty()) {
//              imageUrl = awsS3Service.saveImageToS3(photo);
//          }
//          Room room = roomRepository.findById(roomId).orElseThrow(() -> new HandleException("Room Not Found"));
//          if (roomType != null) room.setRoomType(roomType);
//          if (roomPrice != null) room.setRoomPrice(roomPrice);
//          if (description != null) room.setRoomDescription(description);
//          if (imageUrl != null) room.setRoomPhotHandlel(imageUrl);
//
//          Room updatedRoom = roomRepository.save(room);
//          RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(updatedRoom);
//
//          response.setStatusCode(200);
//          response.setMessage("successful");
//          response.setRoom(roomDTO);
//
//      } catch (HandleException e) {
//          response.setStatusCode(404);
//          response.setMessage(e.getMessage());
//      } catch (Exception e) {
//          response.setStatusCode(500);
//          response.setMessage("Error saving a room " + e.getMessage());
//      }
//      return response;
//  }

	@Override
	public List<String> getAllRoomTypes() {
		return roomRepository.findDistinctRoomTypes();
	}

	@Override
	public Response getAllRooms() {
		Response response = new Response();

		try {
			List<Room> roomList = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
			List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);
			response.setStatusCode(200);
			response.setMessage("successful");
			response.setRoomList(roomDTOList);

		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error saving a room " + e.getMessage());
		}
		return response;
	}

	@Value("${image.storage.path}")
	private String IMAGE_DIRECTORY;

	@PostConstruct
	public void init() {
	    try {
	        Files.createDirectories(Paths.get(IMAGE_DIRECTORY));
	    } catch (Exception e) {
	        throw new RuntimeException("Could not create image directory", e);
	    }
	}

	public Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {
		Response response = new Response();

		try {
			String imageUrl = saveImageLocally(photo);
			Room room = new Room();
			room.setRoomPhotoUrl(imageUrl);
			room.setRoomType(roomType);
			room.setRoomPrice(roomPrice);
			room.setRoomDescription(description);
			Room savedRoom = roomRepository.save(room);
			RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(savedRoom);
			response.setStatusCode(200);
			response.setMessage("successful");
			response.setRoom(roomDTO);

		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error saving a room " + e.getMessage());
		}
		return response;
	}

	@Override
	public Response updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice,
			MultipartFile photo) {
		Response response = new Response();

		try {
			String imageUrl = null;
			if (photo != null && !photo.isEmpty()) {
				imageUrl = saveImageLocally(photo);
			}
			Room room = roomRepository.findById(roomId).orElseThrow(() -> new HandleException("Room Not Found"));
			if (roomType != null)
				room.setRoomType(roomType);
			if (roomPrice != null)
				room.setRoomPrice(roomPrice);
			if (description != null)
				room.setRoomDescription(description);
			if (imageUrl != null)
				room.setRoomPhotoUrl(imageUrl);

			Room updatedRoom = roomRepository.save(room);
			RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(updatedRoom);

			response.setStatusCode(200);
			response.setMessage("successful");
			response.setRoom(roomDTO);

		} catch (HandleException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error updating the room " + e.getMessage());
		}
		return response;
	}

	// Helper method to save image locally
	private String saveImageLocally(MultipartFile photo) throws IOException {
		if (photo == null || photo.isEmpty()) {
			return null;
		}

		// Generate a unique filename
		String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();

		// Create the file path
		Path filePath = Paths.get(IMAGE_DIRECTORY, fileName);

		// Save the file locally
		Files.write(filePath, photo.getBytes());

		// Return the file path as a string
		return filePath.toString();
	}

	@Override
	public Response deleteRoom(Long roomId) {
		Response response = new Response();

		try {
			roomRepository.findById(roomId).orElseThrow(() -> new HandleException("Room Not Found"));
			roomRepository.deleteById(roomId);
			response.setStatusCode(200);
			response.setMessage("successful");

		} catch (HandleException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error saving a room " + e.getMessage());
		}
		return response;
	}

	@Override
	public Response getRoomById(Long roomId) {
		Response response = new Response();

		try {
			Room room = roomRepository.findById(roomId).orElseThrow(() -> new HandleException("Room Not Found"));
			RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTOPlusBookings(room);
			response.setStatusCode(200);
			response.setMessage("successful");
			response.setRoom(roomDTO);

		} catch (HandleException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error saving a room " + e.getMessage());
		}
		return response;
	}

	@Override
	public Response getAvailableRoomsByDataAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
		Response response = new Response();

		try {
			List<Room> availableRooms = roomRepository.findAvailableRoomsByDatesAndTypes(checkInDate, checkOutDate,
					roomType);
			List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(availableRooms);
			response.setStatusCode(200);
			response.setMessage("successful");
			response.setRoomList(roomDTOList);

		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error saving a room " + e.getMessage());
		}
		return response;
	}

	@Override
	public Response getAllAvailableRooms() {
		Response response = new Response();

		try {
			List<Room> roomList = roomRepository.getAllAvailableRooms();
			List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);
			response.setStatusCode(200);
			response.setMessage("successful");
			response.setRoomList(roomDTOList);

		} catch (HandleException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error saving a room " + e.getMessage());
		}
		return response;
	}
}
