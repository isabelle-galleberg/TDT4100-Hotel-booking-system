package hotelBookingSystem.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookingTest {
	
	private Booking booking;
	private Room room1, room2, room3, room4;
	

	
	@BeforeEach
	public void setUp() {
		booking = new Booking();
		room1 = new Room("Standard");
		room2 = new Room("Single");
		room3 = new Room("Family");
		room4 = new Room("Suite");
	}
	
	@Test
	public void testName() {
		booking.setName("Ola");
		assertEquals(booking.getName(), "Ola");
		assertThrows(IllegalArgumentException.class, () -> {
			booking.setName("");
		}, "Must enter name");
	}
	
	@Test
	private void testInvalidEmail(String invalidEmail, String existingEmail) {
		assertThrows(IllegalArgumentException.class, () -> {
			booking.setEmail(invalidEmail);
		}, "Invalid email");
	}
	
	@Test
	public void testEmail() {
		booking.setEmail("username@domain.com");
		assertEquals(booking.getEmail(), "username@domain.com");
		
		testInvalidEmail("username", booking.getEmail()); //uten @ 
		testInvalidEmail("user@name@domain.com", booking.getEmail()); //flere @
		testInvalidEmail("username@.com", booking.getEmail()); //mangler domene
		testInvalidEmail(".username@domain.com", booking.getEmail()); //begynner med .
		testInvalidEmail("username.@domain.com", booking.getEmail()); //brukernavn slutter med .
		testInvalidEmail("user..name@domain.com", booking.getEmail()); //flere . i brukernavn
		testInvalidEmail("username@domain..com", booking.getEmail()); //flere . i domene
		testInvalidEmail("username@domain.com.", booking.getEmail()); //slutter med .
		testInvalidEmail("username@domain.c", booking.getEmail()); //cTRLD mindre enn 2 tegn
		testInvalidEmail("username.@domain.com", booking.getEmail()); //navn slutter med .
		testInvalidEmail("user.name@domain", booking.getEmail()); //mangler cTRLD
		testInvalidEmail("user!+name@domain.com", booking.getEmail()); //ugyldige tegn: !, +
		testInvalidEmail("usernamexxxxxxxxxxxxxxxxxxxxxxx@domain.com", booking.getEmail()); //brukernavn lenger enn 30 tegn
	}
	
	@Test
	public void testCheckInDate() {
		LocalDate future = LocalDate.now().plusDays(1L);
		LocalDate past = LocalDate.now().minusDays(1L);
		
		booking.setCheckIn(future);
		assertEquals(future, booking.getCheckIn());
		
		assertThrows(IllegalArgumentException.class, () -> {
			booking.setCheckIn(past);
		}, "Check in date cannot be in the past");
		
	}
	
	@Test
	public void testCheckOutDate() {
		LocalDate futureDay1 = LocalDate.now().plusDays(1L);
		LocalDate futureDay2 = LocalDate.now().plusDays(2L);
		LocalDate past = LocalDate.now().minusDays(1L);
		
		booking.setCheckIn(futureDay1);
		booking.setCheckOut(futureDay2);
		assertEquals(futureDay2, booking.getCheckOut());
		
		
		assertThrows(IllegalArgumentException.class, () -> {
			booking.setCheckOut(past);
		}, "Check out date cannot be in the past");
		

		assertThrows(IllegalArgumentException.class, () -> {
			booking.setCheckIn(futureDay1);
			booking.setCheckOut(futureDay1);
		}, "Check out and check in cannot be on the same date");
		
		assertThrows(IllegalArgumentException.class, () -> {
			booking.setCheckIn(futureDay2);
			booking.setCheckOut(futureDay1);
		}, "Check out date cannot be before check in date");
		
	}
	
	@Test 
	public void testRoomBooking() {
		assertEquals(booking.getBookedRooms(), new ArrayList<Room>());
		
		booking.addRoom(room1);
		assertEquals(booking.getBookedRooms(), Arrays.asList(room1));
		assertEquals(booking.getNumberOfBookedRooms(), 1);
		
		booking.addRoom(room2);
		assertEquals(booking.getBookedRooms(), Arrays.asList(room1, room2));
		assertEquals(booking.getNumberOfBookedRooms(), 2);
		
		booking.removeRoom(room1);
		assertEquals(booking.getBookedRooms(), Arrays.asList(room2));
		assertEquals(booking.getNumberOfBookedRooms(), 1);
		
		assertThrows(IllegalArgumentException.class, () -> {
			booking.removeRoom(room3);
		}, "Cannot remove room that is not booked");
	}
	
	@Test
	public void testRoomPrice() {
		booking.addRoom(room1);
		booking.addRoom(room2);
		booking.addRoom(room3);
		booking.addRoom(room4);
		LocalDate checkIn = LocalDate.now().plusDays(2L);
		LocalDate checkOut = LocalDate.now().plusDays(5L);
	
		assertEquals(booking.getTotalPrice(checkIn, checkOut), 13800);
		
		assertThrows(IllegalArgumentException.class, () -> {
			booking.setTotalPrice(-1);
		}, "Price cannot be negative");
		
	}
	
}
