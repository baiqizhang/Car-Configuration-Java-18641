package com.baiqiz.project1.model;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;

import javax.print.attribute.standard.RequestingUserName;

import com.baiqiz.project1.exception.AutoException;
import com.baiqiz.project1.model.OptionSet.Option;

/*
 *  I decide to put syncronized method in Automotive class because mutual exclusion is only
 *  required for methods of a certain Automotive instance. It is acceptable for two edit method
 *  for two different Automotive instance interleaves. 
 */

@SuppressWarnings("serial")
public class Automotive implements Serializable{
	
/*
 * Instance variables
 */	
	private String make;
	private String model;
	private float basePrice;
	private ArrayList<OptionSet> optionSet;
	private ArrayList<OptionSet.Option> choice;
	
	
/*
 * Constructors
 */	
	public Automotive(String make, String model, float basePrice)
	{
		this.optionSet = new ArrayList<OptionSet>();
		this.choice = new ArrayList<Option>();		
		this.make = make;
		this.model = model;
		this.basePrice = basePrice;
	}
	
	public Automotive()
	{
		optionSet = new ArrayList<OptionSet>();
		this.choice = new ArrayList<Option>();
		this.make = "None";
		this.model = "None";
		this.basePrice = 0;
	}
/*
 * Getters for Automotive Class
 */

	public synchronized float getBasePrice() {
		return basePrice;
	}
	
	public synchronized String getMake() {
		return make;
	}

	public synchronized void setModel(String model) {
		this.model = model;
	}

	public synchronized int getOptionSetSize() {
		return this.optionSet.size();
	}
	
	public synchronized String getOptionChoice(String optionSetName) throws AutoException{
		int index=findOptionSet(optionSetName);
		if (index<0)
			throw new AutoException(AutoException.MISSINGOPTIONCHOICE);
		return optionSet.get(index).getOptionChoice().getName();
	}
	
	public synchronized float getOptionChoicePrice(String optionSetName) throws AutoException{
		int index=findOptionSet(optionSetName);
		if (index<0)
			throw new AutoException(AutoException.MISSINGOPTIONSET);
		return optionSet.get(index).getOptionChoice().getPrice();
	}

	public synchronized float getTotalPrice(){
		float price=this.getBasePrice();
		for (int index=0;index<optionSet.size();index++){
			Option option;			
			while (true){
				try {
					option = this.optionSet.get(index).getOptionChoice();
					break;
				} catch (AutoException e) {
					System.out.println("No option choosen for optionSet:" 
							+ this.optionSet.get(index).getName());
					String choice = e.fix();
					try {
						this.optionSet.get(index).SetOptionChoice(choice);
					} catch (AutoException e1) {
						continue;
					}
					continue;
				}
			}
			
			price+=option.getPrice();
		}
		return price;
	}

/*
 * Getters for OptionSet Class
 */
	
	public synchronized ArrayList<OptionSet> getOptionSet() {
		return optionSet;
	}

	public synchronized OptionSet getOptionSet(int index) throws AutoException {
		if (index<optionSet.size())
			return optionSet.get(index);
		else 
			throw new AutoException(AutoException.MISSINGOPTIONSET);
	}
	
	public synchronized String getOptionSetName(int index) throws AutoException {
		if (index<optionSet.size())
			return optionSet.get(index).getName();
		else 
			throw new AutoException(AutoException.MISSINGOPTIONSET);
	}
	
	public synchronized int getOptionSize(int optionSetIndex) {
		return optionSet.get(optionSetIndex).getOptionSize();
	}

/*
 * Getters for Option Class
 */
	public synchronized OptionSet.Option getOption(int optionSetIndex,int optionIndex) throws AutoException {
		if (optionSetIndex>=optionSet.size())
			throw new AutoException(AutoException.MISSINGOPTIONSET);
		OptionSet optionSet = this.optionSet.get(optionIndex);
		return optionSet.getOption(optionIndex);
	}
	
	public synchronized String getOptionName(int optionSetIndex,int optionIndex) throws AutoException {
		if (optionSetIndex>=optionSet.size())
			throw new AutoException(AutoException.MISSINGOPTIONSET);
		OptionSet optionSet = this.optionSet.get(optionSetIndex);
		return optionSet.getOption(optionIndex).getName();
	}

	public synchronized float getOptionPrice(int optionSetIndex,int optionIndex) throws AutoException {
		if (optionSetIndex>=optionSet.size())
			throw new AutoException(AutoException.MISSINGOPTIONSET);
		OptionSet optionSet = this.optionSet.get(optionIndex);
		return optionSet.getOption(optionIndex).getPrice();
	}
	
/*
 * Finds
 */
	public synchronized int findOptionSet(String name) {
		for (int i=0;i<this.optionSet.size();i++)
			if (this.optionSet.get(i).getName().equals(name))
					return i;
		return -1;
	}

	public synchronized int findOption(String optionName, String optionSetName) {
		int index = findOptionSet(optionSetName);
		if (index < 0)
			return -1;
		return this.optionSet.get(index).findOption(optionName);	
	}

/*
 * Setters
 */	
	public synchronized void setMake(String make) {
		this.make = make;
	}

	public synchronized String getModel() {
		return model;
	}
	
	public synchronized void setBasePrice(float basePrice) {
		this.basePrice = basePrice;
	}
	
	public synchronized void setOptionSet(int index, OptionSet optionSet) {
		if (index==this.optionSet.size()){
			this.optionSet.add(optionSet);
			this.choice.add(null);
		}
		if (index<this.optionSet.size())
			this.optionSet.set(index, optionSet);
	}

	public synchronized void setOptionSet(int index, int size, String name) {
		if (index==this.optionSet.size()){
			this.optionSet.add(index, new OptionSet(size, name));
			this.choice.add(null);
		}
		if (index<this.optionSet.size())
			this.optionSet.set(index, new OptionSet(size, name));
	}

	public synchronized void setOption(int optionSetIndex, int optionIndex, String name, float price) {
		if (optionSetIndex < this.optionSet.size())
		{
			OptionSet optionSet = this.optionSet.get(optionSetIndex);
			
			Option option = optionSet.new Option(name, price);
			optionSet.setOption(optionIndex, option);
		}

	}
	
	public synchronized void setOptionChoice(String optionSetName, String optionName) throws AutoException{
		int index=findOptionSet(optionSetName);
		if (index<0)
			throw new AutoException(AutoException.MISSINGOPTIONSET);
		this.optionSet.get(index).SetOptionChoice(optionName);
		this.choice.set(index, this.optionSet.get(index).getOption(optionName));
	}
	

/*
 * Delete
 */
	public synchronized void deleteOptionSet(String name) {
		int index = findOptionSet(name);
		if (index < 0)
			return;
		this.optionSet.remove(index);
		this.choice.remove(index);
	}

	public synchronized void deleteOption(String optionName, String optionSetName) throws AutoException {
		int index = findOptionSet(optionSetName);
		if (index < 0)
			return;
		Option option=this.choice.get(index);
		if (option!=null && option.getName()==optionName)
			this.choice.set(index, null);
		this.optionSet.get(index).deleteOption(optionName);
	}

/*
 * Update
 */
	public synchronized void updateOptionSet(String name,int size,String newname) throws AutoException {
		int index = findOptionSet(name);
		if (index < 0)
			throw new AutoException(AutoException.MISSINGOPTIONSET);
		if (size>=0) 
			this.setOptionSet(index, new OptionSet(size, newname));
		else  	//Do not change size
			this.getOptionSet(index).setName(newname);
	}

	public synchronized void updateOption(String optionName, String optionSetName,String newname,float price) throws AutoException {
		int index = findOptionSet(optionSetName);
		if (index < 0)
			throw new AutoException(AutoException.MISSINGOPTIONSET);
		this.getOptionSet(index).updateOption( optionName, newname, price);
	}

	@Override
	public synchronized String toString() {
		StringBuilder stringBuilder =new StringBuilder();
		stringBuilder.append("Automobile{\n\tMake:");
		stringBuilder.append(this.getMake());
		stringBuilder.append("\n\tModel:");
		stringBuilder.append(this.getModel());
		stringBuilder.append("\n\tBasePrice:$");
		stringBuilder.append(this.basePrice);
		stringBuilder.append("\n\tOptionSet:[\n");
		for (int i=0;i<this.optionSet.size();i++)
			stringBuilder.append(this.optionSet.get(i));
		stringBuilder.append("\t]\n}\n");
		
		for (int i=0;i<this.optionSet.size();i++){
			stringBuilder.append(this.optionSet.get(i).getName());
			stringBuilder.append(":");
			if (this.choice.get(i)==null)
				stringBuilder.append("Not picked yet");
			else 
				stringBuilder.append(this.choice.get(i).getName());
			stringBuilder.append("\n");
		}
			
		
		return stringBuilder.toString();
	}


}
