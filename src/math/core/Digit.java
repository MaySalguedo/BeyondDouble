package math.core;

import math.core.Notationer;

/**

	Turns any primitive given number or a real number on a string into a full notationed readable number

	<p>Example of use:</p>
	<pre>{@code

		Digit n = new Digit(1.23456E7);

		System.out.print(n);//12,345,600

	}</pre>

	@author Dandelion
	@version 0.0.2
	@since v0.0.1

*/

public class Digit extends Notationer{

	/**

		The {@code String integerPart} represents the integer part of the number.

	*/

	public final String integerPart;

	/**

		The {@code String decimalPart} represents the decimal part of the number.

	*/

	public final String decimalPart;

	/**

		The {@code boolean isNegative} represents if the number is either negative 
		or positive, {@code true} for negative, {@code false} for positive.

	*/

	public final boolean isNegative;

	/**

		The {@code boolean notation} represents if the number is either using the 
		decimal point notation or the dot decimal notation, {@code true} for DPN, {@code false} for DDN.

	*/

	private boolean notation;

	/**

		Creates an instance of {@code Digit} with a {@code double} value.

		@param n Real number
		@see math.core.Notationer#validateAndNormalize(StringBuilder)

	*/

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

	/**

		Creates an instance of {@code Digit} with a number value as a {@code String}.

		@param n Real number as a {@code String}
		@exception IllegalArgumentException if {@code String n} is either {@code null} or empty.
		@see math.core.Notationer#validateAndNormalize(StringBuilder)

	*/

	public Digit(String n){

		if (n!=null ? n.isEmpty() : false){

			throw new IllegalArgumentException("Invalid Number");

		}

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

	/**

		Implements the {@code String format()} function from extended class Notationer to print the number on a 
		readable notation using the attributes {@code this.integerPart}, {@code this.decimalPart} and {@code this.notation} 
		as paramethers for such function.
		
		The return values will be either negative or positive depending on the state of the attribute {@code this.isNegative}.

		@return String Returns a full notationed readable number.
		@see math.core.Notationer#format(String, String, boolean)
		@since 0.0.1

	*/

	@Override
	public String toString(){

		return (this.isNegative ? "-" : "")+Digit.format(this.integerPart, this.decimalPart, this.notation);

	}

}