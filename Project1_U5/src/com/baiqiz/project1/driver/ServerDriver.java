package com.baiqiz.project1.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.baiqiz.project1.adapter.BuildAuto;
import com.baiqiz.project1.adapter.CreateAuto;
import com.baiqiz.project1.adapter.PickAuto;
import com.baiqiz.project1.adapter.UpdateAuto;
import com.baiqiz.project1.exception.AutoException;
import com.baiqiz.project1.model.Automotive;
import com.baiqiz.project1.scale.EditOptions;
import com.baiqiz.project1.network.BuildCarModelOptions;
import com.baiqiz.project1.util.FileIO;
import com.baiqiz.project1.util.Properties;


public class ServerDriver {

	public static void main(String[] args) {
		//Keep listening
		BuildCarModelOptions buildCarModelOptions = new BuildCarModelOptions();
		buildCarModelOptions.start();
		System.out.println("Server waiting..");
	}
}
