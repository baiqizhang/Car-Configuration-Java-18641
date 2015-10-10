package com.baiqiz.project1.exception;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

@SuppressWarnings("serial")
public class AutoException extends Exception {
	public static final int WRONGFILENAME = 200;
	public static final int WRONGFORMAT = 201;
	
	public static final int MISSINGOPTIONSET = 100; 
	public static final int MISSINGOPTION = 101;
	public static final int MISSINGOPTIONCHOICE = 102;

	private static final String[] utilError={"WRONG FILENAME","WRONG FORMAT"};
	private static final String[] modelError={"MISSING OPTIONSET", 
			"MISSING OPTION", "MISSING OPTION CHOICE"};
	
	int errorno;

	
	public int getErrorno() {
		return errorno;
	}

	public void setErrorno(int errorno) {
		this.errorno = errorno;
	}

	public AutoException(int errorno) {
		this.errorno = errorno;
		
		int type = errorno/100;
		int code = errorno - type*100;
		switch (type){
			case 1:
				Complain(modelError[code]);
			break;
			case 2: 
				Complain(utilError[code]);
			break;
		}
	}

	private void Complain(String msg){
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter("Log",true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(LocalDateTime.now());
			stringBuilder.append(": Error:");
			stringBuilder.append(msg);
			stringBuilder.append("\n");
			bufferedWriter.write(stringBuilder.toString());
			bufferedWriter.close();
			System.out.println(stringBuilder);
		} catch (IOException e) {
			System.out.println("Cannot write to log file!");
			e.printStackTrace();
		}
		
	}
	
	public String fix(){
		int type = errorno/100;
		switch (type){
			case 1:
				return new FixModel().fix(errorno);
			case 2: 
				return new FixUtil().fix(errorno);
		}
		return null;
	}
}
