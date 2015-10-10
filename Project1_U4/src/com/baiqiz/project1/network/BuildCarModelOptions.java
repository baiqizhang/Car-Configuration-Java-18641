package com.baiqiz.project1.network;

import java.net.*;

import com.baiqiz.project1.adapter.AutoServer;
import com.baiqiz.project1.adapter.BuildAuto;
import com.baiqiz.project1.model.Automotive;
import com.baiqiz.project1.network.AutoSocket;

import java.io.*;

public class BuildCarModelOptions extends AutoSocket implements AutoServer{
	@Override
	public void run() {
		//Keep accepting connections
		while(true){
			if (!openConnection("server"))
				return;
			while (true) {
				try {
					String strInput = recvString();
					// Connection shutdown
					if (strInput == null)
						break;
					//Respond to cliend
					sendString("OK");
					
					//Option1, accept an automotive object from client and add to linkedhashmap
					if (strInput.equals("Method1")) {
						while ((automotive = (Automotive) objectInputStream.readObject()) != null) {
							System.out.println("Client:" + automotive);
							insertAutoIntoHashMap(automotive);
							break;
						}
					}
					//Send a list of autos
					if (strInput.equals("Method2")) {
						sendString(getAutoList());
					}
					//Pick a auto and send to client
					if (strInput.equals("Method2_Pick")) {
						sendString("OK");
						strInput = recvString();
						automotive=getAutoFromList(strInput);
						System.err.println(automotive);
						objectOutputStream.writeObject(automotive);
						objectOutputStream.flush();
					}
	
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			closeSession();
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

	public String recvString(){
		String strInput=null;
		try {
			while ((strInput = reader.readLine()) != null) {
				System.err.println("Client:" + strInput);
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strInput;
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

	@Override
	public Automotive getAutoFromList(String name) {
		AutoServer autoServer = new BuildAuto();
		return autoServer.getAutoFromList(name);
	}
}
