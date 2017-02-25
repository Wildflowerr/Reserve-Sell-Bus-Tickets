package com.ticket;

import com.bus.lines.Bus;

public class TicketInfo {
	private Bus bus;
    private int seatNumberOrdered;
    private boolean ticketOrderCompleted;
    private boolean online;
    
    
    public TicketInfo(Bus bus, int seatNumberOrdered, boolean ticketOrderCompleted, boolean online) {
		super();
		this.bus = bus;
		this.seatNumberOrdered = seatNumberOrdered;
		this.ticketOrderCompleted = ticketOrderCompleted;
		this.online = online;
	}

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	public String getDestination() {
        return bus.getDestination();
    }

    public int getSeatNumberOrdered() {
        return seatNumberOrdered;
    }

    public void setSeatNumberOrdered(int seatNumberOrdered) {
        this.seatNumberOrdered = seatNumberOrdered;
    }

    public boolean isTicketOrderCompleted() {
        return ticketOrderCompleted;
    }

    public void setTicketOrderCompleted(boolean ticketOrderCompleted) {
        this.ticketOrderCompleted = ticketOrderCompleted;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
    
    
}
