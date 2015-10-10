package com.baiqiz.project1.exception;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FixModel {

	public String fix(int errorno){		
		switch (errorno) {
		case AutoException.MISSINGOPTION:
			System.out.println("Please retype option name.");
			break;
		case AutoException.MISSINGOPTIONSET:
			System.out.println("Please retype option set name.");
			break;
		case AutoException.MISSINGOPTIONCHOICE:
			System.out.println("Please make a choice.");
			break;
		default:
			System.out.println("Fixing Failed!");
			return null;
		}
		System.out.println("New input:");
		BufferedReader br = 
                new BufferedReader(new InputStreamReader(System.in));
		try {
			return br.readLine();
		} catch (IOException e1) {
			System.out.println("Fixing Failed!");
		}
		
		return null;		
	}
}
