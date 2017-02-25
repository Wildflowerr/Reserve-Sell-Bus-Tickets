package com.ticket.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.bus.lines.Bus;

public class TicketServiceImplTest {

	TicketServiceImpl tsi;
	
	@Before
	public void setUp() throws Exception {
		tsi = new TicketServiceImpl();
		tsi.loadTicketsData("/Users/viole_000/Desktop/destination-seats-price.txt");
	}


	@Test
	public void testGetBussesOnDestinations() {
		List<Bus> expected = new ArrayList<Bus>();
		expected.add(new Bus("Plovdiv",48,18));
		expected.add(new Bus("Varna",48,30));
		expected.add(new Bus("Blagoevgrad",33,15));
		expected.add(new Bus("Sofia",30,25));
		expected.add(new Bus("Vidin",22,20));
		expected.add(new Bus("Haskovo",20,30));
		List<Bus> actual = tsi.getBussesOnDestinations();
		assertTrue(expected.size()==actual.size());
		for(int i=0;i<expected.size();i++){
			
			assertEquals(expected.get(i).getDestination(),actual.get(i).getDestination());
			
			assertEquals(expected.get(i).getNumSeats(),actual.get(i).getNumSeats());
			
			assertEquals(expected.get(i).getTicketPrice(),actual.get(i).getTicketPrice());
		}
	}

	@Test
	public void testGetAmountSoldTickets() {
		List<Bus> actual = tsi.getBussesOnDestinations();
		for(Bus bus : actual){
			bus.reserveSeat(12);
			bus.updateCountFreeSeats();
			bus.reserveSeat(13);
			bus.updateCountFreeSeats();
		}
		int expectedAmount = 2*18 + 2*30 + 2*15 + 2*25 + 2*20 + 2*30;
		assertEquals(expectedAmount, tsi.getAmountSoldTickets());
	}


	@Test
	public void testGetBussOnDestination() {
		Bus expected = new Bus("Plovdiv",48,18);
		Bus actual = tsi.getBussOnDestination("Plovdiv");
		assertEquals(expected.getDestination(),actual.getDestination());
		
		assertEquals(expected.getNumSeats(),actual.getNumSeats());
		
		assertEquals(expected.getTicketPrice(),actual.getTicketPrice());
	}

}
