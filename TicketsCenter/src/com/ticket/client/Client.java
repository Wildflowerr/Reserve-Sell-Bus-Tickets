package com.ticket.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.bus.lines.Payment;
import com.ticket.TicketInfo;

public class Client {

    private TicketInfo ticketInfo;

    public Client(TicketInfo ti, final String host, final int port) throws IOException
    {	
    	this.ticketInfo = ti;
        try(Socket s = new Socket(host, port);
        	DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        	BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));)
        {
            
            dos.writeUTF(ticketInfo.getDestination());
            dos.writeInt(ticketInfo.getSeatNumberOrdered());
            dos.writeBoolean(ticketInfo.isOnline());
            
            dos.flush();
            
            String answer = input.readLine();
            
            String[] parts = answer.split(" ");
            
            ticketInfo.setTicketOrderCompleted(Boolean.parseBoolean(parts[0]));
            if(ticketInfo.isTicketOrderCompleted()){
            	ticketInfo.getBus().reserveSeat(Integer.parseInt( parts[1] ));
            	ticketInfo.getBus().updateCountFreeSeats();	
            }
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
    }



    public TicketInfo getTicketInfo() {
		return ticketInfo;
	}

}
