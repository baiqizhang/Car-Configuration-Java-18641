package com.baiqiz.project1.driver;

import com.baiqiz.project1.model.*;
import com.baiqiz.project1.util.FileIO;

public class Driver {

	public static void main(String[] args) {		
		FileIO fileIO = new FileIO();
		Automotive automotive = fileIO.buildAutoObject("input");
		System.out.println(automotive);
		
		fileIO.serializeAutomotive("test.ser", automotive);
		Automotive newobject = fileIO.deserializeAutomotive("test.ser");
		System.out.println(newobject);
	}
}
