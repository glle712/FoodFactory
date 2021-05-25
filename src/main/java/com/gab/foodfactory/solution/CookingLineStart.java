package com.gab.foodfactory.solution;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.gab.foodfactory.interfaces.AssemblyLineStage;
import com.gab.foodfactory.interfaces.Product;

/**
 * 
 * @author gabriel
 *
 */
@Component
public class CookingLineStart {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private Cook cook;

	@Async
	public void fromAssemblyLine(AssemblyLineStage assemblyLine, BlockingQueue<Future<Product>> cookingLine) {
		while (true) {
			try {
				Product product = assemblyLine.take();
				Future<Product> cooking = cook.cook(product);
				cookingLine.put(cooking);
			} catch (InterruptedException e) {
				logger.error("trouble at the start of the line", e);
			}
		}
	}

}
