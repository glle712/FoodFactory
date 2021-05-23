package com.gab.foodfactory.util;

import java.time.Duration;
import java.util.concurrent.BlockingQueue;

import com.gab.foodfactory.interfaces.Product;
import com.gab.foodfactory.interfaces.imps.AssemblyLineStageImpl;
import com.gab.foodfactory.interfaces.imps.ProductImpl;

/**
 * This class simulates a Product Production line
 * 
 * 
 * @author gabriel
 *
 */
public class ProductProducer implements Runnable {

	private BlockingQueue<Product> line;
	private String productName;
	private double size;
	private long cookingTimeInMilliseconds;
	private int quantity;

	public ProductProducer(AssemblyLineStageImpl queue, int quantity, String productName, double size, long cookingTimeInMilliseconds) {
		this.line = queue;
		this.productName = productName;
		this.size = size;
		this.cookingTimeInMilliseconds = cookingTimeInMilliseconds;
		this.quantity = quantity;
	}

	@Override
	public void run() {
		// producing messages
		for (int i = 1; i <= quantity; i++) {
			try {
				line.put(new ProductImpl(this.productName + i, this.size, Duration.ofMillis(this.cookingTimeInMilliseconds)));
			} catch (Exception e) {
				System.out.println("Exception:" + e);
			}
		}

	}

}
