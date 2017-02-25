package com.ticket.server;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

import com.bus.lines.Bus;
import com.bus.lines.Payment;
import com.ticket.service.TicketService;

public class ThreadServer implements Runnable {
	
	TicketService ticketService;
	Bus bus;
	int seatNumberOrdered;
	String destination;
	boolean ticketOrderCompleted;
	boolean online;
	Object monitor;
	

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    public ThreadServer (Socket socket, TicketService ts) throws IOException
    {
    	this.ticketService = ts;
        this.socket = socket;

    }

    @Override
    public synchronized void run() {

        try
        {

                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());

                if (in.available() > 0) {
                    try {
                    	
                        destination = in.readUTF();
                        seatNumberOrdered = in.readInt();
                        online = in.readBoolean();
                        
                        this.bus = ticketService.getBussOnDestination(destination);
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        
                        
                        if ((!this.ticketOrderCompleted) && bus.getCountFreeSeats()>0 ) {

                            // Here ticket is ordered
                        	ticketOrderCompleted = buyTicket();
                            if(this.ticketOrderCompleted){
                            	try {
        							storeTicketData(ticketService.getFilesPath()+ticketService.getTicketPurchaseStatisticsFileName());
        						} catch (IOException e) {
        							// TODO Auto-generated catch block
        							e.printStackTrace();
        						}
                            	if(this.online){
                            		this.bus.getSeats().get(seatNumberOrdered).setP(Payment.ONLINE);
                            	} else{
                            		this.bus.getSeats().get(seatNumberOrdered).setP(Payment.ONDESK); 
                            	}
                            }
                    }
                        
                        
                        out.println( this.ticketOrderCompleted +" "+seatNumberOrdered);
                        
                        

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        finally
        {
            try {
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
    
    private boolean buyTicket() {

	        if (bus.getCountFreeSeats() > 0) {
	            if (bus.getSeats().get(this.seatNumberOrdered).isReserved() == false) {
	                bus.reserveSeat(this.seatNumberOrdered);// here we buy ticket
	               // System.out.println("Seat ordered: "+this.seatNumberOrdered);
	            } else {
	                int randomIndex = ThreadLocalRandom.current().nextInt(1, bus.getNumSeats() + 1);
	                while (bus.getSeats().get(randomIndex).isReserved() == true) {
	                    randomIndex = ThreadLocalRandom.current().nextInt(1, bus.getNumSeats() + 1);
	                }
	                bus.reserveSeat(randomIndex);
	                this.seatNumberOrdered = randomIndex;
	               // System.out.println("Seat received: "+randomIndex);
	            }
	            bus.updateCountFreeSeats();
	            return true;
	        }
	        
	        return false;

    }
    
    private void storeTicketData(String file) throws IOException{
    	File f = new File(file);
    	if(!f.exists()){
    		f.createNewFile();
    	}
    	try(BufferedWriter writer = new BufferedWriter(new FileWriter(f,true))){
			writer.write("Successful purchase of seat number "+this.seatNumberOrdered+" to "+bus.getDestination()+". Ticket is bought ");
			if(online){
				writer.write("online.");
			} else{
				writer.write("on desk.");
			}
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
