package com.baiqiz.project1.test;

import com.baiqiz.project1.model.Automotive;
import com.baiqiz.project1.util.FileIO;

public class TestClass {
/*
 * Test Create & Read Operation
 */
	public static boolean test1() {		
		/* Test for Automotive Class */
		Automotive automotive = new Automotive(2, "Test", 10000);
		if (automotive.getOptionSetSize()!=2)
			return false;
		if ((double)automotive.getBasePrice()!=10000)
			return false;
		if (!automotive.getName().equals("Test"))
			return false;
		
		/* Test for OptionSet Class */		  
		automotive.setOptionSet(0, 3, "Set0");
		
		if (!automotive.getOptionSetName(0).equals("Set0"))
			return false;
		if (automotive.getOptionSize(0)!=3)
			return false;
		
		/* Test for Option Class */		  
		automotive.setOption(0, 0, "Option0", 100);
		if (!automotive.getOptionName(0, 0).equals("Option0"))
			return false;
		if (automotive.getOptionPrice(0, 0)!=100)
			return false;

		return true;
	}

/*
 * Test FileIO & Update Operation
 */
	public static boolean test2() {		
		FileIO fileIO = new FileIO();
		Automotive automotive = fileIO.buildAutoObject("input");
		
		/* Test for OptionSet Class */		  
		if (!automotive.getOptionSetName(0).equals("Color")) /* before */
			return false;
		if (automotive.getOptionSize(0)!=3)
			return false;

		automotive.updateOptionSet("Color", 1, "Set0");
		
		if (!automotive.getOptionSetName(0).equals("Set0")) /* after */
			return false;
		if (automotive.getOptionSize(0)!=1)
			return false;
		

		/* Test for OptionSet Class */		
		automotive = fileIO.buildAutoObject("input");
		if (!automotive.getOptionName(0, 0).equals("Black")) /* before */
			return false;
		if (automotive.getOptionPrice(0, 0)!=1000)
			return false;

		automotive.updateOption("Black", "Color", "Option", 1);
		
		if (!automotive.getOptionName(0, 0).equals("Option")) /* after */
			return false;
		if (automotive.getOptionPrice(0, 0)!=1)
			return false;
		
		return true;
	}
	
/*
 * Test Delete Operation
 */
	public static boolean test3() {		
		FileIO fileIO = new FileIO();
		Automotive automotive = fileIO.buildAutoObject("input");
		
		/* Test for OptionSet Class */
		/* before : OptionSet[0] = "Color", 3 */
		if (!automotive.getOptionSetName(0).equals("Color")) /* before */
			return false;
		if (automotive.getOptionSize(0)!=3)
			return false;

		automotive.deleteOptionSet("Color");
		
		if (automotive.getOptionSet(0)!=null) /* after */
			return false;
				

		/* Test for OptionSet Class */		
		automotive = fileIO.buildAutoObject("input");
		/* before : Option[0] = "black", 1000 */
		if (!automotive.getOptionName(0, 0).equals("Black"))
			return false;
		if (automotive.getOptionPrice(0, 0)!=1000)
			return false;

		automotive.deleteOption("Black", "Color");
		
		if (automotive.getOption(0, 0)!=null) /* after */
			return false;
		
		return true;

	}
	
	public static void main(String[] args) {
		System.out.println("Testing Create & Read Operation..");
		if (test1())
			System.out.println("Passed.");
		else
			System.out.println("FAIL!");
		
		System.out.println("Testing Create & Read Operation..");
		if (test2())
			System.out.println("Passed.");
		else
			System.out.println("FAIL!");
		
		System.out.println("Testing Delete Operation..");
		if (test3())
			System.out.println("Passed.");
		else
			System.out.println("FAIL!");
	}	
}
