package com.ticket.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.bus.lines.Bus;

class TicketServiceImpl implements TicketService {
	private List<Bus> busses;
	
	private String filesPath;
	private String busDestinationsFileName;
	private String ticketPurchaseStatisticsFileName;
	private String serverHost;
	private int serverPort;
	
	TicketServiceImpl(){
		initialize();
		
		if(busses == null)
		busses = new ArrayList<Bus>();
	}
	

	@Override
	public void initialize() {
		
		Properties prop = new Properties();
		
		try(InputStream in = new FileInputStream(TicketService.CONFIG_FILE_NAME); //getClass().getResourceAsStream(TicketService.CONFIG_FILE_NAME);
				) {
			prop.load(in);
			this.filesPath = prop.getProperty("file_path");
			this.busDestinationsFileName = prop.getProperty("bus_destinations_file_name");
			this.ticketPurchaseStatisticsFileName = prop.getProperty("ticket_purchase_statistics_file_name");
			this.serverHost = prop.getProperty("host");
			this.serverPort = Integer.parseInt( prop.getProperty("port") );
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			this.filesPath = TicketService.DEFAULT_FILES_PATH;
			this.busDestinationsFileName = TicketService.DEFAULT_DESTINATION_FILE;
			this.ticketPurchaseStatisticsFileName = TicketService.DEFAULT_TICKET_PURCHASE_STATISTICS_FILE_NAME;
			this.serverHost = TicketService.DEFAULT_HOST;
			this.serverPort = TicketService.DEFAULT_PORT;
		}
	}

	public String getFilesPath() {
		return filesPath;
	}


	public String getBusDestinationsFileName() {
		return busDestinationsFileName;
	}


	public String getTicketPurchaseStatisticsFileName() {
		return ticketPurchaseStatisticsFileName;
	}


	public String getServerHost() {
		return serverHost;
	}


	public int getServerPort() {
		return serverPort;
	}


	@Override
	public void loadTicketsData(String file) throws FileNotFoundException {
		
		String line;
		try(BufferedReader br = new BufferedReader(new FileReader(file))){
			while ((line = br.readLine()) != null) {
				String[] data = line.split("-");
				busses.add(new Bus(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2])));
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public List<Bus> getBussesOnDestinations() {
		return busses;
	}

	@Override
	public int getAmountSoldTickets() {
		int amount=0;
		for(Bus bus : busses ){
			amount+=bus.estimateProfit();
		}
		return amount;
	}

	@Override
	public void numberOfSoldTicketsPerLine() {
		for(Bus bus : busses){
			System.out.println("2.) Tickets sold for "+bus.getDestination()+" are "+bus.getCountSoldTickets());
		}
		
	}

	@Override
	public void splitDuePayment() {
		for(Bus bus : busses){
			System.out.print("3.) Destination: "+bus.getDestination());
			bus.splitAmountDueWayOfSale();
		}
		
	}

	@Override
	public void freeSeatsPerLine() {
		for(Bus bus : busses){
			bus.printFreeSeats();
		}
		
	}

	@Override
	public Bus getBussOnDestination(String destination) {
		for(int i =0; i<busses.size();i++){
			if(busses.get(i).getDestination().equals(destination)) return busses.get(i);
		}
		
		return null;
	}


}
