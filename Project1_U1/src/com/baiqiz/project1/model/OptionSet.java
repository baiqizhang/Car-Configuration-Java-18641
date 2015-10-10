package com.baiqiz.project1.model;

import java.io.Serializable;

@SuppressWarnings("serial")
class OptionSet implements Serializable{
/*
 *  Instance Variables
 */
	private Option option[];
	private String name;

	protected OptionSet(){
		this.name="None";
	}
	protected OptionSet(int size,String name)
	{
		this.option = new Option[size]; 
		for(int i=0;i<option.length;i++)
			option[i] = new Option(null, 0);
		this.name = name;
	}

/*
 * Getters
 */
	protected String getName() {
		return this.name;
	}
	
	protected int getOptionSize() {
		return this.option.length;		
	}
	
	protected Option[] getOption() {
		return this.option;
	}
	protected Option getOption(int index) {
		if (index<this.option.length)
			return this.option[index];
		else
			return null;
	}
	
/*
 * Finds
 */
	protected int findOption(String name) {
		for (int i=0;i<this.option.length;i++)
			if (this.option[i].getName().equals(name))
					return i;
		return -1;
	}
/*
 * Setters
 */
	protected void setOption(Option[] option) {
		this.option = option;
	}
	protected void setOption(int index, Option option) {
		if (index<this.option.length)
			this.option[index] = option;
	}
	protected void setName(String name) {
		this.name = name;
	}
/*
 * Delete
 */
	protected void deleteOption(String optionName) {
		int index = findOption(optionName);
		if (index < 0)
			return;
		this.option[index] = null;
		return;
	}
/*
 * Update
 */
	protected void updateOption(String name,String newname,float price) {
		int index = findOption(name);
		if (index < 0)
			return;
		this.option[index] = new Option(newname,price);
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
		for (int i=0;i<this.option.length;i++)
			if (this.option[i]!=null)
				stringBuilder.append(this.option[i]);
		stringBuilder.append("\t\t]\n");
		return stringBuilder.toString();
	}
}