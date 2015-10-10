package com.baiqiz.project1.driver;

import com.baiqiz.project1.adapter.BuildAuto;
import com.baiqiz.project1.adapter.CreateAuto;
import com.baiqiz.project1.adapter.PickAuto;
import com.baiqiz.project1.adapter.UpdateAuto;

public class Test {
	public static void main(String[] args) {		
		CreateAuto createAuto = new BuildAuto();
		createAuto.BuildAuto("input");
		createAuto.PrintAuto("Focus Wagon ZTW");
		
		/* static Automotive is shared */
		UpdateAuto updateAuto = new BuildAuto();
		
		/* Tests for exceptions */
		createAuto.BuildAuto("input2"); // Not exist, will ask for new filename
		createAuto.PrintAuto("Focus Wagon ZTW");
		createAuto.BuildAuto("input_error1"); // Wrong format, will ask for new filename
		createAuto.PrintAuto("Focus Wagon ZTW");
		
		/* Option "Newname" not exist, will ask for new Option. 
		 * Please try "Black" */
		updateAuto.updateOptionPrice("Focus Wagon ZTW", "Color", "Newname", 500);
		createAuto.PrintAuto("Focus Wagon ZTW");

		/* OptionSet "XXX" not exist, will ask for new Option. 
		 * Please try "Airbag" */
		updateAuto.updateOptionSetName("Focus Wagon ZTW", "XXX", "NewAirbag");
		createAuto.PrintAuto("Focus Wagon ZTW");
		
		/* Transmission not selected, will ask for a selection
		 * Please try "Standard" */
		PickAuto pickAuto = new BuildAuto();
		pickAuto.pickOption("Focus Wagon ZTW", "Color","Red");
		pickAuto.pickOption("Focus Wagon ZTW", "NewAirbag","Selected");
		
		createAuto.PrintAuto("Focus Wagon ZTW");
		System.out.println(pickAuto.getTotalPrice("Focus Wagon ZTW"));
		
	}
}
