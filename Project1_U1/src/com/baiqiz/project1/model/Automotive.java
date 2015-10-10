package com.baiqiz.project1.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Automotive implements Serializable{
	
/*
 * Instance variables
 */	
	private String name;
	private float basePrice;
	private OptionSet optionSet[];
	
/*
 * Constructors
 */	
	public Automotive(int optionSize, String name, float basePrice)
	{
		
		optionSet = new OptionSet[optionSize]; 
		for(int i=0;i<optionSet.length;i++)
			optionSet[i] = new OptionSet(0,null);
		this.name = name;
		this.basePrice = basePrice;
	}

	public Automotive(String name, float basePrice)
	{
		optionSet = null; 
		this.name = name;
		this.basePrice = 0;
	}
	
	public Automotive()
	{
		optionSet = null; 
		this.name = "None";
		this.basePrice = 0;
	}
/*
 * Getters for Automotive Class
 */
	public String getName() {
		return name;
	}

	public float getBasePrice() {
		return basePrice;
	}
	
	public int getOptionSetSize() {
		return this.optionSet.length;
	}

/*
 * Getters for OptionSet Class
 */
	
	public OptionSet[] getOptionSet() {
		return optionSet;
	}

	public OptionSet getOptionSet(int index) {
		if (index<optionSet.length)
			return optionSet[index];
		else 
			return null;
	}
	
	public String getOptionSetName(int index) {
		if (index<optionSet.length)
			return optionSet[index].getName();
		else 
			return null;
	}
	
	public int getOptionSize(int optionSetIndex) {
		return this.optionSet[optionSetIndex].getOptionSize();
	}

/*
 * Getters for Option Class
 */
	public Option getOption(int optionSetIndex,int optionIndex) {
		if (optionSetIndex>=optionSet.length)
			return null;
		OptionSet optionSet = this.optionSet[optionIndex];
		return optionSet.getOption(optionIndex);
	}
	
	public String getOptionName(int optionSetIndex,int optionIndex) {
		if (optionSetIndex>=optionSet.length)
			return null;
		OptionSet optionSet = this.optionSet[optionIndex];
		return optionSet.getOption(optionIndex).getName();
	}

	public float getOptionPrice(int optionSetIndex,int optionIndex) {
		if (optionSetIndex>=optionSet.length)
			return 0;
		OptionSet optionSet = this.optionSet[optionIndex];
		return optionSet.getOption(optionIndex).getPrice();
	}
	
/*
 * Finds
 */
	public int findOptionSet(String name) {
		for (int i=0;i<this.optionSet.length;i++)
			if (this.optionSet[i].getName().equals(name))
					return i;
		return -1;
	}

	public int findOption(String optionName, String optionSetName) {
		int index = findOptionSet(optionSetName);
		if (index < 0)
			return -1;
		OptionSet optionSet = this.optionSet[index];
		Option options[] = optionSet.getOption();
		for (int i=0;i<options.length;i++)
			if (options[i].getName().equals(optionName))
					return i;
		return -1;
	}

/*
 * Setters
 */
	public void setName(String name) {
		this.name = name;
	}

	public void setBasePrice(float basePrice) {
		this.basePrice = basePrice;
	}

	public void setOptionSetSize(int size) {
		this.optionSet = new OptionSet[size];
		for (int i=0;i<size;i++)
			this.optionSet[i] = new OptionSet();
	}

	public void setOptionSet(OptionSet[] optionSet) {
		this.optionSet = optionSet;
	}
	
	public void setOptionSet(int index, OptionSet optionSet) {
		if (index<this.optionSet.length)
			this.optionSet[index] = optionSet;
	}

	public void setOptionSet(int index, int size, String name) {
		if (index<this.optionSet.length){
			this.optionSet[index] = new OptionSet(size, name);
		}
	}

	public void setOption(int optionSetIndex, int optionIndex, String name, float price) {
		if (optionSetIndex < this.optionSet.length)
		{
			OptionSet optionSet = this.optionSet[optionSetIndex];
			if (optionIndex < optionSet.getOption().length){
				Option option = new Option(name, price);
				optionSet.setOption(optionIndex, option);
			}
		}

	}

/*
 * Delete
 */
	public void deleteOptionSet(String name) {
		int index = findOptionSet(name);
		if (index < 0)
			return;
		this.optionSet[index] = null;
		return;
	}

	public void deleteOption(String optionName, String optionSetName) {
		int index = findOptionSet(optionSetName);
		if (index < 0)
			return;
		this.optionSet[index].deleteOption(optionName);
		return;
	}

/*
 * Update
 */
	public void updateOptionSet(String name,int size,String newname) {
		int index = findOptionSet(name);
		if (index < 0)
			return;
		this.optionSet[index] = new OptionSet(size, newname);
	}

	public void updateOption(String optionName, String optionSetName,String newname,float price) {
		int index = findOptionSet(optionSetName);
		if (index < 0)
			return;
		this.optionSet[index].updateOption( optionName, newname, price);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder =new StringBuilder();
		stringBuilder.append("Automobile{\n\tmodel:");
		stringBuilder.append(this.name);
		stringBuilder.append("\n\tbasePrice:$");
		stringBuilder.append(this.basePrice);
		stringBuilder.append("\n\tOptionSet:[\n");
		for (int i=0;i<this.optionSet.length;i++)
			if (this.optionSet[i]!=null)
				stringBuilder.append(this.optionSet[i]);
		stringBuilder.append("\t]\n}\n");		
		return stringBuilder.toString();
	}


}
