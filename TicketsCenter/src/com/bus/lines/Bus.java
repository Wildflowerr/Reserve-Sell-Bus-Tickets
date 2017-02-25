package com.bus.lines;

import java.util.ArrayList;

public class Bus {
	public static final int ticketBuyersOnDestination = 40;
	private String destination;
	private int numSeats;
	private int countFreeSeats;
	private int countSoldTickets;
	private ArrayList<Seat> seats;
	private int ticketPrice;

	public Bus(String destination, int numSeats, int ticketPrice) {
		super();
		assert (dataValidation(destination, numSeats,ticketPrice));
		this.destination = destination;
		this.numSeats = numSeats;
		this.countFreeSeats = numSeats;
		this.countSoldTickets = 0;
		this.seats = new ArrayList<Seat>();
		for (int i = 0; i < this.numSeats + 1; i++) { // numeration begins from
														// 1 not 0
			Seat current = new Seat(false,Payment.UNPAID);
			seats.add(i, current);
		}
		this.ticketPrice = ticketPrice;

	}

	public Bus() {
		// TODO Auto-generated constructor stub
	}

	public static boolean dataValidation(String destination, int numSeats, int ticketPrice) {
		return isValid(destination) && numSeats > 0 && ticketPrice > 0;
	}

	public static boolean isValid(String destination) {
		return destination.matches("^[a-zA-Z ]+$");
	}
	
	@Override
	public String toString() {
		return "Bus [destination=" + destination + ", numSeats=" + numSeats + ", ticketPrice=" + ticketPrice + "]";
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getNumSeats() {
		return numSeats;
	}

	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
	}

	public int getCountFreeSeats() {
		return countFreeSeats;
	}

	public int getCountSoldTickets() {
		this.countSoldTickets = this.numSeats - this.countFreeSeats;
		return this.countSoldTickets;
	}

	public int estimateProfit() {
		return this.getCountSoldTickets() * this.ticketPrice;
	}

	public void updateCountFreeSeats() {
		--countFreeSeats;
	}

	public ArrayList<Seat> getSeats() {
		return seats;
	}

	public void reserveSeat(int seatNumber) {
		assert(seatNumber>0&&seatNumber<this.numSeats);
		assert(!seats.get(seatNumber).isReserved());
		seats.get(seatNumber).setReserved(true);
	}

	public int getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(int ticketPrice) {
		assert(ticketPrice>0);
		this.ticketPrice = ticketPrice;
	}

	public void printFreeSeats() {
		System.out.print("4.) Destination: " + this.destination + " ");
		if (this.countFreeSeats > 0) {
			System.out.print("free seats are the following sequence: ");
			for (int k = 1; k <= this.numSeats; k++) {
				if (this.seats.get(k).isReserved() == false) {
					System.out.print(k + " ");
				}
			}
			System.out.println("");
		} else {
			System.out.println("all tickets have been bought.");
		}

	}
	
	public void splitAmountDueWayOfSale(){
		int boughtOnline=0;
		int boughtOnDesk=0;
		for(int i=1;i<=this.numSeats;i++){
			if(this.seats.get(i).getP().equals(Payment.ONLINE)){
				++boughtOnline;
			} else if (this.seats.get(i).getP().equals(Payment.ONDESK)){
				++boughtOnDesk;
			}
		}
		System.out.print(", amount of tickets sold online: "+boughtOnline);
		System.out.println(", amount of tickets sold on desk: "+boughtOnDesk);
	}
}
