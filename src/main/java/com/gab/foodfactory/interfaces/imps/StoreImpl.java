package com.gab.foodfactory.interfaces.imps;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gab.foodfactory.interfaces.Product;
import com.gab.foodfactory.interfaces.Store;

public class StoreImpl implements Store {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private BlockingQueue<Product> store;

	public StoreImpl(int capacity) {
		this.store = new ArrayBlockingQueue<>(capacity);
	}

	@Override
	public void put(Product product) {
		try {
			store.put(product);
//			System.out.print("*" + ((ProductImpl)product).getName()+ "*");
		} catch (InterruptedException e) {
			logger.error("trouble putting into the store", e);
		}
	}

	@Override
	public Product take() {
		try {
			return store.take();
		} catch (InterruptedException e) {
			logger.error("trouble taking from the store", e);
		}
		return null;
	}

	@Override
	public void take(Product product) {
//		System.out.print(" - " + ((ProductImpl)product).getName()+ " out " + "of store ");
		store.remove(product);
	}

}
