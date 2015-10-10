package com.baiqiz.project1.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.baiqiz.project1.exception.AutoException;
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
			int optionSetIndex, int optionIndex) throws IOException, AutoException{
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
		else{
			try{
				price=Float.parseFloat(line);
			}catch(NumberFormatException e){
				throw new AutoException(AutoException.WRONGFORMAT);
			}
		}
		automotive.setOption(optionSetIndex, optionIndex, name, price);
	}
	
/*
 * Private method for reading OptionSets, 2rd depth in dfs.
 * Will call readOption method for each Option seat
 */
	private void readOptionSet(BufferedReader buff, Automotive automotive, 
			int optionSetIndex) throws IOException, AutoException{
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
		else{
			try{
				size=Integer.parseInt(line);
			}catch(NumberFormatException e){
				throw new AutoException(AutoException.WRONGFORMAT);
			}
		}
		automotive.setOptionSet(optionSetIndex, size, name);
		for (int i=0;i<size;i++)
			readOption(buff, automotive, optionSetIndex, i);
	}
	
/*
 * Main method for reading Automotive Class, 1st depth in dfs.
 * Will call readOptionSet method for each OptionSet seat
 */
	public Automotive buildAutoObject(String filename) throws AutoException{
		int size=0;
		float price=0;
		String make,model=null;
		
		Automotive automotive = new Automotive();
		File testFile = new File(filename);
		if (testFile.exists()==false)
			throw new AutoException(AutoException.WRONGFILENAME);
		
		try {
			FileReader file = new FileReader(filename);
			BufferedReader buff = new BufferedReader(file);
	
			make=buff.readLine();
			model=buff.readLine();
			price=Float.parseFloat(buff.readLine());			
			size=Integer.parseInt(buff.readLine());
			automotive.setMake(make);
			automotive.setModel(model);
			automotive.setBasePrice(price);
			for (int i=0;i<size;i++)
				readOptionSet(buff, automotive, i);
			buff.close();
		} catch (IOException e) {
			System.out.println("Error " + e.toString());
		} catch(NumberFormatException e){
			throw new AutoException(AutoException.WRONGFORMAT);
		}
		
		return automotive;		
	}
	
	
	/*
	 * Main method for reading Automotive Class, 1st depth in dfs.
	 * Will call readOptionSet method for each OptionSet seat
	 */
		public Automotive buildAutoObjectFromPropertyFile(String filename) throws AutoException{
			int size=0;
			float price=0;
			String make,model=null;

			Automotive automotive = new Automotive();
			Properties properties;
			try {
				properties = new Properties(filename);			
				model=properties.getProperty("Model");
				make=properties.getProperty("Make");
				price=Float.parseFloat(properties.getProperty("BasePrice"));			
				size=Integer.parseInt(properties.getProperty("OptionSetCount"));
				automotive.setMake(make);
				automotive.setModel(model);
				automotive.setBasePrice(price);
				for (int optionSetIndex=0;optionSetIndex<size;optionSetIndex++){
					String name=properties.getProperty("OptionSet"+optionSetIndex+"_Name");
					size=Integer.parseInt(properties.getProperty("OptionSet"+optionSetIndex+"_Count"));
	
					automotive.setOptionSet(optionSetIndex, size, name);
					for (int optionIndex=0;optionIndex<size;optionIndex++){
						name=properties.getProperty("OptionSet"+optionSetIndex+"_Option"
													+optionIndex+"_Name");
						price=Float.parseFloat(properties.getProperty("OptionSet"+optionSetIndex
								+"_Option"+optionIndex+"_Price"));
						automotive.setOption(optionSetIndex, optionIndex, name, price);
					}
				}
			} catch (Exception e) {
				System.err.println("File Exception");
			}

			return automotive;		
		}
}
