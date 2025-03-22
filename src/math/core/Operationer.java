package math.core;

import math.core.Notationer;

/**

	Manages the operations and results of the four basic mathemathic operations. 
	Intended to be only used as a stakeholder manager for its functions.

	<p>Example of use:</p>
	<pre>{@code

		public class MyClass{

			public Operationer operationManager = new Operationer();

		}

	}</pre>

	@author Dandelion
	@version v0.0.1
	@since v0.0.9

*/

public class Operationer{

	/**

		Empty constructor.

	*/

	public Operationer(){}

	/**

		Compares two positive integer numbers as two {@code String}.

		<p>Example of use:</p>
		<pre>{@code

			int result = this.compareIntegerParts("45", "50");

		}</pre>

		The return values will be {@code result} = -1

		@param a Integer number as a {@code String}
		@param b Integer number as a {@code String}

		@return int Returns an x value ranging from {@literal -1<x<1} where x belongs to integers. Where 0 if both are 
		equal, 1 if {@literal n>m} and -1 if {@literal n<m}.
		@since v0.0.3

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

			int result = this.compareDecimalParts("5", "52");

		}</pre>

		The return values will be {@code result} = 1

		@param a Integer number as a {@code String}
		@param b Integer number as a {@code String}

		@return int Returns an x value ranging from {@literal -1<x<1} where x belongs to integers. Where 0 if both are 
		equal, 1 if {@literal n<m} and -1 if {@literal n>m}.
		@since v0.0.3

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

	/**

		Add zeros to the {@code String} result to the right until the length matches the length paramether.

		<p>Example of use:</p>
		<pre>{@code

			String result = this.padZerosRight("5", 3);

		}</pre>

		The return values will be {@code result} = "500"

		@param str Integer number as a {@code String}
		@param length Intented length for the {@code String} result.

		@return String Returns String that matches the length paramether.
		@since v0.0.4

	*/

	protected String padZerosRight(String str, int length){

		int strLength = str.length();

		if (strLength>=length){

			return str;

		}

		return new StringBuilder(str).append("0".repeat(length-strLength)).toString();

	}

	/**

		Add zeros to the {@code String} result to the left until the length matches the length paramether.

		<p>Example of use:</p>
		<pre>{@code

			String result = this.padZerosLeft("5", 3);

		}</pre>

		The return values will be {@code result} = "005"

		@param str Integer number as a {@code String}
		@param length Intented length for the {@code String} result.

		@return String Returns String that matches the length paramether.
		@since v0.0.5

	*/

	protected String padZerosLeft(String str, int length){

		int strLength = str.length();

		if (strLength>=length){

			return str;

		}

		return new StringBuilder(str).insert(0, "0".repeat(length-strLength)).toString();

	}

	/**

		Adds integers as {@code String} logicly and sequentially.

		<p>Example of use:</p>
		<pre>{@code

			String result = this.addition(0, new String[] {

				"1", "2", "3"

			}, true);

		}</pre>

		The return values will be {@code result} = "7"

		@param index Index to where in the array it will start adding the numbers.
		@param integers {@code String} array made out of integer numbers.
		@param isAddition Is addition or substraction as {@code boolean}.

		@return String Result from the addition of the integers.
		@see math.core.Notationer#trimZeros(String)
		@see math.core.Operationer#padZerosLeft(String, int)
		@since v0.0.5

	*/

	protected String addition(int index, String[] integers, boolean isAddition){

		if (integers.length==1 || index==integers.length-1) return integers[integers.length-1];

		String thisFullNumber = integers[index];
		String otherFullNumber = integers[index + 1];

		int maxLength = thisFullNumber.length()>otherFullNumber.length() ? thisFullNumber.length() : otherFullNumber.length();

		thisFullNumber = padZerosLeft(thisFullNumber, maxLength);
		otherFullNumber = padZerosLeft(otherFullNumber, maxLength);

		StringBuilder result = new StringBuilder();
		int carryIn = 0;
		int carryOut = 0;

		for (int i=maxLength-1; i>=0; i--){

			int thisDigit = (thisFullNumber.charAt(i) - '0') - carryOut;
			int otherDigit = otherFullNumber.charAt(i) - '0';

			int sum = 0;

			if (isAddition){

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

		int remainders = integers.length - (index + 2);

		String trimResult;

		if (integers.length==2 || remainders==0){

			trimResult = result.toString();

		}else if (remainders==1){

			trimResult = this.addition(0, new String[] {

				result.toString(), integers[index + 2]

			}, isAddition);

		}else{

			trimResult = this.addition(0, new String[] {

				result.toString(), this.addition(index + 2, integers, isAddition)


			}, isAddition);

		}

		return new Notationer().trimZeros(trimResult);

	}

	/**

		Multiplies integers as {@code String} logicly and sequentially.

		<p>Example of use:</p>
		<pre>{@code

			String result = this.multiplication("12", "3");

		}</pre>

		The return values will be {@code result} = "36"

		@param thisInteger First integer as {@code String}.
		@param otherInteger Second integer as {@code String}.

		@return String Result from the multiplication of the integers.
		@see math.core.Operationer#addition(int, String[], boolean)
		@since v0.0.5

	*/

	protected String multiplication(String thisInteger, String otherInteger){

		String[] integerColumn = new String[otherInteger.length()];

		StringBuilder zero = new StringBuilder("");

		for (int f=integerColumn.length-1; f>=0; f--){

			StringBuilder result = new StringBuilder();
			int carryIn = 0;
			int multi = 0;

			int otherDigit = otherInteger.charAt(f) - '0';

			for (int c=thisInteger.length()-1; c>=0; c--){

				int thisDigit = thisInteger.charAt(c) - '0';
				
				multi = (thisDigit * otherDigit) + carryIn;
				carryIn = multi/10;

				result.insert(0, multi%10);

			}

			if (carryIn>0){

				result.insert(0, carryIn);

			}

			result.append(zero);
			zero.append("0");

			integerColumn[(integerColumn.length - 1) - f] = result.toString();

		}

		return this.addition(0, integerColumn, true);

	}

	/**

		Divides integers as {@code String} logicly and sequentially.

		<p>Example of use:</p>
		<pre>{@code

			String[] result = this.division("7", "5", 20);

		}</pre>

		The return values will be {@code result[0]} = "1" and {@code result[1]} = "4"

		@param dividend Integer dividend as {@code String}.
		@param divisor Integer Divisor as {@code String}.
		@param presition Decimal presition as {@code long}.

		@return String[] Result from the division of the integers.
		@see math.core.Operationer#compareIntegerParts(String, String)
		@see math.core.Operationer#calculateDecimalPartForDivision(String, String, long)
		@since v0.0.6

	*/

	protected String[] division(String dividend, String divisor, long presition){

		int isGreaterThanDivivisor = this.compareIntegerParts(dividend, divisor);

		long integerPartCounter = 0;

		while(isGreaterThanDivivisor>0){

			dividend = this.addition(0, new String[] {

				dividend.toString(), divisor.toString()

			}, false);

			integerPartCounter++;

			isGreaterThanDivivisor = this.compareIntegerParts(dividend, divisor);

		}

		if (isGreaterThanDivivisor==0){ 

			return new String[] {(integerPartCounter + 1)+"", ""};

		}else{

			return new String[] {integerPartCounter+"", this.calculateDecimalPartForDivision(dividend+"0", divisor, presition)};

		}

	}

	/**

		Calculates the decimal part of a division using the remainder and divisor as {@code String} logicly and sequentially.

		<p>Example of use:</p>
		<pre>{@code

			String result = this.calculateDecimalPartForDivision("20", "5", 20);

		}</pre>

		The return values will be {@code result} = "4"

		@param remainder Integer remainder as {@code String}.
		@param divisor Integer Divisor as {@code String}.
		@param presition Decimal presition as {@code long}.

		@return String Decimal part for the division.

		@see math.core.Operationer#compareIntegerParts(String, String)
		@see math.core.Operationer#addition(int, String[], boolean)

		@since v0.0.6

	*/

	protected String calculateDecimalPartForDivision(String remainder, String divisor, long presition){

		StringBuilder result = new StringBuilder("");

		for (long i=0; i<presition; i++){

			int isGreaterThanRemainder = this.compareIntegerParts(remainder, divisor);

			byte counter = 0;

			while(isGreaterThanRemainder>=0){

				remainder = this.addition(0, new String[] {

					remainder.toString(), divisor.toString()

				}, false);

				counter++;

				isGreaterThanRemainder = this.compareIntegerParts(remainder, divisor);

			}

			if (!remainder.matches("0")){

				result.append(counter);
				remainder+= "0";

			}else{

				if (counter<10){

					result.append(counter);

					return result.toString();

				}else{

					return this.addition(0, new String[] {result.toString(), "1"}, true);

				}

			}

		}

		return result.toString();

	}

}