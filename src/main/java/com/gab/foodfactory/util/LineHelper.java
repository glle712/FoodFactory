package com.gab.foodfactory.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.gab.foodfactory.interfaces.imps.AssemblyLineStageImpl;
import com.gab.foodfactory.interfaces.imps.ProductImpl;

@Service
public class LineHelper {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Async
	public void outputLine(AssemblyLineStageImpl line) {

		ProductImpl product;
		try {
			while ((product = line.getOut().take()) != null) {
				System.out.println("[out:"+line.getName()+":"+product.getName() +"]");
			}
		} catch (InterruptedException e) {
			logger.error("trouble during output");
		}
	}


	@Async
	public void checkOrder(AssemblyLineStageImpl line) {

		ProductImpl product;
		int counter = 1;
		try {
			while ((product = line.getOut().take()) != null) {
				if(product.getName().equals(line.getName()+(counter++))) {
					System.out.print("ok");
				} else {
					System.out.println("NOOOOO");
					System.out.println("NOOOOO");
					System.out.println("NOOOOO");
				}
			}
		} catch (InterruptedException e) {
			logger.error("trouble during check order");
		}
	}

}
