package com.tcp;

import java.io.*;
import java.net.*;

public class TCPServer implements Runnable{
	
	private String threadName;
	private int serverPort;
	private Thread t;
	private int connectionNum;
	
	public TCPServer(String threadName,int serverPort){
		this.threadName = threadName;
		this.serverPort = serverPort;
	}
	
	public int getConnectionNum(){
		return connectionNum;
	}
	   
	   public void start () {
	      System.out.println("Starting " +  threadName );
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	   }

	@Override
	public void run() {
		try {
			ServerSocket listenSocket = new ServerSocket(serverPort);
			int i = 0;
			while (true) {
				System.out.println("Server listening for a connection");
				Socket clientSocket = listenSocket.accept();
				i++;
				connectionNum = i;
				System.out.println(threadName +"Received connection " + i);
				Connection c = new Connection(clientSocket);
			}
		} catch (IOException e) {
			System.out.println("Listen socket:" + e.getMessage());
		}		
	}
}

class Connection extends Thread {
	DataInputStream in;
	DataOutputStream out;
	Socket clientSocket;

	public Connection(Socket aClientSocket) {
		try {
			clientSocket = aClientSocket;
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
			this.start();
		} catch (IOException e) {
			System.out.println("Connection:" + e.getMessage());
		}
	}

	public void run() {
		try { // an echo server
			String data = in.readUTF(); // read a line of data from the stream
			out.writeUTF(data);
		} catch (EOFException e) {
			System.out.println("EOF:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("readline:" + e.getMessage());
		} finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				/* close failed */}
		}
	}
}