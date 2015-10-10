package com.baiqiz.project1.adapter;

import com.baiqiz.project1.model.Automotive;

public interface UpdateAuto {
	public void updateOptionSetName(String Modelname, String OptionSetname, String
			newName);
	public void updateOptionPrice(String Modelname, String optionSetName, String
			optionName, float newprice);
	
	public float getOptionPrice(String modelName, String optionSetName, String
			optionName);
	public Automotive getAutomotive(String modelName);
	
}
