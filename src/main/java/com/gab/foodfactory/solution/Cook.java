package com.gab.foodfactory.solution;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gab.foodfactory.interfaces.Oven;
import com.gab.foodfactory.interfaces.Product;
import com.gab.foodfactory.interfaces.imps.ProductImpl;

/**
 * 
 * @author gabriel
 *
 */
@Service
public class Cook {

	private ExecutorService executor = Executors.newCachedThreadPool();

	@Autowired
	OvenLine ovenLine;

	@Autowired
	private StorageLine storage;

	public synchronized Future<Product> cook(Product product) throws InterruptedException {

		// pick number for the oven line
		UUID myNumber = ovenLine.takeNumber();

		// put in storage, storage returns future product, storage halts if full
		System.out.println(String.format("put %s into storage", ((ProductImpl) product).getName()));
		storage.put(product);

		return executor.submit(() -> {
//			System.out.println(String.format("%s waiting in line", ((ProductImpl) product).getName()));
			Oven oven = null;
			while (true) {
//				System.out.println("checking - " + myNumber);
				if (ovenLine.imNext(myNumber)) {
					
					storage.take(product);
					System.out.println(String.format("take %s from storage", ((ProductImpl) product).getName()));

//					System.out.println(String.format("%s waiting for oven", ((ProductImpl) product).getName()));
//					System.out.println("got in - " + myNumber);

					while ((oven = ovenLine.startCookingInOven(product)) == null) {
						Thread.sleep(500);
					}
					// oven is mine, tell ovenLine to accept next
//					System.out.println(String.format("%s got oven", ((ProductImpl) product).getName()));
//					System.out.println("got oven - " + myNumber);
					break;
				}
				Thread.sleep(200);
			}
//			System.out.println("end - " + myNumber);
			ovenLine.imServed(myNumber);
//			System.out.println(String.format("%s cooking", ((ProductImpl) product).getName()));
			// cook the product and return it
			Thread.sleep(product.cookTime().toMillis());
			oven.take(product);
			System.out.println("" + ((ProductImpl)product).getName() + " cooked");
			return product;

		});

	}

}
