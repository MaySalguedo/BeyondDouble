package math.core;

import java.lang.Number;
import java.lang.NumberFormatException;

import java.math.RoundingMode;

import java.util.concurrent.ExecutionException;

import math.core.interfaces.EnhancedOperable;
import math.core.exceptions.IllegalNumberFormatException;
import math.core.exceptions.UndeterminedException;
import math.core.Notationer;
import math.core.Operationer;

/**

	Turns any primitive given number or a real number as string into a full notationed readable number.<br><br>

	Represents arbitrary-precision decimal numbers with configurable notation formatting.
	This class provides immutable numeric values that can exceed primitive type limits,
	along with comprehensive arithmetic operations, comparisons, and formatting capabilities.
	
	<p><b>Key Features:</b></p>
	<ul>

		<li><b>Arbitrary Precision</b> - Handles real numbers of virtually unlimited size (memory permitting)</li>
		<li><b>Multiple Notation Systems</b> - Supports decimal point ({@code 1,234.56}) or comma ({@code 1.234,56}) formats</li>
		<li><b>Full Arithmetic Operations</b> - Addition, subtraction, multiplication, division with precision control</li>
		<li><b>Advanced Mathematical Functions</b> - Inverse, modulus, rounding, and sign operations</li>
		<li><b>Comparison Utilities</b> - Specialized methods for comparing to zero, one, and minus one</li>
		<li><b>Type Conversion</b> - Implements all {@code Number} primitive conversions</li>
		<li><b>Immutable Design</b> - Thread-safe implementation with value semantics</li>

	</ul>

	<table border="1">

		<caption><b>Core Capabilities</b></caption>

		<tr><th>Category</th><th>Methods</th></tr>
		<tr><td>Construction</td><td>From String, double, long pairs, or component parts</td></tr>
		<tr><td>Arithmetic</td><td>add, subtract, multiply, divide, inverse, module</td></tr>
		<tr><td>Comparison</td><td>compareTo, compareToZero, compareToOne, compareToMinusOne</td></tr>
		<tr><td>Sign Operations</td><td>negate, abs, increase, decrease</td></tr>
		<tr><td>Formatting</td><td>toString, stringValue</td></tr>
		<tr><td>Precision Control</td><td>setScale (rounding modes), inverse (precision)</td></tr>

	</table>
	
	<p><b>Technical Implementation Notes:</b></p>
	<ul>

		<li>Numbers stored as separate integer and decimal string components</li>
		<li>Delegates arithmetic operations to {@link Operationer} utility</li>
		<li>Uses {@link Notationer} for number formatting and parsing</li>
		<li>Implements {@link EnhancedOperable} for advanced math operations</li>
		<li>Supports all standard rounding modes ({@link RoundingMode})</li>

	</ul>

	<p><b>Usage Example:</b></p>
	<pre>{@code

		Digit n = new Digit(1.23456E7);

		System.out.print(n);//12,345,600

	}</pre>

	@author Dandelion
	@version v0.1.2
	@since v0.0.1
	@see Operationer
	@see Notationer
	@see EnhancedOperable
	@see RoundingMode

*/

public class Digit extends Number implements EnhancedOperable<Digit>{

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
		@exception IllegalNumberFormatException if {@code String n} is either {@code null}, empty or not a valid real number.
		@see math.core.Notationer#isValidNumber(String)
		@see math.core.Notationer#Normalize(StringBuilder)
		@since v0.0.1

	*/

	public Digit(String n){

		if (!this.notationManager.isValidNumber(n)) throw new IllegalNumberFormatException(n+" is not a valid real Number");

		StringBuilder number = new StringBuilder(n);

		boolean isNegativeBackUp = number.charAt(0)=='-';
		boolean isPositiveBackUp = number.charAt(0)=='+';

		if (isNegativeBackUp || isPositiveBackUp){

			number.deleteCharAt(0);

		}

		String[] parts = this.notationManager.Normalize(number);
		this.integerPart = parts[0];
		this.decimalPart = parts[1];
		this.isNegative = (this.integerPart.matches("0") && this.decimalPart.isEmpty()) ? false : isNegativeBackUp;
		this.notation = true;

	}

	/**

		Creates an instance of {@code Digit} with a number value as a {@code String} and the notation type as {@code boolean}.

		@param n Real number as a {@code String}
		@param notation Digit notation
		@see math.core.Digit#Digit(String)
		@since v0.0.8

	*/

	public Digit(String n, boolean notation){

		this(n);

		this.notation = notation;

	}

	/**

		Creates an instance of {@code Digit} with a {@code double} value.

		@param n Real number.
		@see math.core.Digit#Digit(String)
		@since v0.0.1

	*/

	public Digit(double n){

		this(n+"");

	}

	/**

		Creates an instance of {@code Digit} with a {@code double} value and the notation type as {@code boolean}.

		@param n Real number.
		@param notation Digit notation
		@see math.core.Digit#Digit(double)
		@since v0.0.8

	*/

	public Digit(double n, boolean notation){

		this(n);

		this.notation = notation;

	}

	/**

		Creates an instance of {@code Digit} with two number values as {@code String}.

		@param integerPart Integer part of the number.
		@param decimalPart Decimal part of the number.
		@see math.core.Digit#Digit(String)
		@since v0.0.4

	*/

	public Digit(String integerPart, String decimalPart){

		this(integerPart+"."+decimalPart);

	}

	/**

		Creates an instance of {@code Digit} with two number values as {@code String} and the notation type as {@code boolean}.

		@param integerPart Integer part of the number.
		@param decimalPart Decimal part of the number.
		@param notation Digit notation
		@see math.core.Digit#Digit(String, String)
		@since v0.0.8

	*/

	public Digit(String integerPart, String decimalPart, boolean notation){

		this(integerPart, decimalPart);

		this.notation = notation;

	}

	/**

		Creates an instance of {@code Digit} with two number values as {@code long}.

		@param integerPart Integer part of the number.
		@param decimalPart Decimal part of the number.
		@see math.core.Digit#Digit(String)
		@since v0.0.8

	*/

	public Digit(long integerPart, long decimalPart){

		this(integerPart+"."+decimalPart);

	}

	/**

		Creates an instance of {@code Digit} with two number values as {@code long} and the notation type as {@code boolean}.

		@param integerPart Integer part of the number.
		@param decimalPart Decimal part of the number.
		@param notation Digit notation
		@see math.core.Digit#Digit(long, long)
		@since v0.0.8

	*/

	public Digit(long integerPart, long decimalPart, boolean notation){

		this(integerPart, decimalPart);

		this.notation = notation;

	}

	/**

		Creates an instance of {@code Digit} with all the attributes required.

		@param integerPart Integer part of the number.
		@param decimalPart Decimal part of the number.
		@param isNegative The {@code boolean isNegative} represents if the number is either negative 
		or positive.
		@param notation The {@code boolean notation} represents if the number is either using the decimal point 
		notation or the dot decimal notation.

		@since v0.0.4

	*/

	protected Digit(String integerPart, String decimalPart, boolean isNegative, boolean notation){

		this.integerPart = integerPart;
		this.decimalPart = this.operationManager.trimZerosRight(decimalPart);
		this.isNegative = (this.integerPart.matches("0") && this.decimalPart.isEmpty()) ? false : isNegative;
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

		this.integerPart = integerPart;
		this.decimalPart = "";
		this.isNegative = this.integerPart.matches("0") ? false : isNegative;
		this.notation = notation;

	}

	/**

		Implements the {@code String format()} function from extended class Notationer to print the number on a 
		readable notation using the attributes {@code this.integerPart}, {@code this.decimalPart} and {@code this.notation} 
		as parameters for such function.
		
		The return values will be either negative or positive depending on the state of the attribute {@code this.isNegative}.

		@return String Returns a full notationed readable number.
		@see math.core.Notationer#format(String, String, boolean)
		@since v0.0.1

	*/

	@Override public String toString(){

		return (this.isNegative ? "-" : "")+this.notationManager.format(this.integerPart, this.decimalPart, this.notation);

	}

	/**

		Returns the value of the specified number as a byte. The numeric value represented by this object after conversion to type byte.

		@exception IllegalNumberFormatException Throws by the method parseByte from class {@code Byte}.
		@return The numeric value represented by this object after conversion to type byte.
		@see java.lang.Number#byteValue()
		@since v0.0.7

	*/

	@Override public byte byteValue() throws IllegalNumberFormatException{

		return Byte.parseByte(this.integerPart);

	}

	/**

		Returns the value of the specified number as a double. The numeric value represented by this object after conversion to type double.

		@exception IllegalNumberFormatException Throws by the method parseDouble from class {@code double}.
		@return The numeric value represented by this object after conversion to type double.
		@see java.lang.Number#doubleValue()
		@since v0.0.7

	*/

	@Override public double doubleValue() throws IllegalNumberFormatException{

		return Double.parseDouble(this.integerPart+(

			!this.decimalPart.isEmpty() ? "."+this.decimalPart : ""

		));

	}

	/**

		Returns the value of the specified number as a float. The numeric value represented by this object after conversion to type float.

		@exception IllegalNumberFormatException Throws by the method parseFloat from class {@code float}.
		@return The numeric value represented by this object after conversion to type float.
		@see java.lang.Number#floatValue()
		@since v0.0.7

	*/

	@Override public float floatValue() throws IllegalNumberFormatException{

		return Float.parseFloat(this.integerPart+(

			!this.decimalPart.isEmpty() ? "."+this.decimalPart : ""

		));

	}

	/**

		Returns the value of the specified number as a int. The numeric value represented by this object after conversion to type int.

		@exception IllegalNumberFormatException Throws by the method parseInt from class {@code Integer}.
		@return The numeric value represented by this object after conversion to type int.
		@see java.lang.Number#intValue()
		@since v0.0.7

	*/

	@Override public int intValue() throws IllegalNumberFormatException{

		return Integer.parseInt(this.integerPart);

	}

	/**

		Returns the value of the specified number as a long. The numeric value represented by this object after conversion to type long.

		@exception IllegalNumberFormatException Throws by the method parseLong from class {@code Long}.
		@return The numeric value represented by this object after conversion to type long.
		@see java.lang.Number#longValue()
		@since v0.0.7

	*/

	@Override public long longValue() throws IllegalNumberFormatException{

		return Long.parseLong(this.integerPart);

	}

	/**

		Returns the value of the specified number as a short. The numeric value represented by this object after conversion to type short.

		@exception IllegalNumberFormatException Throws by the method parseShort from class {@code Short}.
		@return The numeric value represented by this object after conversion to type short.
		@see java.lang.Number#shortValue()
		@since v0.0.7

	*/

	@Override public short shortValue() throws IllegalNumberFormatException{

		return Short.parseShort(this.integerPart+(

			!this.decimalPart.isEmpty() ? "."+this.decimalPart : ""

		));

	}

	/**

		Returns the value of the specified number as a String. The numeric value represented by this object after conversion to type String.

		@return The numeric value represented by this object after conversion to type String.
		@since v0.0.9

	*/

	public String stringValue(){

		return this.integerPart+(

			!this.decimalPart.isEmpty() ? "."+this.decimalPart : ""

		);

	}

	/**

		Implements the {@code boolean equals(Object)} function from extended class class {@link java.lang.Object} to compare the instances.

		<br><br><b>Usage Example</b>
		<pre>{@code

			Digit n = new Digit(1.23456E7);
			Digit m = new Digit("89");

			boolean result = n.equals(m);

		}</pre>

		The return value will be {@code result = false}

		@param obj Object instance.
		@return boolean Returns a boolean value where {@code true} means the instances are equal and {@code false} means they are not.
		@see java.lang.Comparable#compareTo(Object)
		@see java.lang.Object#equals(Object)
		@since v0.0.17

	*/

	@Override public boolean equals(Object obj) {

		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		
		Digit other = (Digit) obj;
		return this.compareTo(other)==0;

	}

	/**

		Implements the {@code int compareTo(Object)} function from implemented class Comparable to compare the instances.

		<br><br><b>Usage Example</b>
		<pre>{@code

			Digit n = new Digit(1.23456E7);
			Digit m = new Digit("89");

			int result = n.compareTo(m);

		}</pre>

		The return value will be {@code result = 1}

		@param n Digit instance.
		@return int Returns an x value ranging from {@literal -1<x<1} where x belongs to integers. Where {@literal 0} if both are 
		equal, {@literal 1} if {@literal n>m} and {@literal -1} if {@literal n<m}.
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
		@return int Returns an x value ranging from {@literal -1<x<1} where x belongs to integers. Where {@literal 0} if both are 
		equal, {@literal 1} if {@literal n>m} and {@literal -1} if {@literal n<m}.
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
		@return int Returns an x value ranging from {@literal -1<x<1} where x belongs to integers. Where {@literal 0} if both are 
		equal, {@literal 1} if {@literal n>m} and {@literal -1} if {@literal n<m}.
		@see java.lang.Comparable#compareTo(Object)
		@see math.core.Digit#compareTo(Digit)
		@since v0.0.4

	*/

	public int compareTo(int n){

		return this.compareTo(new Digit(n+"", this.isNegative, this.notation));

	}

	/**

		Compares a {@code Digit} instance to zero.

		<br><br><b>Usage Example</b>
		<pre>{@code

			Digit n = new Digit(1);

			int result = n.compareToZero();

		}</pre>

		The return value will be {@code result = 1}

		@return int Returns an x value ranging from {@literal -1<x<1} where x belongs to integers. Where {@literal 0} if is equals to 
		zero, {@literal 1} if {@literal n>0} and {@literal -1} if {@literal n<0}.
		@since v0.0.5

	*/

	public int compareToZero(){

		if (this.isNegative) return -1;

		if (this.integerPart.matches("0") && this.decimalPart.isEmpty()) return 0;

		return 1;

	}

	/**

		Compares a {@code Digit} instance to one.

		<br><br><b>Usage Example</b>
		<pre>{@code

			Digit n = new Digit(1);

			int result = n.compareToOne();

		}</pre>

		The return value will be {@code result = 0}

		@return int Returns an x value ranging from {@literal -1<x<1} where x belongs to integers. Where {@literal 0} if is equals to 
		one, {@literal 1} if {@literal n>1} and {@literal -1} if {@literal n<1}.
		@since v0.0.5

	*/

	public int compareToOne(){

		if (this.isNegative || this.integerPart.matches("0")) return -1;

		if (this.integerPart.matches("1") && this.decimalPart.isEmpty()) return 0;

		return 1;

	}

	/**

		Compares a {@code Digit} instance to minus one.

		<br><br><b>Usage Example</b>
		<pre>{@code

			Digit n = new Digit(1);

			int result = n.compareToMinusOne();

		}</pre>

		The return value will be {@code result = 1}

		@return int Returns an x value ranging from {@literal -1<x<1} where x belongs to integers. Where {@literal 0} if is equals to 
		minus one, {@literal 1} if {@literal n>-1} and {@literal -1} if {@literal n<-1}.
		@since v0.0.5

	*/

	public int compareToMinusOne(){

		if (!this.isNegative || (this.integerPart.matches("0") && !this.decimalPart.isEmpty())) return 1;

		if (this.integerPart.matches("1") && this.decimalPart.isEmpty()) return 0;

		return -1;

	}

	/**

		Increases the {@code Digit} instance's value by one.

		<br><br><b>Usage Example</b>
		<pre>{@code

			Digit n = new Digit(-0.5);

			Digit result = n.increase();

		}</pre>

		The return value will be {@code result = 0.5}

		@return Digit Intance increased by one.
		@since v0.1.0

	*/

	public Digit increase(){

		boolean isBetween = this.compareToMinusOne() > 0 && this.compareToZero() < 0;

		if (isBetween){

			return this.negate();

		}else if (!this.isNegative){

			return new Digit(this.operationManager.increase(this.integerPart), this.decimalPart, this.isNegative, this.notation);

		}else{

			return new Digit(this.operationManager.decrease(this.integerPart), this.decimalPart, this.isNegative, this.notation);

		}

	}

	/**

		Decreases the {@code Digit} instance's value by one.

		<br><br><b>Usage Example</b>
		<pre>{@code

			Digit n = new Digit(0.5);

			Digit result = n.increase();

		}</pre>

		The return value will be {@code result = -0.5}

		@return Digit Intance increased by one.
		@since v0.1.0

	*/

	public Digit decrease(){

		boolean isBetween = this.compareToOne() < 0 && this.compareToZero() > 0;

		if (isBetween){

			return this.negate();

		}else if (this.isNegative){

			return new Digit(this.operationManager.increase(this.integerPart), this.decimalPart, this.isNegative, this.notation);

		}else{

			return new Digit(this.operationManager.decrease(this.integerPart), this.decimalPart, this.isNegative, this.notation);

		}

	}

	/**

		Creates a Digit instance with the {@code boolean isNegative} attribute negated.

		<br><br><b>Usage Example</b>
		<pre>{@code

			Digit n = new Digit(1);
			Digit m = n.negate();

		}</pre>

		The return value will be {@code m = -1}

		@return Digit Returns the negated value for the {@code Digit} instance.
		@see math.core.Digit#Digit(String, String, boolean, boolean)
		@since v0.0.4

	*/

	public Digit negate(){

		return new Digit(this.integerPart, this.decimalPart, !this.isNegative, this.notation);

	}

	/**

		Creates a Digit instance with the {@code boolean isNegative} attribute on false.

		<br><br><b>Usage Example</b>
		<pre>{@code

			Digit n = new Digit(-1);
			Digit m = n.abs();

		}</pre>

		The return value will be {@code m = 1}

		@return Digit Returns the absolute value for the {@code Digit} instance.
		@see math.core.Digit#Digit(String, String, boolean, boolean)
		@since v0.0.4

	*/

	public Digit abs(){

		return new Digit(this.integerPart, this.decimalPart, false, this.notation);

	}

	/**

		Calculates the {@code Digit} instance's inverse value.

		<br><br><b>Usage Example</b>
		<pre>{@code

			Digit n = new Digit(2);
			Digit m = n.inverse(2);

		}</pre>

		The return value will be {@code m = 0.5}

		@param precision Decimal precision.
		@return Digit Returns the inverse value for the {@code Digit} instance.
		@see math.core.Digit#Digit(String, boolean, boolean)
		@see math.core.Digit#divide(Digit, long)
		@since v0.0.7

	*/

	public Digit inverse(long precision) {

		return new Digit("1", false, this.notation).divide(this, precision);

	}

	/**

		Calculates the {@code Digit} instance's inverse value with the precision set at {@code 128}. Overriding the implemented function {@code inverse} from {@code Operable} interface.

		<br><br><b>Usage Example</b>
		<pre>{@code

			Digit n = new Digit(2);
			Digit m = n.inverse();

		}</pre>

		The return value will be {@code m = 0.5}

		@return Digit Returns the inverse value for the {@code Digit} instance.
		@see math.core.interfaces.EnhancedOperable#inverse()
		@see math.core.Digit#inverse(long)
		@since v0.0.7

	*/

	@Override public Digit inverse() {

		return this.inverse(128);

	}

	/**

		Gets the {@code Digit} instance's unity value being ({@code 1}). Overriding the implemented function {@code unity} from {@code Operable} interface.

		<br><br><b>Usage Example</b>
		<pre>{@code

			Digit n = new Digit(2);
			Digit m = n.unity();

		}</pre>

		The return value will be {@code m = 1}

		@return Digit Returns the unity value {@code 1} regardless of the {@code Digit} instance.
		@see math.core.interfaces.EnhancedOperable#unity()
		@since v0.1.1

	*/

	@Override public Digit unity(){

		return new Digit("1", false, this.notation);

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

	public Digit multiply(double other) {

		return this.multiply(new Digit(other));

	}

	/**

		Divides two numbers logicly and sequentially.

		@param other double value.
		@param precision Decimal precision.
		@return Digit Result from the division.
		@see math.core.Digit#divide(Digit, long)
		@since v0.0.7

	*/

	public Digit divide(double other, long precision) {

		return this.divide(new Digit(other), precision);

	}

	/**

		Divides two numbers logicly and sequentially with the precision set at {@code 128}.

		@param other double value.
		@return Digit Result from the division.
		@see math.core.Digit#divide(Digit)
		@since v0.1.1

	*/

	public Digit divide(double other) {

		return this.divide(new Digit(other));

	}

	/**

		Gets the module between two {@code Digit} numbers logicly and sequentially.

		@param other double instance.
		@return Digit Result from the module of the firts instance given the second instance.
		@see math.core.Digit#module(Digit)
		@since v0.0.8

	*/

	public Digit module(double other) {

		return this.module(new Digit(other));

	}

	/**

		Adds two {@code Digit} numbers logicly and sequentially. Overriding the implemented function {@code add} from {@code Operable} interface.

		<br><br><b>Usage Example</b>
		<pre>{@code

			Digit n = new Digit("-123.45");
			Digit m = new Digit(678.9);

			Digit result = n.add(m);

		}</pre>

		The return value will be {@code result = 555.45}

		@param other Digit instance.
		@return Digit Result from the addition of the two instance.
		@see math.core.Digit#negate()
		@see math.core.Digit#abs()
		@see math.core.interfaces.Operable#add(Object)
		@see math.core.Operationer#padZerosRight(String, int)
		@see math.core.Operationer#addTwoTogether(String, String, boolean)
		@since v0.0.4

	*/

	@Override public Digit add(Digit other){

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

		String fullResult = this.operationManager.addTwoTogether(

			this.integerPart + this.operationManager.padZerosRight(this.decimalPart, maxDecimalLength),
			other.integerPart + this.operationManager.padZerosRight(other.decimalPart, maxDecimalLength),
			!other.isNegative

		);

		int scale = fullResult.length() - maxDecimalLength;

		String integerResult = scale!=0 ? this.operationManager.trimZerosLeft(fullResult.substring(0, scale)) : "0";
		String decimalResult = fullResult.substring(scale);

		return new Digit(integerResult, decimalResult, false, this.notation);

	}

	/**

		Subtracts two {@code Digit} numbers logicly and sequentially. Overriding the implemented function {@code subtract} from {@code Operable} interface.

		@param other Digit instance.
		@return Digit Result from the subtraction of the two intance.
		@see math.core.Digit#add(Digit)
		@see math.core.interfaces.Operable#subtract(Object)
		@since v0.0.4

	*/

	@Override public Digit subtract(Digit other){

		return this.add(other.negate());

	}

	/**

		Multiplies two {@code Digit} numbers logicly and sequentially. Overriding the implemented function {@code multiply} from {@code Operable} interface.

		<br><br><b>Usage Example</b>
		<pre>{@code

			Digit n = new Digit("25");
			Digit m = new Digit(5);

			Digit result = n.multiply(m);

		}</pre>

		The return value will be {@code result = 125}

		@param other Digit instance.
		@return Digit Result from the multiplication of the two instance.
		@see math.core.interfaces.Operable#multiply(Object)
		@see math.core.Digit#negate()
		@see math.core.Operationer#multiplication(String, String)
		@since v0.0.5

	*/

	@Override public Digit multiply(Digit other) {

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

		String integerResult = this.operationManager.trimZerosLeft(fullResult.substring(0, length - maxDecimalLength));
		String decimalResult = fullResult.substring(length - maxDecimalLength);

		return new Digit(integerResult, decimalResult, this.isNegative!=other.isNegative, this.notation);

	}

	/**

		Divides two {@code Digit} numbers logicly and sequentially with the precision set at {@code 128}. 
		Overriding the implemented function {@code divide} from {@code Operable} interface.

		<br><br><b>Usage Example</b>
		<pre>{@code

			Digit n = new Digit("7");
			Digit m = new Digit(4);

			Digit result = n.divide(m);

		}</pre>

		The return value will be {@code result = 1.75}

		@param other Digit instance.
		@exception UndeterminedException if {@code Digit} other is zero.
		@return Digit Result from the division of the two instance.
		@see math.core.interfaces.Operable#divide(Object)
		@see math.core.Digit#divide(Digit, long)
		@since v0.1.1

	*/

	@Override public Digit divide(Digit other) {

		return this.divide(other, 128);

	}

	/**

		Divides two {@code Digit} numbers logicly and sequentially.

		<br><br><b>Usage Example</b>
		<pre>{@code

			Digit n = new Digit("7");
			Digit m = new Digit(4);

			Digit result = n.divide(m, 16);

		}</pre>

		The return value will be {@code result = 1.75}

		@param other Digit instance.
		@param precision Decimal precision.
		@exception UndeterminedException if {@code Digit} other is zero.
		@return Digit Result from the division of the two instance.
		@see math.core.interfaces.Operable#divide(Object)
		@see math.core.Notationer#trimZeros(String)
		@see math.core.Operationer#division(String, String, long)

		@since v0.0.6

	*/

	public Digit divide(Digit other, long precision) {

		if (other.compareToZero()==0) throw new UndeterminedException("Division by zero is undetermine.");

		int decimalLeft = - this.decimalPart.length() + other.decimalPart.length();

		String dividend = this.operationManager.trimZerosLeft(this.integerPart+this.decimalPart);
		String divisor = this.operationManager.trimZerosLeft(other.integerPart+other.decimalPart);

		if (decimalLeft>0){

			dividend+= "0".repeat(decimalLeft);

		}else{

			divisor+= "0".repeat(-decimalLeft);

		}

		String[] result = this.operationManager.division(dividend, divisor, precision);

		return new Digit(result[0], result[1], this.isNegative!=other.isNegative, this.notation);

	}

	/**

		Gets the module between two {@code Digit} numbers logicly and sequentially.

		<br><br>{@literal a mod b = |a| - |b| * FLOOR(|a|/|b|)}
		
		<br><br>The sign of the result will be the original sign from {@literal a}.

		<br><br><b>Usage Example</b>
		<pre>{@code

			Digit n = new Digit("4");
			Digit m = new Digit(2);

			Digit result = n.module(m);

		}</pre>

		The return value will be {@code result = 0}

		@param other Digit instance.
		@exception UndeterminedException if {@code Digit} other is zero.
		@return Digit Result from the module of the firts instance given the second instance.
		@see math.core.Digit#abs()
		@see math.core.Digit#subtract(Digit)
		@see math.core.Digit#multiply(Digit)
		@see math.core.Digit#divide(Digit, long)

		@since v0.0.8

	*/

	public Digit module(Digit other) {

		if (other.compareToZero()==0) throw new UndeterminedException("Moduling by zero is undetermine.");

		Digit thisAbsolute = this.abs();
		Digit otherAbsolute = other.abs();

		Digit result = thisAbsolute.subtract(

			otherAbsolute.multiply(

				thisAbsolute.divide(

					otherAbsolute, 0

				)

			)

		);// a - b * FLOOR(a/b)

		return this.isNegative ? result.negate() : result;

	}/**/

	/**

		Rounds the number given a scale and a rounding mode.

		<br><br><b>Usage Example</b>
		<pre>{@code

			Digit n = new Digit("1.56");

			Digit result = n.setScale(1, RoundingMode.HALF_UP);

		}</pre>

		The return values will be {@code result = 1.6}

		@param scale Decimal scale.
		@param mode Rounding mode.
		@exception ArithmeticException if {@code RoundingMode} mode is not either a given constant for {@code RoundingMode} class or the constant is {@code RoundingMode.UNNECESARY}.
		@return Digit Rounded instance.
		@see java.math.RoundingMode

		@since v0.0.8

	*/

	public Digit setScale(int scale, RoundingMode mode) {

		if (this.decimalPart.isEmpty()){

			return this;

		}else if (scale<=0){

			return new Digit(this.integerPart, this.isNegative, this.notation);

		}else if (mode==RoundingMode.CEILING){

			return this.RoundingOrTrunk(!this.isNegative);

		}else if (mode==RoundingMode.FLOOR){

			return this.RoundingOrTrunk(this.isNegative);

		}else if (mode==RoundingMode.DOWN){

			return new Digit(this.integerPart, this.isNegative, this.notation);

		}else if (mode==RoundingMode.UP){

			return RoundingOrTrunk(true);

		}else if (mode==RoundingMode.HALF_UP){

			return this.RoundingHalf(scale, true);

		}else if (mode==RoundingMode.HALF_DOWN){

			return this.RoundingHalf(scale, false);

		}else if (mode==RoundingMode.HALF_EVEN){

			return this.RoundingEven(scale, true);

		}else{

			throw new ArithmeticException("Invalid RoundingMode");

		}

	}

	private Digit RoundingOrTrunk(boolean rounding){

		if (rounding){

			return new Digit(this.operationManager.increase(this.integerPart), this.isNegative, this.notation);

		}else{

			return new Digit(this.integerPart, this.isNegative, this.notation);

		}

	}

	private Digit RoundingEven(int scale, boolean even) {

		int isPointFive = this.operationManager.compareDecimalParts(this.decimalPart, "5");

		Digit Even = new Digit(this.integerPart, this.isNegative, this.notation);

		int isEven = Even.module(new Digit("2", false, true)).compareToZero();

		if (isPointFive==0){

			if (isEven==0){

				return Even;

			}else{

				return new Digit(this.operationManager.increase(this.integerPart), this.isNegative, this.notation);

			}

		}else{

			return this.RoundingHalf(scale, even);

		}

	}/**/

	private Digit RoundingHalf(int scale, boolean even){

		int decimalLength = this.decimalPart.length();

		if (decimalLength<=scale) return this;

		int carry = this.carry(this.decimalPart.substring(scale), even);

		String newDecimalPart = this.decimalPart.substring(0, scale);

		if (carry==1){

			String roundedPart = this.operationManager.increase(newDecimalPart);

			if (roundedPart.length()==newDecimalPart.length()){

				return new Digit(this.integerPart, this.operationManager.trimZerosRight(roundedPart), this.isNegative, this.notation);

			}else{

				return new Digit(this.operationManager.increase(this.integerPart), this.isNegative, this.notation);

			}

		}else{

			return new Digit(this.integerPart, newDecimalPart, this.isNegative, this.notation);

		}

	}

	private int carry(String number, boolean even){

		int carry = 0;

		if (number.length()!=1){

			carry = this.carry(number.substring(1), even);

		}

		int n = number.charAt(0) - '0' + carry;

		carry = 0;

		if (even ? n>=5 : n>5){

			carry = 1;

		}

		return carry;

	}

}