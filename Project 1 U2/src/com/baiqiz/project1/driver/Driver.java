package com.baiqiz.project1.driver;

import com.baiqiz.project1.adapter.BuildAuto;
import com.baiqiz.project1.adapter.CreateAuto;
import com.baiqiz.project1.adapter.PickAuto;
import com.baiqiz.project1.adapter.UpdateAuto;


public class Driver {

	public static void main(String[] args) {		
		CreateAuto createAuto = new BuildAuto();
		createAuto.BuildAuto("input");
		createAuto.PrintAuto("Focus Wagon ZTW");
		
		/* static Automotive is shared */
		UpdateAuto updateAuto = new BuildAuto();
		
		/* update, Red: $0->$2000, "Color" -> "NewColor" */
		updateAuto.updateOptionPrice("Focus Wagon ZTW", "Color", "Red", 2000);
		updateAuto.updateOptionSetName("Focus Wagon ZTW", "Color", "NewColor");
		
		/* pick, NewColor:Red, Airbag: Selected Transmission: Automatic */
		PickAuto pickAuto = new BuildAuto();
		pickAuto.pickOption("Focus Wagon ZTW", "NewColor","Red");
		pickAuto.pickOption("Focus Wagon ZTW", "Airbag","Selected");
		pickAuto.pickOption("Focus Wagon ZTW", "Transmission","Automatic");
		
		/* print total price */
		createAuto.PrintAuto("Focus Wagon ZTW");
		System.out.print("Focus: Red + Airbag + Automatic = ");
		System.out.println(pickAuto.getTotalPrice("Focus Wagon ZTW"));
		
		System.out.println("\n======== Auto 2 =======\n");
		
		
		/* add another model */
		createAuto.BuildAuto("input2");		
		
		updateAuto.updateOptionPrice("Toyota Prius", "Color", "Black", 100);
		
		pickAuto.pickOption("Toyota Prius", "Color","Black");
		pickAuto.pickOption("Toyota Prius", "Airbag","None");
		pickAuto.pickOption("Toyota Prius", "Transmission","Standard");
		createAuto.PrintAuto("Toyota Prius");
		System.out.print("Prius: Black + None + Standard = ");
		System.out.println(pickAuto.getTotalPrice("Toyota Prius"));
		
	}
}
