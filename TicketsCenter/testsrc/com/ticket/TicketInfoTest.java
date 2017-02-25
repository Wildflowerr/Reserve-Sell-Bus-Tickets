package com.ticket;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import com.bus.lines.*;

public class TicketInfoTest {
    TicketInfo ti;
    Bus bus;
	@Before
	public void setUp() throws Exception {
	    bus = new Bus("Plovdiv",48,18);
		ti = new TicketInfo(bus,13,false,false);
	}

	@Test
	public void testGetBus() {
		assertEquals(bus,ti.getBus());
	}

	@Test
	public void testGetDestination() {
		assertEquals("Plovdiv",ti.getDestination());
	}

	@Test
	public void testGetSeatNumberOrdered() {
		assertEquals(13,ti.getSeatNumberOrdered());
	}

	@Test
	public void testIsTicketOrderCompleted() {
		assertFalse(ti.isTicketOrderCompleted());
	}

	@Test
	public void testIsOnline() {
		assertFalse(ti.isOnline());
	}

}
