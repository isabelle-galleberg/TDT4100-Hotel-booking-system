package hotelBookingSystem.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hotelBookingSystem.fxui.HotelBookingFileSupport;

public class FileSupportTest {
	
	private Booking booking = new Booking();
	private HotelBookingFileSupport filesupport = new HotelBookingFileSupport();
	
	@BeforeEach
	public void setup() {
		booking.setName("Knut Knutsen");
		booking.setEmail("knutk@ntnu.no");
		booking.setCheckIn(LocalDate.of(2021, Month.JULY, 4));
		booking.setCheckOut(LocalDate.of(2021, Month.JULY, 10));
		booking.addRoom(new Room("Standard"));
		booking.addRoom(new Room("Single"));
	}
	
	@Test
	public void testLoad() {
		Booking savedBooking; 
		try {
			savedBooking = filesupport.load("booking-test");
		}
		catch (FileNotFoundException e) {
			fail("Could not load saved file");
			return;
		}
		assertEquals(booking.toString(), savedBooking.toString()); 
	}

	//lese fil som ikke finnes
	@Test
	public void testLoadNonExistingFile() {
		assertThrows(
			FileNotFoundException.class, () -> booking = filesupport.load("non-existing-file"), 
			"File does not exist"
		);
	}
	
	//lese fil på feil format
	@Test
	public void testLoadInvalidFile() { 
		assertThrows(
			Exception.class, () -> booking = filesupport.load("invalid-file"), 
			"File is invalid!"
		);
	}
	
	@Test
	public void testSave() {
		try {
			filesupport.save("booking-new", booking);
		} 
		catch (FileNotFoundException e) {
			fail("Could not save file");
		}
		
		byte[] testFile = null, newFile = null;
		
		try {
			testFile = Files.readAllBytes(Path.of(HotelBookingFileSupport.getFilePath("booking-test")));
		} catch (IOException e) {
			fail("Could not load test file");
		}

		try {
			newFile = Files.readAllBytes(Path.of(HotelBookingFileSupport.getFilePath("booking-new")));
		} catch (IOException e) {
			fail("Could not load saved file");
		}
		assertNotNull(testFile);
		assertNotNull(newFile);
		assertTrue(Arrays.equals(testFile, newFile));

	}
	
	//sletter filer som opprettes under testen
	@AfterAll
	static void teardown() {
		File newTestSaveFile = new File(HotelBookingFileSupport.getFilePath("booking-new"));
		newTestSaveFile.delete();
	}
}
