package com.gab.foodfactory.interfaces.imps;

import java.time.Duration;

import com.gab.foodfactory.interfaces.Product;

public class ProductImpl implements Product {

	private double size;
	private Duration cookTIme;
	private String name;

	public String getName() {
		return name;
	}

	/**
	 * We added the property {name} to the Product implementation in order to
	 * properly implement the overridden hashCode and equals methods. Those methods
	 * overrides are necessary when having to control the order of collections of
	 * this object.
	 * 
	 * @param name
	 * @param size
	 * @param cookTime
	 */
	public ProductImpl(String name, double size, Duration cookTime) {
		this.size = size;
		this.cookTIme = cookTime;
		this.name = name;
	}

	@Override
	public double size() {
		return this.size;
	}

	@Override
	public Duration cookTime() {
		return this.cookTIme;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cookTIme == null) ? 0 : cookTIme.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(size);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductImpl other = (ProductImpl) obj;
		if (cookTIme == null) {
			if (other.cookTIme != null)
				return false;
		} else if (!cookTIme.equals(other.cookTIme))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(size) != Double.doubleToLongBits(other.size))
			return false;
		return true;
	}

}
