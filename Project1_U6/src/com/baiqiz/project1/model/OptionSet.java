package com.baiqiz.project1.model;

import java.io.Serializable;
import java.rmi.server.SkeletonNotFoundException;
import java.util.ArrayList;

import com.baiqiz.project1.exception.AutoException;

@SuppressWarnings("serial")
class OptionSet implements Serializable {
	
	class Option implements Serializable{
	/*
	 *  Instance Variables
	 */
		private String name;
		private float price;
		
		protected Option(){
			this.name="None";
		}
	
		protected Option(String name, float price)
		{
			this.name  = name;
			this.price = price;
		}
	
	/*
	 * Getters
	 */
		protected String getName() {
			return name;
		}
	
		protected float getPrice() {
			return price;
		}
	/*
	 * Setters
	 */
		protected void setPrice(float price) {
			this.price = price;
		}
	
		protected void setName(String name) {
			this.name = name;
		}	
	/*
	 * toString
	 */
		@Override
		public String toString() {
			StringBuilder stringBuilder =new StringBuilder();
			stringBuilder.append("\t\t\t{");
			stringBuilder.append(this.name);
			stringBuilder.append(": $");
			stringBuilder.append(this.price);
			stringBuilder.append("}\n");		
			return stringBuilder.toString();
		}
	}
	
/*
 *  Instance Variables
 */
	private ArrayList<Option> option;
	private Option optionChoice;
	private String name;

	protected OptionSet(){
		this.option = new ArrayList<Option>();
		this.name="None";
	}
	protected OptionSet(int size,String name)
	{
		this.option = new ArrayList<Option>(); 
		this.name = name;
	}

/*
 * Getters
 */
	protected Option getOptionChoice() throws AutoException {
		if (optionChoice==null)
			throw new AutoException(AutoException.MISSINGOPTIONCHOICE);
		return this.optionChoice;
	}
	
	protected String getName() {
		return this.name;
	}
	
	protected int getOptionSize() {
		return this.option.size();		
	}
	
	protected ArrayList<Option> getOption() {
		return this.option;
	}
	protected Option getOption(int index) {
		if (index<this.option.size())
			return this.option.get(index);
		else
			return null;
	}
	protected Option getOption(String optionName) {
		for (int index=0;index<this.option.size();index++)
			if (this.option.get(index).getName().equals(optionName))
				return this.option.get(index);
		return null;
	}
	
/*
 * Finds
 */
	protected int findOption(String name) {
		for (int i=0;i<this.option.size();i++)
			if (this.getOption(i).getName().equals(name))
					return i;
		return -1;
	}
/*
 * Setters
 */
	protected void SetOptionChoice(String optionName) throws AutoException {
		int index=findOption(optionName);
		if (index>=0){
			this.optionChoice = getOption(index);
			return;
		}
		throw new AutoException(AutoException.MISSINGOPTION);
	}
	protected void setOption(ArrayList<Option> option) {
		this.option = option;
	}
	protected void setOption(int index, Option option) {
		if (index==this.option.size())
			this.option.add(option);		
		if (index<this.option.size())
			this.option.set(index, option);
	}
	protected void setName(String name) {
		this.name = name;
	}
/*
 * Delete
 */
	protected void deleteOption(String optionName) throws AutoException {
		int index = findOption(optionName);
		if (index < 0)
			throw new AutoException(AutoException.MISSINGOPTION);
		this.option.remove(index);
	}
/*
 * Update
 */
	protected void updateOption(String name,String newname,float price) throws AutoException {
		int index = findOption(name);
		if (index < 0)
			throw new AutoException(AutoException.MISSINGOPTION);
		setOption(index, new Option(newname,price));
	}
/*
 * ToString
 */
	@Override
	public String toString() {
		StringBuilder stringBuilder =new StringBuilder();
		stringBuilder.append("\t\t");
		stringBuilder.append(this.name);
		stringBuilder.append(":[\n");
		for (int i=0;i<this.option.size();i++)
			if (this.getOption(i)!=null)
				stringBuilder.append(this.getOption(i));
		stringBuilder.append("\t\t]\n");
		return stringBuilder.toString();
	}
}