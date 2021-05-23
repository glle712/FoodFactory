package com.gab.foodfactory.interfaces.imps;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gab.foodfactory.interfaces.AssemblyLineStage;
import com.gab.foodfactory.interfaces.Product;

public class AssemblyLineStageImpl extends LinkedBlockingQueue<Product> implements AssemblyLineStage {

	private String name;

	public String getName() {
		return name;
	}

	/**
	 * We add a name constructor to the assembly line stage in order to
	 * differentiate each line from the others when verifying the processed results
	 * 
	 * @param name
	 */
	public AssemblyLineStageImpl(String name) {
		this.name = name;
	}

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final long serialVersionUID = 1L;

	private BlockingQueue<Product> in = new LinkedBlockingQueue<>();

	private BlockingQueue<ProductImpl> out = new LinkedBlockingQueue<>();

	public BlockingQueue<ProductImpl> getOut() {
		return out;
	}

	@Override
	public void put(Product p) throws InterruptedException {
		this.in.put(p);
	}

	@Override
	public void putAfter(Product product) {
		try {
			// this output is to verify that the product order and the line to which it belongs is correct
//			logger.info("product [" + ((ProductImpl) product).getName() + "] coming out to line " + this.name);
			this.out.put((ProductImpl)product);
		} catch (InterruptedException e) {
			logger.error("trouble putting Product back on the assembly line", e);
		}
	}

	@Override
	public Product take() {
		try {
			return this.in.take();
		} catch (InterruptedException e) {
			logger.error("trouble taking Product from the assembly line", e);
			return null;
		}
	}

}
