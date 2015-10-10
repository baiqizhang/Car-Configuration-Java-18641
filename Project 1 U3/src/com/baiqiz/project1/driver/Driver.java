package com.baiqiz.project1.driver;

import com.baiqiz.project1.adapter.BuildAuto;
import com.baiqiz.project1.adapter.CreateAuto;
import com.baiqiz.project1.adapter.PickAuto;
import com.baiqiz.project1.adapter.UpdateAuto;
import com.baiqiz.project1.scale.EditOptions;


public class Driver {

	public static void main(String[] args) {		
		CreateAuto createAuto = new BuildAuto();
		createAuto.BuildAuto("input");
		createAuto.PrintAuto("Focus Wagon ZTW");
		
		/* Create 3 threads that all try to add the price of an option.
		 * If synchronization is not handled correctly, the price will not
		 * increase by exactly 100, and the thread will complain.   
		 */
		
		EditOptions thread1 = new EditOptions("Thread1", "Focus Wagon ZTW",100);
		EditOptions thread2 = new EditOptions("Thread2", "Focus Wagon ZTW",50);
		EditOptions thread3 = new EditOptions("Thread3", "Focus Wagon ZTW",10);
		thread1.start();
		thread2.start();
		thread3.start();
	}
}
