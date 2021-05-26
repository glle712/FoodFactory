package com.gab.foodfactory.solution;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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

	private Map<Product, Integer> map = Collections.synchronizedMap(new HashMap<Product, Integer>());

	private int index = 0;

	private Store[] stores;

	@PostConstruct
	public void init() {
		stores = new Store[M];
		for (int i = 0; i < M; i++) {
			stores[i] = new StoreImpl(C);
		}
	}

	public synchronized void put(Product product) {
		int index = cycle();
		map.put(product, index);
		stores[index].put(product);
	}

	private int cycle() {
		this.index = (this.index >= M - 1) ? 0 : this.index + 1;
		return 0;
	}

	public void take(Product product) {
		stores[map.get(product)].take(product);
	}

}
