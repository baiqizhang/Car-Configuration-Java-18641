package com.baiqiz.project1.adapter;


import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import com.baiqiz.project1.exception.AutoException;
import com.baiqiz.project1.model.Automotive;
import com.baiqiz.project1.util.FileIO;

public abstract class ProxyAutomotive {
	private static LinkedHashMap<String, Automotive> automotives 
		= new LinkedHashMap<String, Automotive>();
	
	public void BuildAuto(String filename){
		FileIO fileIO = new FileIO();
		while (true){
			try {
				Automotive automotive = fileIO.buildAutoObject(filename);
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(automotive.getMake());
				stringBuilder.append(" ");
				stringBuilder.append(automotive.getModel());
				automotives.put(stringBuilder.toString(),automotive);
				break;
			} catch (AutoException e) {
				if (e.getErrorno() == AutoException.WRONGFILENAME
					|| e.getErrorno() == AutoException.WRONGFORMAT
				){
					filename = FixError(e);
					continue;
				}else
					System.out.println("Failed to recover.");
					break;
			}
			
		}
	}
	
	public void PrintAuto(String modelname){
		Automotive automotive = automotives.get(modelname);
		if (automotive!=null)
			System.out.println(automotive.toString());
		else
			System.out.println("Object not exist!");
	}
	
	public void updateOptionSetName(String modelName, String optionSetName, String
			newName){
		//-1 = do not change size
		
		Automotive automotive = automotives.get(modelName);
		if (automotive==null){
			System.out.println("Object not exist!");
			return;
		}
		while (true){
			try {
				automotive.updateOptionSet(optionSetName, -1, newName);
				break;
			} catch (AutoException e) {
				if (e.getErrorno() == AutoException.MISSINGOPTIONSET){
					optionSetName = FixError(e);
					continue;
				}else
					System.out.println("Failed to recover.");
					break;
			}
		}
	}
	
	public void updateOptionPrice(String modelName, String optionSetName, String
			optionName, float newprice){
		Automotive automotive = automotives.get(modelName);
		if (automotive==null){
			System.out.println("Object not exist!");
			return;
		}
		while (true){
			try {
				automotive.updateOption(optionName, optionSetName, optionName, newprice);
				break;
			} catch (AutoException e) {
				if (e.getErrorno()==AutoException.MISSINGOPTION){
					optionName = FixError(e);
					continue;
				}else if (e.getErrorno() == AutoException.MISSINGOPTIONSET){
					optionSetName = FixError(e);
					continue;
				}else
					System.out.println("Failed to recover.");
					break;
			}
		}
	}
	
	public void pickOption(String modelName, String optionSetName, String
			optionName){
		Automotive automotive = automotives.get(modelName);
		if (automotive==null){
			System.out.println("Object not exist!");
			return;
		}
		while (true){
			try {
				automotive.setOptionChoice(optionSetName, optionName);
				break;
			} catch (AutoException e) {
				if (e.getErrorno()==AutoException.MISSINGOPTION){
					optionName = FixError(e);
					continue;
				}else if (e.getErrorno() == AutoException.MISSINGOPTIONSET){
					optionSetName = FixError(e);
					continue;
				}else
					System.out.println("Failed to recover.");
					break;
			}
		}
	}
	
	public float getTotalPrice(String modelName){
		Automotive automotive = automotives.get(modelName);
		if (automotive==null){
			System.out.println("Object not exist!");
			return 0;
		}
		return automotive.getTotalPrice();
	}
	
	public float getOptionPrice(String modelName, String optionSetName, String
			optionName){
		Automotive automotive = automotives.get(modelName);
		if (automotive==null){
			System.out.println("Object not exist!");
			return 0;
		}
		int optionSetIndex=automotive.findOptionSet(optionSetName);
		int optionIndex=automotive.findOption(optionName, optionSetName);
		if (optionSetIndex<0)
			System.out.println("OptionSet not exist!");
		else if (optionIndex<0)
			System.out.println("Option not exist!");
		else {
			try {
				return automotive.getOptionPrice(optionSetIndex, optionIndex);
			} catch (AutoException e) {
				System.out.println("Option/OptionSet not exist!");
				return 0;
			}
		}
		return 0;
	}
	
	public Automotive getAutomotive(String modelName){
		Automotive automotive = automotives.get(modelName);
		if (automotive==null){
			System.out.println("Object not exist!");
			return null;
		}
		return automotive;
	}
	
	public String FixError(AutoException e){
		return e.fix();
	}
	
}
