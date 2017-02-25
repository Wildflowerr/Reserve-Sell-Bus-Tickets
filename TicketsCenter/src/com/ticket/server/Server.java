package com.ticket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import com.bus.lines.Bus;
import com.ticket.service.TicketService;
import com.ticket.service.TicketServiceFactory;

public class Server extends Thread {
	
	TicketService ticketService;
	
    private ServerSocket listener;
    int socketCounter = 0;

    public Server() throws IOException
    {
    	
    	TicketServiceFactory factory = new TicketServiceFactory();

        ticketService = factory.getTicketService();

        ticketService.loadTicketsData(ticketService.getFilesPath()+ticketService.getBusDestinationsFileName());
        
       
    	
        try
        {
            listener = new ServerSocket( ticketService.getServerPort() );

            while (true)
            {
                Socket socket = listener.accept();
                socketCounter++;
                
                ThreadServer ts = new ThreadServer(socket, ticketService);
                Thread t1 = new Thread(ts);
                t1.start();
                
                if(socketCounter >= ticketService.getBussesOnDestinations().size() * Bus.ticketBuyersOnDestination ){
                	
                 	   System.out.println("1.) Amount of money from sold tickets: "+ticketService.getAmountSoldTickets());
                 	   ticketService.numberOfSoldTicketsPerLine();
                 	   ticketService.splitDuePayment();
                 	   ticketService.freeSeatsPerLine();
                 	   break;
                }
            }
        }
        finally
        {
            listener.close();
        }

    }

	public static void main(String[] args) throws IOException
    {
        new Server();
    }
}
