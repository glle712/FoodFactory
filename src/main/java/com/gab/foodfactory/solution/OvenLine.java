package com.gab.foodfactory.solution;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gab.foodfactory.exceptions.CapacityExceededException;
import com.gab.foodfactory.interfaces.Oven;
import com.gab.foodfactory.interfaces.Product;
import com.gab.foodfactory.interfaces.imps.OvenImpl;

@Service
public class OvenLine {

	@Value("${ovens}")
	private int N;
	@Value("${oven.size}")
	private int S;

	private Oven[] ovens;

	private BlockingQueue<UUID> line = new LinkedBlockingQueue<UUID>();

	@PostConstruct
	public void init() {
		ovens = new Oven[N];
		for (int i = 0; i < N; i++) {
			ovens[i] = new OvenImpl(S);
		}
	}

	public synchronized UUID takeNumber() {
		UUID number = UUID.randomUUID();
		line.add(number);
		return number;
	}

	public synchronized Oven startCookingInOven(Product product) {
		for (Oven oven : ovens) {
//			try each oven until we find one that has space
			try {
				oven.turnOn();
				oven.put(product);
				return oven;
			} catch (CapacityExceededException e) {
				// this oven doesn't have room, let's try the next one
				continue;
			}
		}

		// no room
		return null;

	}

	public synchronized boolean imNext(UUID number) {
		return line.peek() != null && line.peek().equals(number);
	}

	public synchronized void imServed(UUID number) {
		line.remove(number);
	}

}
