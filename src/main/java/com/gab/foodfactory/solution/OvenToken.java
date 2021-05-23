package com.gab.foodfactory.solution;

import com.gab.foodfactory.interfaces.Oven;

public class OvenToken {

	private Oven oven = null;
	private boolean isMyTurn = false;

	private OvenToken(Oven oven) {
		this.oven = oven;
		this.isMyTurn = true;
	}

	public Oven getOven() {
		return oven;
	}

	public boolean isMyTurn() {
		return isMyTurn;
	}

	public static OvenToken oven(Oven o) {
		return new OvenToken(o);
	}
}
