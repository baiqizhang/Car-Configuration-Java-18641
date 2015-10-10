package com.baiqiz.project1.model;

import java.io.Serializable;

@SuppressWarnings("serial") 
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