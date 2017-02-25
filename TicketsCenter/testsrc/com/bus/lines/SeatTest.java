package com.bus.lines;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SeatTest {

	private Seat classUnderTest;
	@Before
	public void setUp() throws Exception {
		classUnderTest = new Seat(true,Payment.ONLINE);
	}

	@Test
	public void testIsReserved() {
		assertEquals(true,classUnderTest.isReserved());
	}

	@Test
	public void testGetP() {
		assertEquals(Payment.ONLINE,classUnderTest.getP());
	}

}
