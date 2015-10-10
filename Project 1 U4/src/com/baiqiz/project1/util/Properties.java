package com.baiqiz.project1.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Properties {
	private Map<String, String> map=new HashMap<String, String>();
	public Properties(String filename) throws IOException,FileNotFoundException{
		FileReader fileReader = new FileReader(filename);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String str;
		while (true){
			str=bufferedReader.readLine();
			if (str==null)
				break;
			String arr[]=str.split("=");
			if (arr.length!=2){
				System.out.println("Wrong format!");
				return;			
			}
			map.put(arr[0],arr[1]);
		}	
	}
	public String getProperty(String key){
		return map.get(key);
	}
	public void setProperty(String key,String val){
		map.put(key, val);
	}
}
