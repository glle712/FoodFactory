package com.gab.foodfactory.solution;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gab.foodfactory.exceptions.CapacityExceededException;
import com.gab.foodfactory.interfaces.Oven;
import com.gab.foodfactory.interfaces.Product;
import com.gab.foodfactory.interfaces.Store;
import com.gab.foodfactory.interfaces.imps.OvenImpl;
import com.gab.foodfactory.interfaces.imps.StoreImpl;

@Service
public class OvenAndStoreProvider {

	@Value("${ovens}")
	private int N;
	@Value("${oven.size}")
	private int S;
	@Value("${stores}")
	private int M;
	@Value("${store.capacity}")
	private int C;

	private Oven[] ovens;
	private Store[] stores;

	private int storeIndex = -1;

	private BlockingQueue<OvenToken> ovenLine = new 
	
	
	@PostConstruct
	public void init() {
		ovens = new Oven[N];
		stores = new Store[M];
		for (int i = 0; i < N; i++) {
			ovens[i] = new OvenImpl(S);
		}
		for (int i = 0; i < M; i++) {
			stores[i] = new StoreImpl(C);
		}
	}

	/**
	 * This method finds the next available Oven with enough room for the Product,
	 * when it finds it it turns it on (even if it was on already), puts the product
	 * inside and returns it to the cook for it to deal with it. If no Oven is found
	 * null is returned.
	 * 
	 * This method is synchronized too guarantee exclusive access to each Oven.
	 * 
	 * @param product
	 * @return the turned on Oven with the product inside.
	 */
	public synchronized Future<Oven> startCookingInOven(Product product) {

		
		
		
		
		for (Oven oven : ovens) {
//			try each oven until we find one that has space
			try {
				oven.turnOn();
				oven.put(product);
				return OvenToken.oven(oven);
			} catch (CapacityExceededException e) {
				// this oven doesn't have room, let's try the next one
				continue;
			}
		}
		
		// no room, product is next
		return null;

	}

//	/**
//	 * This method simply cycles through all the Store instances in order to try and
//	 * balance the usage.
//	 * 
//	 * This method is synchronized too guarantee exclusive access to each Store
//	 * 
//	 * @return the next available Store
//	 */
//	public synchronized Store getStore() {
//		incrementIndex();
//		return stores[storeIndex];
//	}
//
//	private void incrementIndex() {
//		storeIndex = (storeIndex + 1 == M) ? 0 : storeIndex + 1;
//	}

//	@Async
//	public void store(Product product) {
//		storageLine.put(product);
////		
////		
////		
////		Store store = getStore(); // moves index
////		
////		
////		
////		store.put(product); // potentially blocking
////		
////		
////
//////		put in storage line
////		Future<Product> storageLine.nextInLine(store, product);
////		
////		oven
////		
////		
////		
////		// TODO Auto-generated method stub
////		return null;
//	}

}
