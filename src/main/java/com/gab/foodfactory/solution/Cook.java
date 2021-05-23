package com.gab.foodfactory.solution;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gab.foodfactory.interfaces.Oven;
import com.gab.foodfactory.interfaces.Product;
import com.gab.foodfactory.interfaces.Store;

/**
 * 
 * @author gabriel
 *
 */
@Service
public class Cook {

	private ExecutorService executor = Executors.newCachedThreadPool();

	@Autowired
	OvenAndStoreProvider provider;

	@Autowired
	private StorageLine storageLine;

	/**
	 * 
	 * This method takes a Product and asks the provider for an Oven with available
	 * space in it for this Product. If the oven is found it will return a Future
	 * promise of the Product cooked in the Oven for the correct amount of time. In
	 * the event of not finding an available oven it asks the provider for a Store
	 * where to store the Product and polls every second (this is arbitrary) again
	 * for an available Oven. The {put} method of the store is blocking so if the
	 * Store is full the thread halts.
	 * 
	 * @param product
	 * @return a Product Future
	 * @throws InterruptedException
	 */
	
	public Future<Product> cook(Product product) throws InterruptedException {

		// put in storage, storage returns future product
		
		storageLine.put(product);
		
		
		
		// schedule with oven
		// check with oven if I am next
		// block until oven sez I am next
		
		// take 
		
		// tell oven to take from storage
		
		
		
		// check oven

		// oven y?

		// start putting in the storage line, if the storage line is full then the line
		// halts

		// submit to an executor the following task and return it as future:

		// wait for the next available oven if nobody's already waiting
		// take the product from the storage line
		// put the product in the oven and cook it

		provider.
		
		
		Oven oven = provider.askForOven(product);

		// no oven, put in store and wait for it
		if (oven == null) {

			storageLine.put(product); // blocking
			while (oven == null) {
				Thread.sleep(1000);
				oven = provider.putInNextAvalable(product);
			}

//			return storeAndWaitForOvenAndStartCooking(store, product);
		}

		return this.startCooking(oven, product);

	}

//	private Future<Product> storeAndWaitForOvenAndStartCooking(Store store, Product product)
//			throws InterruptedException {
//		// let cook for appropriate cookTime 
//		
//		// las heladeras no son un future son otra cola
//		return executor.submit(() -> {
//			Oven oven = null;
//			while (oven == null) {
//				Thread.sleep(1000);
//				
//				// soy el proximo!
//				oven = provider.imNext(product);
//			}
//			store.take(product);
//			Thread.sleep(product.cookTime().toMillis());
//			oven.take(product);
//			return product;
//		});
//	}

	private Future<Product> startCooking(Oven oven, Product product) throws InterruptedException {
		// let cook for appropriate cookTime
		return executor.submit(() -> {
			Thread.sleep(product.cookTime().toMillis());
			oven.take(product);
			return product;
		});
	}
}
