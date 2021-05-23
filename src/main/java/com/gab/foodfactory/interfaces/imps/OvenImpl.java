package com.gab.foodfactory.interfaces.imps;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.gab.foodfactory.exceptions.CapacityExceededException;
import com.gab.foodfactory.interfaces.Oven;
import com.gab.foodfactory.interfaces.Product;

public class OvenImpl implements Oven {

	private double size;
	
	private boolean isOn;

	public boolean isOn() {
		return isOn;
	}

	private List<Product> cooking = Collections.synchronizedList(new ArrayList<Product>());

	private double availableSpace() {
		return size - cooking.stream().map(Product::size).reduce(0d, Double::sum);
	}

	public OvenImpl(double size) {
		this.size = size;
	}

	@Override
	public double size() {
		return this.size;
	}

	@Override
	public synchronized void put(Product product) throws CapacityExceededException {
		if (product.size() > this.availableSpace())
			throw new CapacityExceededException();
		this.cooking.add(product);
		System.out.print("{" + ((ProductImpl)product).getName() + ":"+product.size()+"|"+this.availableSpace()+"}");
	}

	@Override
	public void take(Product product) {
		this.cooking.remove(product);
		if(size()==availableSpace()) {
//			System.out.print("oven empty, out " + ((ProductImpl)product).getName() + " ");
			this.turnOff();
		}
	}

	/**
	 * This method is not implemented as the Oven does not really modify the state
	 * of the Product as per this code challenge
	 */
	@Override
	public void turnOn() {
		// we cook whatever is in the oven
		if(!this.isOn) {
			this.isOn = true;
//			System.out.print("oven on ");
		}
	}

	@Override
	public void turnOn(Duration duration) {
		// we cook whatever is in the oven for {duration} time.
		new Thread(() -> {
			this.turnOn();
			try {
				Thread.sleep(duration.toMillis());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.turnOff();
		}).start();
	}

	/**
	 * This method is not implemented as the Oven does not really modify the state
	 * of the Product as per this code challenge
	 */
	@Override
	public void turnOff() {
		// we don't cook anymore
		if(isOn) {
			isOn=false;
//			System.out.print("oven off ");
//			System.out.print(" empty: "+(size()==availableSpace())+" ");
		}
	}

}
