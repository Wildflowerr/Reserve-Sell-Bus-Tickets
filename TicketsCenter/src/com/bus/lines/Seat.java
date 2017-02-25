package com.bus.lines;

 public class Seat {
	private boolean reserved;
	private Payment p; 

	public Seat(boolean reserved, Payment p) {
		super();
		this.reserved = reserved;
		this.p = p;
	}

	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

	public void setP(Payment p) {
		this.p = p;
	}

	public Payment getP() {
		return p;
	}

}
