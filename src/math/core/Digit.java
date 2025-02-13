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
	@version 0.0.3
	@since v0.0.1

*/

public class Digit extends Notationer implements Comparable<Digit>{

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
		@since v0.0.1

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
		@since v0.0.1

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

	/**

		Implements the {@code int compareTo(Object)} function from implemented class Comparable to compare the numbers.

		<p>Example of use:</p>
		<pre>{@code

			Digit n = new Digit(1.23456E7);
			Digit m = new Digit("89");

			int result = n.compareTo(m);

		}</pre>

		The return values will be {@code result} = 1

		@param n Digit instance.
		@return int Returns an x value ranging from {@literal -1<x<1} where x belongs to integers. Where 0 if both are 
		equal, 1 if {@literal n>m} and -1 if {@literal n<m}.
		@see java.lang.Comparable#compareTo(Object)
		@see math.core.Digit#compareIntegerParts(String, String)
		@see math.core.Digit#compareDecimalParts(String, String)
		@since 0.0.3

	*/

	public int compareTo(Digit n){

		//Case 2: Either both negative or positive
		int multiplier = this.isNegative ? -1 : 1;

		//Case 1: Diferent Signs
		if (this.isNegative!=n.isNegative) return multiplier;

		int integersCompared = compareIntegerParts(this.integerPart, n.integerPart);

		if (integersCompared!=0) return multiplier * integersCompared;

		int decimalsCompared = compareDecimalParts(this.decimalPart, n.decimalPart);

		if (decimalsCompared!=0) return multiplier * decimalsCompared;

		return 0;

	}

	/**

		Compares two positive integer numbers as two {@code String}.

		<p>Example of use:</p>
		<pre>{@code

			int result = compareIntegerParts("45", "50");

		}</pre>

		The return values will be {@code result} = -1

		@param a Integer number as a {@code String}
		@param b Integer number as a {@code String}

		@return int Returns an x value ranging from {@literal -1<x<1} where x belongs to integers. Where 0 if both are 
		equal, 1 if {@literal n>m} and -1 if {@literal n<m}.
		@since 0.0.3

	*/

	protected int compareIntegerParts(String a, String b){

		int aLength = a.length();
		int bLength = b.length();

		//Case 1: Diferent length
		if (aLength!=bLength) return aLength>bLength ? 1 : -1;

		//Case 2: Same length

		for (int i=0; i<aLength; i++){

			char c1 = a.charAt(i);
			char c2 = b.charAt(i);

			if (c1!=c2) return c1>c2 ? 1 : -1;

		}

		return 0;

	}

	/**

		Compares two decimal parts as two positive integer numbers as two {@code String}.

		<p>Example of use:</p>
		<pre>{@code

			int result = compareDecimalParts("5", "52");

		}</pre>

		The return values will be {@code result} = 1

		@param a Integer number as a {@code String}
		@param b Integer number as a {@code String}

		@return int Returns an x value ranging from {@literal -1<x<1} where x belongs to integers. Where 0 if both are 
		equal, 1 if {@literal n<m} and -1 if {@literal n>m}.
		@since 0.0.3

	*/

	protected int compareDecimalParts(String a, String b){

		int aLength = a.length();
		int bLength = b.length();

		int minLength = aLength<bLength ? aLength : bLength;

		for (int i=0; i<minLength; i++){

			char c1 = a.charAt(i);
			char c2 = b.charAt(i);

			if (c1!=c2) return c1>c2 ? 1 : -1;

		}

		if (aLength==bLength) return 0;

		return aLength>bLength ? 1 : -1;

	}

}