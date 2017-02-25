package com.bus.lines;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;



public class BusTest {

	private Bus classUnderTest;
	
	@Before
	public void setUp() throws Exception{
		this.classUnderTest = new Bus("Plovdiv",48,18);
		classUnderTest.reserveSeat(12);
		classUnderTest.updateCountFreeSeats();
		classUnderTest.getSeats().get(12).setP(Payment.ONLINE);
		classUnderTest.reserveSeat(11);
		classUnderTest.updateCountFreeSeats();
		classUnderTest.getSeats().get(11).setP(Payment.ONDESK);
	}
	@Test
	public void testDataValidation() {
		assertEquals(true, Bus.dataValidation("Plovdiv", 48, 18));
		assertEquals(false, Bus.dataValidation("Plovdiv123", 48, 18));
		assertEquals(false, Bus.dataValidation("Plovdiv", -48, 18));
		assertEquals(false, Bus.dataValidation("Plovdiv123", 48, -18));
	}

	@Test
	public void testIsValid() {
		assertFalse(Bus.isValid("Plovdiv123"));
		assertTrue(Bus.isValid("Stara Zagora"));
		assertTrue(Bus.isValid("Plovdiv"));
	}
	@Test
	public void testGetCountFreeSeats() {
		int result = 46;
		assertEquals(result, classUnderTest.getCountFreeSeats());
	}

	@Test
	public void testGetCountSoldTickets() {
		int result = 2;
		assertEquals(result, classUnderTest.getCountSoldTickets());
	}

	@Test
	public void testEstimateProfit() {
		int result = 36;
		assertEquals(result, classUnderTest.estimateProfit());
	}

	@Test
	public void testGetDestination(){
		assertEquals("Plovdiv", classUnderTest.getDestination());
	}
	
	@Test
	public void testGetNumSeats(){
		assertEquals(48, classUnderTest.getNumSeats());
	}
	
	@Test
	public void testGetTicketPrice(){
		assertEquals(18, classUnderTest.getTicketPrice());
	}
}
