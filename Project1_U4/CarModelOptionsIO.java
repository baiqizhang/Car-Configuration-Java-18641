package com.baiqiz.project1.server;

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

public class CarModelOptionsIO extends Thread {
	private BufferedReader reader;
	private BufferedWriter writer;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	private Socket socket;
	private int method = 0;
	private Automotive automotive;
	private final int iPort = 4444;

	public boolean methodFinished() {
		if (method == 0)
			return true;
		else
			return false;
	}

	public void run() {
		if (openConnection()) {
			while (method != -1) {
				switch (method) {
				// Send Automotive
				case 0:
					try {
						sleep(10);
					} catch (InterruptedException e1) {
					}
					break;
				case 1:
					method = 0;
					try {
						sendCmd("Method1");
						objectOutputStream.writeObject(automotive);
						objectOutputStream.flush();
						// System.out.println("Invoked");
					} catch (IOException e) {
						System.out.println("Error writing");
					}
					break;
				// Configure Car
				case 2:
					method = 0;
					sendCmd("Method2");
					String strInput = null;
					try {
						int count = 0;
						while ((strInput = reader.readLine()) != null) {
							System.out.println("List:");
							if (strInput.equals("__EOF"))
								break;
							count++;
							System.out.print(count);
							System.out.print(":");
							System.out.println(strInput);

							while ((strInput = reader.readLine()) != null) {
								if (strInput.equals("__EOF"))
									break;
								System.out.print(count);
								System.out.print(":");
								System.out.println(count+":"+strInput);
								count++;
							}
							break;
						}
						if (count==0){
							System.out.println("Error: Empty...");
							break;
						}
						BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
						try {
							int index = Integer.parseInt(bufferedReader.readLine());
							
						} catch (Exception e) {
							System.err.println("Error: Integer parsing failed.");
						}


						
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}
			}
			closeSession();
		}
	}// run

	public boolean openConnection() {
		try {
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

	public void sendAutomotive(Automotive automotive) {
		this.automotive = automotive;
		method = 1;
	}

	public void sendCmd(String string) {
		System.err.println("Client:" + string);
		string = string + "\n";
		try {
			writer.write(string, 0, string.length());
			writer.flush();
			String echo;
			while ((echo = reader.readLine()) != null) {
				System.err.println("reply:" + echo);
				break;
			}
		} catch (IOException e) {
			System.err.println("Error writing");
		}
	}

	public void configAutomotive() {
		method = 2;
	}

	public void closeSession() {
		try {
			writer = null;
			reader = null;
			socket.close();
		} catch (IOException e) {
			System.err.println("Error closing socket ");
		}
		System.err.println("closed");
	}

}
