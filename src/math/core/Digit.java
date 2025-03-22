package math.core;

import java.lang.Number;
import java.lang.NumberFormatException;

import math.core.Notationer;
import math.core.Operationer;

/**

	Turns any primitive given number or a real number as string into a full notationed readable number.

	Digit's class presition is arbitrary, which means it can represent numbers as big as the memory allows it.

	<p>Example of use:</p>
	<pre>{@code

		Digit n = new Digit(1.23456E7);

		System.out.print(n);//12,345,600

	}</pre>

	@author Dandelion
	@version v0.0.7
	@since v0.0.1

*/

public class Digit extends Number implements Comparable<Digit>{

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

		Notation manager from class {@code Notationer}.

	*/

	protected final Notationer notationManager = new Notationer();

	/**

		Operation manager from class {@code Operationer}.

	*/

	protected final Operationer operationManager = new Operationer();

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

		String[] parts = this.notationManager.validateAndNormalize(number);
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
		@see math.core.Notationer#trimZeros(String)
		@since v0.0.6

	*/

	protected Digit(String integerPart, boolean isNegative, boolean notation){

		this.integerPart = this.notationManager.trimZeros(integerPart);
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

		return (this.isNegative ? "-" : "")+this.notationManager.format(this.integerPart, this.decimalPart, this.notation);

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
		@see math.core.Operationer#compareIntegerParts(String, String)
		@see math.core.Operationer#compareDecimalParts(String, String)
		@since v0.0.3s

	*/

	@Override public int compareTo(Digit n){

		//Case 2: Either both negative or positive
		int multiplier = this.isNegative ? -1 : 1;

		//Case 1: Diferent Signs
		if (this.isNegative!=n.isNegative) return multiplier;

		int integersCompared = this.operationManager.compareIntegerParts(this.integerPart, n.integerPart);

		if (integersCompared!=0) return multiplier * integersCompared;

		int decimalsCompared = this.operationManager.compareDecimalParts(this.decimalPart, n.decimalPart);

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

		@return Digit Returns the negated value for the {@code Digit} instance.
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

		@return Digit Returns the absolute value for the {@code Digit} instance.
		@see math.core.Digit#Digit(String, String, boolean, boolean)
		@since v0.0.4

	*/

	public Digit abs(){

		return new Digit(this.integerPart, this.decimalPart, false, this.notation);

	}

	/**

		Calculates the {@code Digit} instance's inverse value.

		<p>Example of use:</p>
		<pre>{@code

			Digit n = new Digit(2);
			Digit m = n.inverse();

		}</pre>

		The return values will be {@code m} = 0.5

		@param presition Decimal presition.
		@return Digit Returns the inverse value for the {@code Digit} instance.
		@see math.core.Digit#Digit(String, boolean, boolean)
		@see math.core.Digit#divide(Digit, long)
		@since v0.0.7

	*/

	public Digit inverse(long presition){

		return new Digit("1", false, this.notation).divide(this, presition);

	}

	/**

		Calculates the {@code Digit} instance's inverse value with the presition set at 128.

		@return Digit Returns the inverse value for the {@code Digit} instance.
		@see math.core.Digit#inverse(long)
		@since v0.0.7

	*/

	public Digit inverse(){

		return this.inverse(128);

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

		Multiplies two numbers logicly and sequentially.

		@param other double value.
		@return Digit Result from the multiplication.
		@see math.core.Digit#multiply(Digit)
		@since v0.0.7

	*/

	public Digit multiply(double other){

		return this.multiply(new Digit(other));

	}

	/**

		Divides two numbers logicly and sequentially.

		@param other double value.
		@param presition Decimal presition.
		@return Digit Result from the division.
		@see math.core.Digit#divide(Digit, long)
		@since v0.0.7

	*/

	public Digit divide(double other, long presition){

		return this.divide(new Digit(other), presition);

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
		@see math.core.Operationer#padZerosRight(String, int)
		@see math.core.Operationer#addition(int, String[], boolean)
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

		String fullResult = this.operationManager.addition(0, new String[] {

			this.integerPart + this.operationManager.padZerosRight(this.decimalPart, maxDecimalLength),
			other.integerPart + this.operationManager.padZerosRight(other.decimalPart, maxDecimalLength)

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
		@see math.core.Operationer#multiplication(String, String)
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

		String fullResult = this.operationManager.multiplication(this.integerPart+this.decimalPart, other.integerPart+other.decimalPart);

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

			Digit result = n.divide(m);

		}</pre>

		The return values will be {@code result} = 1.75

		@param other Digit instance.
		@param presition Decimal presition.
		@exception ArithmeticException if {@code Digit} other is zero.
		@return Digit Result from the division of the two instance.
		@see math.core.Notationer#trimZeros(String)
		@see math.core.Operationer#division(String, String, long)

		@since v0.0.6

	*/

	public Digit divide(Digit other, long presition){

		if (other.compareToZero()==0) throw new ArithmeticException("Cannot divide by zero.");

		int decimalLeft = - this.decimalPart.length() + other.decimalPart.length();

		String dividend = this.notationManager.trimZeros(this.integerPart+this.decimalPart);
		String divisor = this.notationManager.trimZeros(other.integerPart+other.decimalPart);

		if (decimalLeft>0){

			dividend+= "0".repeat(decimalLeft);

		}else{

			divisor+= "0".repeat(-decimalLeft);

		}

		String[] result = this.operationManager.division(dividend, divisor, presition);

		return new Digit(result[0], result[1], this.isNegative!=this.isNegative, this.notation);

	}

	/**

		Returns the value of the specified number as a byte. The numeric value represented by this object after conversion to type byte.

		@exception NumberFormatException Throws by the method parseByte from class {@code Byte}.
		@return The numeric value represented by this object after conversion to type byte.
		@see java.lang.Number#byteValue()
		@since v0.0.7

	*/

	public byte byteValue() throws NumberFormatException{

		return Byte.parseByte(this.integerPart);

	}

	/**

		Returns the value of the specified number as a double. The numeric value represented by this object after conversion to type double.

		@exception NumberFormatException Throws by the method parseDouble from class {@code double}.
		@return The numeric value represented by this object after conversion to type double.
		@see java.lang.Number#doubleValue()
		@since v0.0.7

	*/

	public double doubleValue() throws NumberFormatException{

		return Double.parseDouble(this.integerPart+(

			!this.decimalPart.matches("") ? "."+this.decimalPart : ""

		));

	}

	/**

		Returns the value of the specified number as a float. The numeric value represented by this object after conversion to type float.

		@exception NumberFormatException Throws by the method parseFloat from class {@code float}.
		@return The numeric value represented by this object after conversion to type float.
		@see java.lang.Number#floatValue()
		@since v0.0.7

	*/

	public float floatValue() throws NumberFormatException{

		return Float.parseFloat(this.integerPart+(

			!this.decimalPart.matches("") ? "."+this.decimalPart : ""

		));

	}

	/**

		Returns the value of the specified number as a int. The numeric value represented by this object after conversion to type int.

		@exception NumberFormatException Throws by the method parseInt from class {@code Integer}.
		@return The numeric value represented by this object after conversion to type int.
		@see java.lang.Number#intValue()
		@since v0.0.7

	*/

	public int intValue() throws NumberFormatException{

		return Integer.parseInt(this.integerPart);

	}

	/**

		Returns the value of the specified number as a long. The numeric value represented by this object after conversion to type long.

		@exception NumberFormatException Throws by the method parseLong from class {@code Long}.
		@return The numeric value represented by this object after conversion to type long.
		@see java.lang.Number#longValue()
		@since v0.0.7

	*/

	public long longValue() throws NumberFormatException{

		return Long.parseLong(this.integerPart);

	}

	/**

		Returns the value of the specified number as a short. The numeric value represented by this object after conversion to type short.

		@exception NumberFormatException Throws by the method parseShort from class {@code Short}.
		@return The numeric value represented by this object after conversion to type short.
		@see java.lang.Number#shortValue()
		@since v0.0.7

	*/

	public short shortValue() throws NumberFormatException{

		return Short.parseShort(this.integerPart+(

			!this.decimalPart.matches("") ? "."+this.decimalPart : ""

		));

	}

}