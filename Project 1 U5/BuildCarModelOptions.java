package com.baiqiz.project1.server;

import java.net.*;

import com.baiqiz.project1.adapter.AutoServer;
import com.baiqiz.project1.adapter.BuildAuto;
import com.baiqiz.project1.model.Automotive;

import java.io.*;

public class BuildCarModelOptions extends Thread implements AutoServer{
	private BufferedReader reader;
	private BufferedWriter writer;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	private Socket socket;
	private Automotive automotive;
	private final int iPort = 4444;

	public void run() {
		if (!openConnection())
			return;
		while (true) {
			try {
				String strInput = null;
				while ((strInput = reader.readLine()) != null) {
					System.out.println("Client:" + strInput);
					break;
				}
				// Connection shutdown
				if (strInput == null)
					break;
				sendString("OK");
				if (strInput.equals("Method1")) {
					while ((automotive = (Automotive) objectInputStream.readObject()) != null) {
						System.out.println("Client:" + automotive);
						insertAutoIntoHashMap(automotive);
						break;
					}
				}
				if (strInput.equals("Method2")) {
					sendString(getAutoList());
					sendString("__EOF");
				}

			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		closeSession();
	}

	@SuppressWarnings("resource")
	public boolean openConnection() {
		try {
			socket = new ServerSocket(iPort).accept();
		} catch (IOException socketError) {
			System.err.println("Server: Unable to connect");
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
		System.out.println("Server: accepted");
		return true;
	}

	public void sendAutomotive(Automotive automotive) {
		try {
			objectOutputStream.writeObject(automotive);
			objectOutputStream.flush();
		} catch (IOException e) {
			System.out.println("Server: Error writing");
		}
	}

	public void sendString(String string) {
		string = string + "\n";
		try {
			writer.write(string, 0, string.length());
			writer.flush();
		} catch (IOException e) {
			System.out.println("Server: Error writing");
		}
	}

	public void closeSession() {
		try {
			writer = null;
			reader = null;
			socket.close();
		} catch (IOException e) {
			System.err.println("Error closing socket ");
		}
		System.out.println("Server: closed");
	}

	@Override
	public void insertAutoIntoHashMap(Automotive automotive) {
		AutoServer autoServer = new BuildAuto();
		autoServer.insertAutoIntoHashMap(automotive);
	}

	@Override
	public String getAutoList() {
		AutoServer autoServer = new BuildAuto();
		return autoServer.getAutoList();
	}
}
