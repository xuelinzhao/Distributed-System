package com.main;

import java.util.LinkedList;

import com.tcp.TCPClient;
import com.tcp.TCPServer;

public class Main {
	private final static int serverNums = 6;
	private final static int clientNums = 22;
	private static LinkedList<TCPServer> servers;
	private static LinkedList<TCPClient> clients;
	
	
	
	public static void main(String[] args) {
		
		servers = new LinkedList<TCPServer>();
		
		for (int i = 0; i < serverNums; i++) {
			servers.add(new TCPServer("sever"+ i,7889+i));
		}
		
		for(TCPServer server: servers){
			server.start();
		}
		
		clients = new LinkedList<TCPClient>();
		
		for (int i = 0; i < clientNums; i++) {
			clients.add(new TCPClient("client"+ i));
		}
		
		for(TCPClient client: clients){
			client.start();
		}
	}
}
