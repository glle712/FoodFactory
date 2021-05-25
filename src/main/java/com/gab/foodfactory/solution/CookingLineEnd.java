package com.gab.foodfactory.solution;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.gab.foodfactory.interfaces.AssemblyLineStage;
import com.gab.foodfactory.interfaces.imps.AssemblyLineStageImpl;
import com.gab.foodfactory.interfaces.imps.ProductImpl;
import com.gab.foodfactory.interfaces.Product;

/**
 * This class represents the end of the cooking queue. Each Product that comes
 * out of the cooking queue is considered cooked and ready for the next step in
 * the AsseblyLine. This class has the responsibility of taking products from
 * the cooking queue when they are ready and putting them back in the
 * AssemblyLineStage for the next steps to take over.
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
				System.out.println(String.format("putting %s in %s line", ((ProductImpl)cooked).getName(),
						((AssemblyLineStageImpl)assemblyLine).getName()));
				assemblyLine.putAfter(cooked);
			} catch (InterruptedException | ExecutionException e) {
				logger.error("trouble at the end of the line", e);
			}
		}
	}

}
