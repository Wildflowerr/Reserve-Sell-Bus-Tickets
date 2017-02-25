package com.ticket.simulator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

import com.bus.lines.Bus;
import com.ticket.TicketInfo;
import com.ticket.client.Client;
import com.ticket.service.TicketService;
import com.ticket.service.TicketServiceFactory;

public class ClientPurchaseSimulator {

	public static void main(String[] args) throws IOException {
        
    	TicketServiceFactory factory = new TicketServiceFactory();
        //creating an Anonymous object
        TicketService ticketService = factory.getTicketService();

        ticketService.loadTicketsData(ticketService.getFilesPath()+ticketService.getBusDestinationsFileName());

        List<TicketInfo> onlineBuyers = new ArrayList<TicketInfo>();
        Queue<TicketInfo> onDesk = new ConcurrentLinkedQueue<TicketInfo>();
        Object monitor = new Object();

        for (int index = 0; index < ticketService.getBussesOnDestinations().size(); index++) {
            Bus bus = ticketService.getBussesOnDestinations().get(index);
            for (int index2 = 0; index2 < Bus.ticketBuyersOnDestination; index2++) {
                // assume that there are 40 candidates to travel in each bus!

                int seatNumber = ThreadLocalRandom.current().nextInt(1, bus.getNumSeats() + 1);
                boolean online  = index2 < 10 ? false : true;
                TicketInfo ticketInfo = new TicketInfo(bus, seatNumber, false, online);
                if(index2 >= 10){
                    onlineBuyers.add(ticketInfo);
                }
                else{
                    onDesk.add(ticketInfo);
                }
            }
        }

        
        // START ORDERING TICKETS
        
        //BUY ON DESK
        Thread saleOndesk = new Thread( new Runnable() {
            @Override
            public void run() {
                while(onDesk.size()>0){

                    long ls = System.currentTimeMillis();

                    TicketInfo ti = onDesk.poll();

                    long lf = ls + 1000;

                    try {
						new Client(ti, ticketService.getServerHost(), ticketService.getServerPort());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    
                    
                    if(ti.getBus().getCountFreeSeats() > 0){
                        while(System.currentTimeMillis() <= lf){
                            synchronized (monitor) {
                                monitor.notifyAll();
                            }
                        }
                    }
                }
            }
        });
        //END BUY ON DESK


        //BUY ONLINE
        Thread saleOnline = new Thread( new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < onlineBuyers.size(); i++) {

                    long ls = System.currentTimeMillis();

                    TicketInfo ti = onlineBuyers.get(i);

                    long lf = ls + 200;

                    try {
						new Client(ti, ticketService.getServerHost(), ticketService.getServerPort());
					} catch (IOException e) {
						e.printStackTrace();
					}
                    
                    
                    if(ti.getBus().getCountFreeSeats() > 0){
                        while(System.currentTimeMillis() <= lf){
                            synchronized (monitor) {
                                monitor.notifyAll();
                            }
                        }
                    }
                }
            }
        });
        //END BUY ONLINE


        saleOnline.start();
        saleOndesk.start();
    	
    }

}
