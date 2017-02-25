package com.ticket.service;

import java.io.FileNotFoundException;
import java.util.List;

import com.bus.lines.Bus;

public interface TicketService {

	public static final String CONFIG_FILE_NAME = "/Users/viole_000/Desktop/TicketCenterConfig.properties";
	
	public static final String DEFAULT_FILES_PATH = "/Users/viole_000/Desktop/";
	public static final String DEFAULT_HOST = "localhost";
	public static final int    DEFAULT_PORT = 9090;
	public static final String DEFAULT_DESTINATION_FILE = "destination-seats-price.txt";
	public static final String DEFAULT_TICKET_PURCHASE_STATISTICS_FILE_NAME = "ticketPurchaseStatistics.txt";
	
	public void initialize();
	public String getFilesPath();
	public String getBusDestinationsFileName();
	public String getTicketPurchaseStatisticsFileName();
	public String getServerHost();
	public int getServerPort();
	
	public void loadTicketsData(String file) throws FileNotFoundException;

	public List<Bus> getBussesOnDestinations();

	public Bus getBussOnDestination(String destination);

	public int getAmountSoldTickets();
	
	public void numberOfSoldTicketsPerLine();
	
	public void splitDuePayment();
	
	public void freeSeatsPerLine();
}
