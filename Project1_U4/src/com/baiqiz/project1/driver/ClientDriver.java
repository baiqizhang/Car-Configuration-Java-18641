package com.baiqiz.project1.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;

import com.baiqiz.project1.adapter.BuildAuto;
import com.baiqiz.project1.adapter.CreateAuto;
import com.baiqiz.project1.adapter.PickAuto;
import com.baiqiz.project1.adapter.UpdateAuto;
import com.baiqiz.project1.exception.AutoException;
import com.baiqiz.project1.model.Automotive;
import com.baiqiz.project1.network.CarModelOptionsIO;
import com.baiqiz.project1.scale.EditOptions;
import com.baiqiz.project1.util.FileIO;
import com.baiqiz.project1.util.Properties;

public class ClientDriver {

	public static void main(String[] args) {
		FileIO fileIO = new FileIO();
		try {

			CarModelOptionsIO carModelOptionsIO = new CarModelOptionsIO();
			carModelOptionsIO.start();

			while (true) {				
				//UI
				System.out.println("1.upload properties file");
				System.out.println("2.configure a car");
				
				//Read line
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
				String option = bufferedReader.readLine();

				//Handle two options
				if (option.equals("1")){
					System.out.println("Please input the file path:");
					String filepath = bufferedReader.readLine();
					Automotive automotive = fileIO.buildAutoObjectFromPropertyFile(filepath);
					if (automotive!=null)
						carModelOptionsIO.sendAutomotive(automotive);
				}
				if (option.equals("2")){
					System.err.println("Fetching");
					carModelOptionsIO.configAutomotive();
				}
					
				//Wait for the process finish
				while (true){
					if (carModelOptionsIO.methodFinished())
						break;
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {}
				}
			}

		} catch (AutoException | IOException e) {
			e.printStackTrace();
		}
	}
}
