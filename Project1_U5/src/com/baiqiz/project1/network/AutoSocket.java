package com.baiqiz.project1.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.baiqiz.project1.model.Automotive;

public class AutoSocket extends Thread{
	protected BufferedReader reader;
	protected BufferedWriter writer;
	protected ObjectInputStream objectInputStream;
	protected ObjectOutputStream objectOutputStream;
	protected ServerSocket serverSocket;
	protected Socket socket;
	protected Automotive automotive;
	protected final int iPort = 4444;

	public boolean openConnection(String role) {
		try {
			if (role.equals("server")){
				if (serverSocket == null)
					serverSocket = new ServerSocket(iPort);
				socket = serverSocket.accept();
			}
			else
				socket = new Socket("localhost", iPort);
		} catch (IOException socketError) {
			System.err.println("Unable to connect");
			return false;
		}
		try {
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (Exception e) {
			System.err.println("Unable to obtain stream from localhost");
			return false;
		}
		System.err.println("Client connected");
		return true;
	}
	
	public void closeSession() {
		try {
			writer = null;
			reader = null;
			socket.close();
			socket=null;
		} catch (IOException e) {
			System.err.println("Error closing socket ");
		}
		System.err.println("closed");
	}
}
