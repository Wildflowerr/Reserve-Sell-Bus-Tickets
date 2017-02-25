package com.ticket.service;

public class TicketServiceFactory {

	public TicketService getTicketService(){
		return new TicketServiceImpl();
	}
}
