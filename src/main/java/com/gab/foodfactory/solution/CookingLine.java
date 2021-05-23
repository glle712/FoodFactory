package com.gab.foodfactory.solution;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.gab.foodfactory.interfaces.AssemblyLineStage;
import com.gab.foodfactory.interfaces.Product;

@Service
public class CookingLine {

	@Autowired
	CookingLineStart start;

	@Autowired
	CookingLineEnd end;

	/**
	 * This asynchronous method exposes the cooking stage. The cooking process is
	 * modeled as a BlockingQueue so as to keep the FIFO order of each line.
	 * 
	 * @param assemblyLine
	 */
	@Async
	public void accept(AssemblyLineStage assemblyLine) {

		BlockingQueue<Future<Product>> cookingLine = new LinkedBlockingQueue<>();

//		new Thread(() -> start.fromAssemblyLine(assemblyLine, cookingLine)).start();
//
//		new Thread(() -> end.backToAssemblyLine(assemblyLine, cookingLine)).start();

		
		start.fromAssemblyLine(assemblyLine, cookingLine);

		end.backToAssemblyLine(assemblyLine, cookingLine);

	}

}
