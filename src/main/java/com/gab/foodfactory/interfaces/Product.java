package com.gab.foodfactory.interfaces;

import java.time.Duration;

/**
 * Implementations of this class should take care of overriding the necessary
 * methods of the Object class to allow for the use of Collections in the
 * different implementations of Oven and Store.
 */
public interface Product {
	/**
	 * The size that this product physically occupies in cm2
	 * 
	 * @return
	 */
	double size();

	/**
	 * This is the duration that this product should be cooked for.
	 */
	Duration cookTime();
}
