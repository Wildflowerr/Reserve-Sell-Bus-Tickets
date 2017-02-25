package com.bus.lines;

public enum Payment {
 ONLINE("Online"), ONDESK("On desk"), UNPAID("unpaid");
	
	private final String payment;
	private Payment(String payment){
		this.payment = payment;
	}
	
	public String getPayment(){
		return payment;
	}
}
