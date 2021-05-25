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

	@Async
	public void accept(AssemblyLineStage assemblyLine) {

		BlockingQueue<Future<Product>> cookingLine = new LinkedBlockingQueue<>();

		start.fromAssemblyLine(assemblyLine, cookingLine);

		end.backToAssemblyLine(assemblyLine, cookingLine);

	}

}
