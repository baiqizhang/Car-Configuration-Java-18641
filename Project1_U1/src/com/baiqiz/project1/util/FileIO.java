package com.baiqiz.project1.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.baiqiz.project1.model.Automotive;

public class FileIO {
	
/*
 * Deserialization method
 */
	public Automotive deserializeAutomotive(String filename) {
		Automotive automotive = null;
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
			automotive=(Automotive)in.readObject();
			in.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return automotive;		
	}
	
/*
 * Serialization method
 */
	public void serializeAutomotive(String filename,Automotive automotive) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
			out.writeObject(automotive);
			out.close();	
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

/*
 * Private method for reading Options, 3rd depth in dfs.
 */
	private void readOption(BufferedReader buff, Automotive automotive, 
			int optionSetIndex, int optionIndex) throws IOException{
		float price=0;
		String name=null;

		String line = buff.readLine();
		if (line == null)
			return;
		else
			name=line;
		line = buff.readLine();
		if (line == null)
			return;
		else
			price=Float.parseFloat(line);
		automotive.setOption(optionSetIndex, optionIndex, name, price);
	}
	
/*
 * Private method for reading OptionSets, 2rd depth in dfs.
 * Will call readOption method for each Option seat
 */
	private void readOptionSet(BufferedReader buff, Automotive automotive, 
			int optionSetIndex) throws IOException{
		int size=0;
		String name=null;

		String line = buff.readLine();
		if (line == null)
			return;
		else
			name=line;
		line = buff.readLine();
		if (line == null)
			return;
		else
			size=Integer.parseInt(line);
		automotive.setOptionSet(optionSetIndex, size, name);
		for (int i=0;i<size;i++)
			readOption(buff, automotive, optionSetIndex, i);
	}
	
/*
 * Main method for reading Automotive Class, 1st depth in dfs.
 * Will call readOptionSet method for each OptionSet seat
 */
	public Automotive buildAutoObject(String filename){
		int size=0;
		float price=0;
		String name=null;
		
		Automotive automotive = new Automotive();
		try {
			FileReader file = new FileReader(filename);
			BufferedReader buff = new BufferedReader(file);
	
			name=buff.readLine();
			price=Float.parseFloat(buff.readLine());			
			size=Integer.parseInt(buff.readLine());
			automotive.setName(name);
			automotive.setBasePrice(price);
			automotive.setOptionSetSize(size);
			for (int i=0;i<size;i++)
				readOptionSet(buff, automotive, i);
			buff.close();
		} catch (IOException e) {
			System.out.println("Error " + e.toString());
		}
		return automotive;		
	}
}
