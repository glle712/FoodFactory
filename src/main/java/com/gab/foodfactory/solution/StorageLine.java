package com.gab.foodfactory.solution;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gab.foodfactory.interfaces.Product;
import com.gab.foodfactory.interfaces.Store;
import com.gab.foodfactory.interfaces.imps.StoreImpl;

@Service
public class StorageLine {

	BlockingQueue<Product> storageLine = new LinkedBlockingQueue<>();
	
	@Value("${stores}")
	private int M;
	@Value("${store.capacity}")
	private int C;
	

	private Store[] stores;
	
	@PostConstruct
	public void init() {
		stores = new Store[M];
		for (int i = 0; i < M; i++) {
			stores[i] = new StoreImpl(C);
			// daisy chain
			if(i>0) {
				final int index = i;
				new Thread(()->{while (true) stores[index].put(stores[index-1].take());}).start();
			}
		}
	}
	
	public void put(Product product) {
		stores[0].put(product);
	}
	
	public Product take() {
		return stores[M-1].take();
	}
	

}
