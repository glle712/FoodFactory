package com.gab.foodfactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;

import com.gab.foodfactory.interfaces.imps.AssemblyLineStageImpl;
import com.gab.foodfactory.solution.CookingLine;
import com.gab.foodfactory.util.LineHelper;
import com.gab.foodfactory.util.ProductProducer;

/**
 *
 * Spring Boot application starter class
 */
@SpringBootApplication
@EnableAsync
public class Application {

	@Autowired
	private CookingLine cookingLine;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void init() {

		// name, quantity, size, cookTimeMs
		createLine("bread", 100, 1, 5000);
		
		createLine("cake", 50, 4, 8000);
		
		
	}
	
	private void createLine(String name, int quantity, double size, long cookTimeMs) {
		
		// setup
		AssemblyLineStageImpl line = new AssemblyLineStageImpl(name);
		
		// fill line
		new Thread(new ProductProducer(line, quantity, name, size, cookTimeMs)).start();

		cookingLine.accept(line); // challenge

		//asyncHelper.checkOrder(line); // verification

		//asyncHelper.outputLine(line); // output
		
	}

}
