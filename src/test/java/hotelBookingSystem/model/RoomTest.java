package hotelBookingSystem.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoomTest {

	private Room room;
	
	@BeforeEach
	public void setUp() {
		room = new Room("Standard");
	}
	
	@Test
	public void testConstructor() {
		assertEquals(room.getRoomType(), "Standard");
		
		assertThrows(IllegalArgumentException.class, () -> {
			room = new Room(null);
		}, "Must select type of room");
		
		assertThrows(IllegalArgumentException.class, () -> {
			room = new Room("Comfort+");
		}, "Invalid type of room");
		
	}
	
	@Test
	public void testSetRoomType() {
		room.setRoomType("Single");
		assertEquals(room.getRoomType(), "Single");
		
		assertThrows(IllegalArgumentException.class, () -> {
			room.setRoomType(null);
		}, "Must select type of room");
		
		assertThrows(IllegalArgumentException.class, () -> {
			room.setRoomType("Comfort+");
		}, "Invalid type of room");
		
	}
	
	@Test
	public void testRoomPrice() {
		assertEquals(Room.getRoomPrice(room.getRoomType()), 800); 
		room.setRoomType("Single");
		assertEquals(Room.getRoomPrice(room.getRoomType()), 600); 
		room.setRoomType("Family");
		assertEquals(Room.getRoomPrice(room.getRoomType()), 1200); 
		room.setRoomType("Suite");
		assertEquals(Room.getRoomPrice(room.getRoomType()), 2000); 
		
		assertThrows(IllegalArgumentException.class, () -> {
			Room.getRoomPrice("Comfort+");
		}, "Invalid type of room");

	}
}
