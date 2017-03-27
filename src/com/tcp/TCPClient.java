package com.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient implements Runnable {

	static {
		serverPort = 7889;
	}

	private String threadName;
	private static int serverPort;
	private Thread t;

	public TCPClient(String threadName){
		this.threadName = threadName;
	}
	
	
	public void start() {
		System.out.println("Starting " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}

	@Override
	public void run() {
		// arguments supply message and hostname
		Socket s = null;
		try {
			serverPort++;
			if(serverPort  > 7894){
				serverPort = 7889;
			}
			s = new Socket("127.0.0.1", serverPort);
			System.out.println("Connection Established");
			DataInputStream in = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			System.out.println("Sending data");
			out.writeUTF(threadName); // UTF is a string encoding see Sn. 4.4
			String data = in.readUTF(); // read a line of data from the stream
			System.out.println("Received: " + data);
		} catch (UnknownHostException e) {
			System.out.println("Socket:" + e.getMessage());
		} catch (EOFException e) {
			System.out.println("EOF:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("readline:" + e.getMessage());
		} finally {
			if (s != null)
				try {
					s.close();
				} catch (IOException e) {
					System.out.println("close:" + e.getMessage());
				}
		}

	}

}