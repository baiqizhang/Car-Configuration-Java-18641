package com.baiqiz.project1.network;

import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.baiqiz.project1.exception.AutoException;
import com.baiqiz.project1.model.Automotive;
import com.baiqiz.project1.network.AutoSocket;

public class CarModelOptionsIO extends AutoSocket {
	private int method = 0;

	public boolean methodFinished() {
		if (method == 0)
			return true;
		else
			return false;
	}
	
	@Override
	public void run() {
		if (openConnection("client")) {
			while (method != -1) {
				switch (method) {
				// Send Automotive
				case 0:
					try {
						sleep(10);
					} catch (InterruptedException e1) {
					}
					break;
				//read a property file and send automotive to the server
				case 1:
					try {
						sendCmd("Method1");
						objectOutputStream.writeObject(automotive);
						objectOutputStream.flush();
						// System.out.println("Invoked");
					} catch (IOException e) {
						System.out.println("Error writing");
					}
					method = 0;
					break;
				// Configure Car
				case 2:
					sendCmd("Method2");
					String strInput = null;
					try {
						int count = 0;
						ArrayList<String> nameList = new ArrayList<String>();
						
						while ((strInput = reader.readLine()) != null) {
							System.out.println("List:");
							if (strInput.equals("")){
								System.out.println("Error: Empty...");
								break;
							}
							count++;
							System.out.print(count);
							System.out.print(":");
							System.out.println(strInput);
							nameList.add(strInput);
							while ((strInput = reader.readLine()) != null) {
								if (strInput.equals(""))
									break;
								count++;
								System.out.print(count);
								System.out.print(":");
								System.out.println(strInput);
								nameList.add(strInput);
							}
							break;
						}
						System.out.println("Which car would you like to choose?");
						BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
						try {
							strInput=bufferedReader.readLine();
							int index = Integer.parseInt(strInput);
							sendCmd("Method2_Pick");
							sendCmd(nameList.get(index-1));
							Automotive automotive = (Automotive) objectInputStream.readObject();
							System.out.println(automotive);
							
							while (true){
								System.out.println("1.choose option");
								System.out.println("2.exit");
								strInput = bufferedReader.readLine();
								index = Integer.parseInt(strInput);
								if (index==1){
									System.out.print("OptionSet:");
									String optionSetName = bufferedReader.readLine();
									System.out.print("OptionName:");
									String optionName = bufferedReader.readLine();
									automotive.setOptionChoice(optionSetName, optionName);
									System.out.println(automotive);
								}
								if (index==2)
									break;
							}
						} catch (NumberFormatException e) {
							System.err.println("Error: Integer parsing failed.");
						} catch (AutoException e) {
							System.err.println("Error: AutoException.");
						} catch (ClassNotFoundException e) {
							System.err.println("Error: ClassNotFoundException.");
						}catch(IndexOutOfBoundsException e){
							System.err.println("Error: Index Out of Bound!");
						}

						method = 0;
						
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}
			}
			closeSession();
		}
	}

	//Send a Automotive object to the server asynchronizly
	public void sendAutomotive(Automotive automotive) {
		this.automotive = automotive;
		method = 1;
	}

	//Send a command to the server, and wait for respond
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

	//Fetch a list of available automotives and start configuration
	public void configAutomotive() {
		method = 2;
	}
	
}
