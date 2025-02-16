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
	@version v0.0.4
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

		The {@code boolean notation} represents if the number is either using the decimal point 
		notation or the dot decimal notation, {@code true} for DPN, {@code false} for DDN.

	*/

	public boolean notation;

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

		boolean isNegativeBackUp = number.charAt(0)=='-';

		if (isNegativeBackUp){

			number.deleteCharAt(0);

		}

		String[] parts = Digit.validateAndNormalize(number);
		this.integerPart = parts[0];
		this.decimalPart = parts[1];
		this.isNegative = (this.integerPart.equals("0") && this.decimalPart.equals("")) ? false : isNegativeBackUp;
		this.notation = true;

	}

	/**

		Creates an instance of {@code Digit} with a {@code double} value.

		@param n Real number.
		@see math.core.Notationer#validateAndNormalize(StringBuilder)
		@since v0.0.1

	*/

	public Digit(double n){

		this(n+"");

	}

	/**

		Creates an instance of {@code Digit} with two number values as {@code String}.

		@param integerPart Integer part of the number.
		@param decimalPart Integer part of the number.
		@see math.core.Notationer#validateAndNormalize(StringBuilder)
		@since v0.0.4

	*/

	public Digit(String integerPart, String decimalPart){

		this(integerPart+"."+decimalPart);

	}

	/**

		Creates an instance of {@code Digit} with all the attributes required.

		@param integerPart Integer part of the number.
		@param decimalPart Integer part of the number.
		@param isNegative The {@code boolean isNegative} represents if the number is either negative 
		or positive.
		@param notation The {@code boolean notation} represents if the number is either using the decimal point 
		notation or the dot decimal notation.

		@since v0.0.4

	*/

	protected Digit(String integerPart, String decimalPart, boolean isNegative, boolean notation){

		this.integerPart = integerPart;
		this.decimalPart = decimalPart;
		this.isNegative = isNegative;
		this.notation = notation;

	}

	/**

		Implements the {@code String format()} function from extended class Notationer to print the number on a 
		readable notation using the attributes {@code this.integerPart}, {@code this.decimalPart} and {@code this.notation} 
		as paramethers for such function.
		
		The return values will be either negative or positive depending on the state of the attribute {@code this.isNegative}.

		@return String Returns a full notationed readable number.
		@see math.core.Notationer#format(String, String, boolean)
		@since v0.0.1

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
		@since v0.0.3

	*/

	@Override
	public int compareTo(Digit n){

		//Case 2: Either both negative or positive
		int multiplier = this.isNegative ? -1 : 1;

		//Case 1: Diferent Signs
		if (this.isNegative!=n.isNegative) return multiplier;

		int integersCompared = this.compareIntegerParts(this.integerPart, n.integerPart);

		if (integersCompared!=0) return multiplier * integersCompared;

		int decimalsCompared = this.compareDecimalParts(this.decimalPart, n.decimalPart);

		if (decimalsCompared!=0) return multiplier * decimalsCompared;

		return 0;

	}

	/**

		Implements the {@code int compareTo(Object)} function from implemented class Comparable to compare the numbers.

		@param n double value.
		@return int Returns an x value ranging from {@literal -1<x<1} where x belongs to integers. Where 0 if both are 
		equal, 1 if {@literal n>m} and -1 if {@literal n<m}.
		@see java.lang.Comparable#compareTo(Object)
		@see math.core.Digit#compareTo(Digit)
		@since v0.0.4

	*/

	public int compareTo(double n){

		return this.compareTo(new Digit(n));

	}

	/**

		Implements the {@code int compareTo(Object)} function from implemented class Comparable to compare the numbers.

		@param n int value.
		@return int Returns an x value ranging from {@literal -1<x<1} where x belongs to integers. Where 0 if both are 
		equal, 1 if {@literal n>m} and -1 if {@literal n<m}.
		@see java.lang.Comparable#compareTo(Object)
		@see math.core.Digit#compareTo(Digit)
		@since v0.0.4

	*/

	protected int compareTo(int n){

		return this.compareTo(new Digit(n+"", "", this.isNegative, this.notation));

	}

	/**

		Creates a Digit instance with the {@code boolean isNegative} attribute negated.

		<p>Example of use:</p>
		<pre>{@code

			Digit n = new Digit(1);
			Digit m = n.negate();

		}</pre>

		The return values will be {@code m} = -1

		@return Digit Returns the negated value for the Digit instance.
		@see math.core.Digit#Digit(String, String, boolean, boolean)
		@since v0.0.4

	*/

	public Digit negate(){

		return new Digit(this.integerPart, this.decimalPart, !this.isNegative, this.notation);

	}

	/**

		Creates a Digit instance with the {@code boolean isNegative} attribute on false.

		<p>Example of use:</p>
		<pre>{@code

			Digit n = new Digit(-1);
			Digit m = n.abs();

		}</pre>

		The return values will be {@code m} = 1

		@return Digit Returns the absolute value for the Digit instance.
		@see math.core.Digit#Digit(String, String, boolean, boolean)
		@since v0.0.4

	*/

	public Digit abs(){

		return new Digit(this.integerPart, this.decimalPart, false, this.notation);

	}

	/**

		Adds two numbers logicly and sequentially.

		@param other double value.
		@return Digit Result from the addition of the two values.
		@see math.core.Digit#add(Digit)
		@since v0.0.4

	*/

	public Digit add(double other){

		return this.add(new Digit(other));

	}

	/**

		Subtracts two numbers logicly and sequentially.

		@param other double value.
		@return Digit Result from the subtraction of the two values.
		@see math.core.Digit#add(Digit)
		@since v0.0.4

	*/

	public Digit subtract(double other){

		return this.subtract(new Digit(other));

	}

	/**

		Subtracts two {@code Digit} numbers logicly and sequentially.

		@param other Digit instance.
		@return Digit Result from the subtraction of the two intance.
		@see math.core.Digit#add(Digit)
		@since v0.0.4

	*/

	public Digit subtract(Digit other){

		return this.add(other.negate());

	}

	/**

		Adds two {@code Digit} numbers logicly and sequentially.

		<p>Example of use:</p>
		<pre>{@code

			Digit n = new Digit("-123.45");
			Digit m = new Digit(678.9);

			Digit result = n.add(m);

		}</pre>

		The return values will be {@code result} = 555.45

		@param other Digit instance.
		@return Digit Result from the addition of the two instance.
		@see math.core.Digit#negate()
		@see math.core.Digit#abs()
		@see math.core.Digit#padZerosRight(String, int)
		@since v0.0.4

	*/

	public Digit add(Digit other){

		/*

			Note:

			Let n be the current Digit instance
			Let m be the other Digit intance

		*/

		int isThisZero = this.compareTo(0);

		//Case I: n equals to zero
		if (isThisZero==0) return other;

		int isOtherZero = other.compareTo(0);

		//Case II: m equals to zero
		if (isOtherZero==0) return this;

		Digit thisAbsolute = this.abs();
		Digit otherAbsolute = other.abs();

		//Case III: Both lower than zero
		if (isThisZero<0 && isOtherZero<0) return thisAbsolute.add(otherAbsolute).negate();

		int absolutesComapred = thisAbsolute.compareTo(otherAbsolute);

		//Case IV: |n|<|m|
		if (absolutesComapred<0) return other.add(this);

		//Case V: |n|>|m| & n<m
		if (absolutesComapred>0 && (isThisZero<isOtherZero)) return this.negate().add(other.negate()).negate();

		int maxDecimalLength = this.decimalPart.length()>other.decimalPart.length() ? this.decimalPart.length() : other.decimalPart.length();

		String thisFullNumber = this.integerPart + this.padZerosRight(this.decimalPart, maxDecimalLength);
		String otherFullNumber = other.integerPart + this.padZerosRight(other.decimalPart, maxDecimalLength);

		StringBuilder result = new StringBuilder();
		int carryIn = 0;
		int carryOut = 0;
		int maxLength = thisFullNumber.length()>otherFullNumber.length() ? thisFullNumber.length() : otherFullNumber.length();

		for (int i=maxLength-1; i>=0; i--){

			int thisDigit = (thisFullNumber.charAt(i) - '0') - carryOut;
			int otherDigit = otherFullNumber.charAt(i) - '0';

			int sum = 0;

			if (!other.isNegative){

				sum = thisDigit + otherDigit + carryIn;
				carryIn = sum/10;
				result.insert(0, sum%10);

			}else{

				carryOut = 0;
				carryIn = 0;

				if ((thisDigit)<otherDigit){

					carryIn = 10;
					carryOut = 1;

				}

				sum = thisDigit - otherDigit + carryIn;
				result.insert(0, sum);

			}

		}

		if (carryIn>0){

			result.insert(0, carryIn);

		}

		String fullResult = result.toString();
		int length = fullResult.length();
		String integerResult = fullResult.substring(0, length - maxDecimalLength);
		String decimalResult = fullResult.substring(length - maxDecimalLength);

		return new Digit(integerResult, decimalResult, false, this.notation);

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
		@since v0.0.3

	*/

	private int compareIntegerParts(String a, String b){

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
		@since v0.0.3

	*/

	private int compareDecimalParts(String a, String b){

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

	/**

		Add zeros to the {@code String} result until the length matches the lenght paramether.

		<p>Example of use:</p>
		<pre>{@code

			String result = padZerosRight("5", 3);

		}</pre>

		The return values will be {@code result} = 500

		@param str Integer number as a {@code String}
		@param length Intented lenght for the {@code String} result.

		@return String Returns String that matches the lenght paramether.
		@since v0.0.4

	*/

	private String padZerosRight(String str, int length){

		int strLength = str.length();

		if (strLength>=length){

			return str;

		}

		return new StringBuilder(str).append("0".repeat(length-strLength)).toString();

	}

}