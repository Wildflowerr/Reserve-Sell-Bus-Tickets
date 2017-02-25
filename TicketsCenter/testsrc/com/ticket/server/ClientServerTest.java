package com.ticket.server;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

import com.bus.lines.Bus;
import com.ticket.TicketInfo;
import com.ticket.client.Client;
import com.ticket.service.TicketService;
import com.ticket.service.TicketServiceFactory;

 
public class ClientServerTest {

	@Test
	public void test() throws IOException{
		
		ServerRunnable sr = new ServerRunnable();
	    Thread serverThread = new Thread(sr);
	    serverThread.start();	
		
		Bus bus = new Bus("Plovdiv",48, 18);
		TicketInfo ti = new TicketInfo(bus, 12, false, true);
		Client cl = new Client(ti, "localhost", 9090);
		assertTrue(cl.getTicketInfo().isTicketOrderCompleted());
	
	}
}

 
class ServerRunnable implements Runnable {
    private ServerSocket serverSocket;
    private TicketService ticketService;

    public ServerRunnable() throws IOException {
        
        TicketServiceFactory factory = new TicketServiceFactory();
        ticketService = factory.getTicketService();
        ticketService.loadTicketsData(ticketService.getFilesPath()+ticketService.getBusDestinationsFileName());
        
        // listen on any free port
        serverSocket = new ServerSocket(ticketService.getServerPort());
    }

    public void run() {
        try {
            while (true) {
                Socket socket = serverSocket.accept();

                ThreadServer ts = new ThreadServer(socket, ticketService);
                Thread t1 = new Thread(ts);
                t1.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
	