package com.baiqiz.project1.exception;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FixUtil {
	
	public String fix(int errorno){		
		switch (errorno) {
		case AutoException.WRONGFILENAME:
		case AutoException.WRONGFORMAT:
			System.out.println("Please retype file name.");
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
