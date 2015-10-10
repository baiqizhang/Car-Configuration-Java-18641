package com.baiqiz.project1.scale;

import com.baiqiz.project1.adapter.BuildAuto;
import com.baiqiz.project1.adapter.CreateAuto;
import com.baiqiz.project1.adapter.UpdateAuto;

public class EditOptions extends Thread {
	private UpdateAuto updateAuto;
	private CreateAuto createAuto;
	private String modelName;
	private int sleepTime;
	private static int available=1;
	private static Object lock;
	
	public EditOptions(String name,String modelName,int sleepTime) {
		super(name);
		this.updateAuto = new BuildAuto();
		this.createAuto = new BuildAuto();
		this.modelName = modelName;
		this.sleepTime=sleepTime;
		if (lock==null)
			lock = new Object();
	}

	
	/* Increase the price of "Black" Option */
	
	public void addPrice() {
		/* Mutual exclusion lock */
		synchronized (lock) {
			while (available==0){
				try {
						lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		available=0;
		float price=updateAuto.getOptionPrice(modelName, "Color", "Black");
		
		/* random sleep time between read and write */
		try {
			sleep((long)(1000*Math.random()));
		} catch (InterruptedException e) {
			System.out.println("Interrupted!");
		}

		updateAuto.updateOptionPrice(modelName, "Color", "Black", price+sleepTime);
		
		/* random sleep time between read and write */
		try {
			sleep((long)(this.sleepTime*Math.random()));
		} catch (InterruptedException e) {
			System.out.println("Interrupted!");
		}
		
		float newprice=updateAuto.getOptionPrice(modelName, "Color", "Black");
		
		/* check the if newprice is exactly oldprice + 100
		 * if not, sync is not handled properly.
		 */
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(this.getName());
		stringBuilder.append(":");
		stringBuilder.append(price);
		stringBuilder.append("->");
		stringBuilder.append(newprice);
		
		if (newprice==price+sleepTime)
			stringBuilder.append(" Correct!");
		else
			stringBuilder.append(" Wrong!");
		System.out.println(stringBuilder);
		createAuto.PrintAuto(modelName);
		
		available=1;
		synchronized (lock) {
			lock.notifyAll();
			try {
				/* wait a short time to let other threads obtain the lock */
				lock.wait(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void run() {
		for (int times=0;times<5;times++){
			addPrice();
		}
	}
}
