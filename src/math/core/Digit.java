package math.core;

/**
 *
 * @author Dandelion
 * 
 */

import math.core.Notationer;

public class Digit extends Notationer{

	private final String integerPart;
	private final String decimalPart;
	public final boolean isNegative;

	private boolean notation;

	public Digit(double n){

		StringBuilder number = new StringBuilder(n+"");

		this.isNegative = number.charAt(0)=='-';

		if (this.isNegative){

			number.deleteCharAt(0);

		}

		String[] parts = Digit.validateAndNormalize(number);
		this.integerPart = parts[0];
		this.decimalPart = parts[1];
		this.notation = true;

	}

	public Digit(String n){

		StringBuilder number = new StringBuilder(n);

		this.isNegative = number.charAt(0)=='-';

		if (this.isNegative){

			number.deleteCharAt(0);

		}

		String[] parts = Digit.validateAndNormalize(number);
		this.integerPart = parts[0];
		this.decimalPart = parts[1];
		this.notation = true;

	}

	@Override
	public String toString(){

		return (this.isNegative ? "-" : "")+Digit.format(this.integerPart, this.decimalPart, this.notation);

	}

}