package com.gab.foodfactory.solution;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CookingLineEnd {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Async
	public void backToAssemblyLine(AssemblyLineStage assemblyLine, BlockingQueue<Future<Product>> cookingLine) {
		while (true) {
			try {
				Future<Product> isCooking = cookingLine.take();
				Product cooked = isCooking.get();
				assemblyLine.putAfter(cooked);
			} catch (InterruptedException | ExecutionException e) {
				logger.error("trouble at the end of the line", e);
			}
		}
	}

}
