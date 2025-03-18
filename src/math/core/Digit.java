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
	@version v0.0.6
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

		if (n!=null ? n.isEmpty() : false) throw new IllegalArgumentException("Invalid Number");

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

		Creates an instance of {@code Digit} with all the attributes required except the decimal part.

		@param integerPart Integer part of the number.
		@param isNegative The {@code boolean isNegative} represents if the number is either negative 
		or positive.
		@param notation The {@code boolean notation} represents if the number is either using the decimal point 
		notation or the dot decimal notation.

		@since v0.0.6

	*/

	protected Digit(String integerPart, boolean isNegative, boolean notation){

		this.integerPart = Digit.trimZeros(integerPart);
		this.decimalPart = "";
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

	@Override public String toString(){

		return (this.isNegative ? "-" : "")+Digit.format(this.integerPart, this.decimalPart, this.notation);

	}

	/**

		Implements the {@code int compareTo(Object)} function from implemented class Comparable to compare the instances.

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

	@Override public int compareTo(Digit n){

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

		Implements the {@code int compareTo(Object)} function from implemented class Comparable to compare the instances.

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

		Implements the {@code int compareTo(Object)} function from implemented class Comparable to compare the instances.

		@param n int value.
		@return int Returns an x value ranging from {@literal -1<x<1} where x belongs to integers. Where 0 if both are 
		equal, 1 if {@literal n>m} and -1 if {@literal n<m}.
		@see java.lang.Comparable#compareTo(Object)
		@see math.core.Digit#compareTo(Digit)
		@since v0.0.4

	*/

	public int compareTo(int n){

		return this.compareTo(new Digit(n+"", "", this.isNegative, this.notation));

	}

	/**

		Compares a {@code Digit} instance to zero.

		<p>Example of use:</p>
		<pre>{@code

			Digit n = new Digit(1);

			int result = n.compareToZero();

		}</pre>

		The return values will be {@code result} = 1

		@return int Returns an x value ranging from {@literal -1<x<1} where x belongs to integers. Where 0 if is equals to 
		zero, 1 if {@literal n>0} and -1 if {@literal n<0}.
		@since v0.0.5

	*/

	public int compareToZero(){

		if (this.isNegative) return -1;

		if (this.integerPart.matches("0") && this.decimalPart.matches("")) return 0;

		return 1;

	}

	/**

		Compares a {@code Digit} instance to one.

		<p>Example of use:</p>
		<pre>{@code

			Digit n = new Digit(1);

			int result = n.compareToOne();

		}</pre>

		The return values will be {@code result} = 0

		@return int Returns an x value ranging from {@literal -1<x<1} where x belongs to integers. Where 0 if is equals to 
		one, 1 if {@literal n>1} and -1 if {@literal n<1}.
		@since v0.0.5

	*/

	public int compareToOne(){

		if (this.isNegative || (this.integerPart.matches("0") && !this.decimalPart.matches(""))) return -1;

		if (this.integerPart.matches("1") && this.decimalPart.matches("")) return 0;

		return 1;

	}

	/**

		Compares a {@code Digit} instance to minus one.

		<p>Example of use:</p>
		<pre>{@code

			Digit n = new Digit(1);

			int result = n.compareToMinusOne();

		}</pre>

		The return values will be {@code result} = 1

		@return int Returns an x value ranging from {@literal -1<x<1} where x belongs to integers. Where 0 if is equals to 
		minus one, 1 if {@literal n>-1} and -1 if {@literal n<-1}.
		@since v0.0.5

	*/

	public int compareToMinusOne(){

		if (!this.isNegative || (this.integerPart.matches("0") && !this.decimalPart.matches(""))) return 1;

		if (this.integerPart.matches("1") && this.decimalPart.matches("")) return 0;

		return -1;

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
		@see math.core.Digit#addition(int, String[], boolean)
		@since v0.0.4

	*/

	public Digit add(Digit other){

		int isThisZero = this.compareToZero();

		if (isThisZero==0) return other;

		int isOtherZero = other.compareToZero();

		if (isOtherZero==0) return this;

		Digit thisAbsolute = this.abs();
		Digit otherAbsolute = other.abs();

		if (isThisZero<0 && isOtherZero<0) return thisAbsolute.add(otherAbsolute).negate();

		int absolutesComapred = thisAbsolute.compareTo(otherAbsolute);

		if (absolutesComapred<0) return other.add(this);

		if (absolutesComapred>0 && (isThisZero<isOtherZero)) return this.negate().add(other.negate()).negate();

		int maxDecimalLength = this.decimalPart.length()>other.decimalPart.length() ? this.decimalPart.length() : other.decimalPart.length();

		String fullResult = this.addition(0, new String[] {

			this.integerPart + this.padZerosRight(this.decimalPart, maxDecimalLength),
			other.integerPart + this.padZerosRight(other.decimalPart, maxDecimalLength)

		}, !other.isNegative);

		int length = fullResult.length();

		String integerResult = fullResult.substring(0, length - maxDecimalLength);
		String decimalResult = fullResult.substring(length - maxDecimalLength);

		return new Digit(integerResult, decimalResult, false, this.notation);

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

		Multiplies two {@code Digit} numbers logicly and sequentially.

		<p>Example of use:</p>
		<pre>{@code

			Digit n = new Digit("25");
			Digit m = new Digit(5);

			Digit result = n.multiply(m);

		}</pre>

		The return values will be {@code result} = 125

		@param other Digit instance.
		@return Digit Result from the multiplication of the two instance.
		@see math.core.Digit#negate()
		@see math.core.Digit#multiplication(String, String)
		@since v0.0.5

	*/

	public Digit multiply(Digit other){

		if (this.compareToZero()==0 || other.compareToZero()==0) return new Digit(0);

		int isThisOne = this.compareToOne();

		if (isThisOne==0) return other;

		int isOtherOne = other.compareToOne();

		if (isOtherOne==0) return this;

		int isThisMinusOne = this.compareToMinusOne();

		if (isThisMinusOne==0) return other.negate();

		int isOtherMinusOne = other.compareToMinusOne();

		if (isOtherMinusOne==0) return this.negate();

		int maxDecimalLength = this.decimalPart.length() + other.decimalPart.length();

		String fullResult = this.multiplication(this.integerPart+this.decimalPart, other.integerPart+other.decimalPart);

		int length = fullResult.length();

		String integerResult = fullResult.substring(0, length - maxDecimalLength);
		String decimalResult = fullResult.substring(length - maxDecimalLength);

		return new Digit(integerResult, decimalResult, this.isNegative!=other.isNegative, this.notation);

	}

	/**

		Divides two {@code Digit} numbers logicly and sequentially.

		<p>Example of use:</p>
		<pre>{@code

			Digit n = new Digit("7");
			Digit m = new Digit(4);

			Digit result = n.multiply(m);

		}</pre>

		The return values will be {@code result} = 1.75

		@param other Digit instance.
		@param presition Decimal presition.
		@exception ArithmeticException if {@code Digit} other is zero.
		@return Digit Result from the division of the two instance.
		@see math.core.Notationer#trimZeros(String)
		@see math.core.Digit#division(String, String, long)

		@since v0.0.6

	*/

	public Digit divide(Digit other, long presition){

		if (other.compareToZero()==0) throw new ArithmeticException("Cannot divide by zero.");

		int decimalLeft = this.decimalPart.length() - other.decimalPart.length();

		String dividend = Digit.trimZeros(this.integerPart+this.decimalPart);
		String divisor = Digit.trimZeros(other.integerPart+other.decimalPart);

		if (decimalLeft>0){

			dividend+= "0".repeat(decimalLeft);

		}else{

			divisor+= "0".repeat(-decimalLeft);

		}
	
		String[] result = this.division(dividend, divisor, presition);

		return new Digit(result[0], result[1], this.isNegative!=this.isNegative, this.notation);

	}

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

			int result = this.compareDecimalParts("5", "52");

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

	private String padZerosRight(String str, int length){

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

	private String padZerosLeft(String str, int length){

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
		@param integers {@String} array made out of integer numbers.
		@param isAddition Is addition or substraction as {@code boolean}.

		@return String Result from the addition of the integers.
		@see math.core.Notationer#trimZeros(String)
		@see math.core.Digit#padZerosLeft(String, int)
		@since v0.0.5

	*/

	private String addition(int index, String[] integers, boolean isAddition){

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

		return trimZeros(trimResult);

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
		@see math.core.Digit#addition(int, String[], boolean)
		@since v0.0.5

	*/

	private String multiplication(String thisInteger, String otherInteger){

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
		@see math.core.Digit#compareIntegerParts(String, String)
		@see math.core.Digit#calculateDecimalPartForDivision(String, String, long)
		@since v0.0.6

	*/

	private String[] division(String dividend, String divisor, long presition){

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

		@see math.core.Digit#compareIntegerParts(String, String)
		@see math.core.Digit#addition(int, String[], boolean)

		@since v0.0.6

	*/

	private String calculateDecimalPartForDivision(String remainder, String divisor, long presition){

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