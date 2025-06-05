package math.core;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.ArrayList;

/**

	Manages the operations and results of the four basic mathemathic operations. 
	Intended to be only used as a stakeholder manager for its functions.<br><br>

	Provides core arithmetic operations for arbitrary-precision integer arithmetic using string-based number representation.
	This class implements fundamental mathematical operations (addition, subtraction, multiplication, division) with
	support for precision control, comparison utilities, and zero-padding/trimming operations.

	<p><b>Key Features:</b></p>
	<ul>

		<li><b>Arbitrary-Precision Arithmetic</b> - Handles string-based integer numbers of any size limited only by memory</li>
		<li><b>Full Operation Suite</b> - Addition, subtraction, multiplication, and division with configurable precision</li>
		<li><b>Comparison Utilities</b> - Specialized methods for comparing integer and decimal components</li>
		<li><b>Zero Management</b> - Padding (left/right) and trimming (left/right) operations for number normalization</li>
		<li><b>Asynchronous Processing</b> - Parallel computation for large-number operations</li>
		<li><b>Increment/Decrement Operations</b> - Efficient string-based number modification</li>
		<li><b>Division Utilities</b> - Quotient/remainder calculation and decimal expansion with precision control</li>

	</ul>

	<p><b>Usage Example:</b></p>
	<pre>{@code

		public class MyClass{

			private Operationer operationManager = new Operationer();

		}

	}</pre>

	@author Dandelion
	@version v0.0.4
	@since v0.0.9

*/

public class Operationer{

	/**

		Empty constructor.

	*/

	public Operationer(){}

	/**

		Compares two positive integer numbers as two {@code String}.

		<br><br><b>Usage Example</b>
		<pre>{@code

			int result = this.operationManager.compareIntegerParts("45", "50");

		}</pre>

		The return value will be {@code result = -1}

		@param a Integer number as a {@code String}
		@param b Integer number as a {@code String}

		@return int Returns an x value ranging from {@literal -1<x<1} where x belongs to integers. Where {@literal 0} if both are 
		equal, {@literal 1} if {@literal n>m} and {@literal -1} if {@literal n<m}.
		@since v0.0.1

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

		<br><br><b>Usage Example</b>
		<pre>{@code

			int result = this.operationManager.compareDecimalParts("5", "52");

		}</pre>

		The return value will be {@code result = 1}

		@param a Integer number as a {@code String}
		@param b Integer number as a {@code String}

		@return int Returns an x value ranging from {@literal -1<x<1} where x belongs to integers. Where {@literal 0} if both are 
		equal, {@literal 1} if {@literal n<m} and {@literal -1} if {@literal n>m}.
		@since v0.0.1

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

		Add zeros to the {@code String} result to the right until the length matches the length parameter.

		<br><br><b>Usage Example</b>
		<pre>{@code

			String result = this.operationManager.padZerosRight("5", 3);

		}</pre>

		The return value will be {@code result = "500"}

		@param str Integer number as a {@code String}
		@param length Intented length for the {@code String} result.

		@return String Returns String that matches the length parameter.
		@since v0.0.1

	*/

	protected String padZerosRight(String str, int length){

		int strLength = str.length();

		if (strLength>=length){

			return str;

		}

		return new StringBuilder(str).append("0".repeat(length-strLength)).toString();

	}

	/**

		Add zeros to the {@code String} result to the left until the length matches the length parameter.

		<br><br><b>Usage Example</b>
		<pre>{@code

			String result = this.operationManager.padZerosLeft("5", 3);

		}</pre>

		The return value will be {@code result = "005"}

		@param str Integer number as a {@code String}
		@param length Intented length for the {@code String} result.

		@return String Returns String that matches the length parameter.
		@since v0.0.1

	*/

	protected String padZerosLeft(String str, int length){

		int strLength = str.length();

		if (strLength>=length){

			return str;

		}

		return new StringBuilder(str).insert(0, "0".repeat(length-strLength)).toString();

	}

	/**

		Trims right zeros.

		<br><br><b>Usage Example</b>
		<pre>{@code

			String result = this.operationManager.trimZerosRight("500");

		}</pre>

		The return value will be {@code result = "5"}

		@param str String value.

		@return String Returns the trim string.
		@since v0.0.2

	*/

	protected String trimZerosRight(String str){

		return str.replaceAll("[0]+$", "");

	}

	/**

		Trims left zeros.

		<br><br><b>Usage Example</b>
		<pre>{@code

			String result = this.operationManager.trimZerosRight("000");

		}</pre>

		The return value will be {@code result = "0"}

		@param str String value.

		@return String Returns the trim string.
		@since v0.0.2

	*/

	protected String trimZerosLeft(String str){

		return str.replaceAll("^[0]+(?!$)", "");

	}

	/**

		Trims left zeros.

		<br><br><b>Usage Example</b>
		<pre>{@code

			String result = this.operationManager.trimAllZerosLeft("000");

		}</pre>

		The return value will be {@code result = empty}

		@param str String value.

		@return String Returns the trim string.
		@since v0.0.3

	*/

	protected String trimAllZerosLeft(String str){

		return str.replaceAll("^[0]+", "");

	}

	/**

		Adds integers as {@code String} logicly and sequentially.

		<br><br><b>Usage Example</b>
		<pre>{@code

			String result = this.operationManager.asyncAddition(new String[] {

				"1", "2", "3"

			});

		}</pre>

		The return value will be {@code result = "7"}

		<br><br><b>Note:</b> <ol><li>The return value will have the same String length as the max String length value from the array.</li></ol>

		@param integers {@code String} array made out of positive integer numbers.

		@return String Result from the addition of the integers.
		@see math.core.Operationer#addTwoTogether(String, String, boolean)
		@since v0.0.3

	*/

	protected String asyncAddition(String[] integers) {

		int arrayLength = integers.length;

		if (arrayLength==1){

			return integers[0];

		}else if (arrayLength==2){

			return this.addTwoTogether(integers[0], integers[1], true);

		}else if (arrayLength==3){

			return this.addTwoTogether(this.addTwoTogether(

				integers[0],
				integers[1],
				true

			), integers[2], true);

		}else{

			return asyncAdditionRecursive(integers).join();

		}

	}

	/**

		Adds two integers as {@code String} logicly and sequentially.

		<br><br><b>Usage Example</b>
		<pre>{@code

			String result = this.operationManager.addTwoTogether("5", "2", true);

		}</pre>

		The return value will be {@code result = "7"}

		<br><br><b>Notes:</b>
		<ol>

			<li>The {@code String} length relation between the parameters must satisfy thisInteger >= otherInteger.</li>
			<li>For subtraction, the relation between the integer parameters must satisfy thisInteger >= otherInteger.</li>
			<li>The return value will have the same String length as the {@code String} thisInteger length.</li>

		</ol>

		@param thisInteger Positive integer number as {@code String}.
		@param otherInteger Positive integer number as {@code String}.
		@param isAddition Is addition or substraction as {@code boolean}.

		@return String Result from the addition of the integers.
		@see math.core.Operationer#padZerosLeft(String, int)
		@since v0.0.3

	*/

	protected String addTwoTogether(String thisInteger, String otherInteger, boolean isAddition){

		int maxLength = thisInteger.length();
		int minLength = otherInteger.length();

		String thisFullNumber = thisInteger;
		String otherFullNumber = padZerosLeft(otherInteger, maxLength);

		StringBuilder result = this.addTwoTogether(new StringBuilder(), thisFullNumber, otherFullNumber, 0, 0, maxLength - 1, otherFullNumber.length() - minLength - 1, isAddition);

		return result.toString();

	}

	/**

		Increases the integer's value stored as a {@code String} by one.

		<br><br><b>Usage Example</b>
		<pre>{@code

			String result = this.operationManager.increase("0");

		}</pre>

		The return value will be {@code result = "1"}

		<br><br><b>Notes:</b>
		<ol>

			<li>The return value will have the same String length as the parameter's length.</li>
			<li>The parameter must satisfy thisInteger >= 0.</li>

		</ol>

		@param thisInteger Positive integer number as {@code String}.
		@return String Parameter increased by one.
		@since v0.0.3

	*/

	protected String increase(String thisInteger){

		int length = thisInteger.length() - 1;

		String paddedOne = "0".repeat(length) + "1";

		StringBuilder result = this.addTwoTogether(new StringBuilder(), thisInteger, paddedOne, 0, 0, length, length - 1, true);

		return result.toString();

	}

	/**

		Decreases the integer's value stored as a {@code String} by one.

		<br><br><b>Usage Example</b>
		<pre>{@code

			String result = this.operationManager.decrease("1");

		}</pre>

		The return value will be {@code result = "0"}

		<br><br><b>Notes:</b>
		<ol>

			<li>The return value will have the same String length as the parameter's length.</li>
			<li>The parameter must satisfy thisInteger >= 1.</li>

		</ol>

		@param thisInteger Positive integer number as {@code String}.
		@return String Parameter decreased by one.
		@since v0.0.3

	*/

	protected String decrease(String thisInteger){

		int length = thisInteger.length() - 1;

		String paddedOne = "0".repeat(length) + "1";

		StringBuilder result = this.addTwoTogether(new StringBuilder(), thisInteger, paddedOne, 0, 0, length, length - 1, false);

		return this.trimZerosLeft(result.toString());

	}

	/**

		Multiplies integers as {@code String} logicly and sequentially.

		<br><br><b>Usage Example</b>
		<pre>{@code

			String result = this.operationManager.multiplication("12", "3");

		}</pre>

		The return value will be {@code result = "36"}

		@param thisInteger First positive integer as {@code String}.
		@param otherInteger Second positive integer as {@code String}.

		@return String Result from the multiplication of the integers.
		@see math.core.Operationer#asyncAddition(String[])
		@since v0.0.1

	*/

	protected String multiplication(String thisInteger, String otherInteger) {

		int arrayLength = otherInteger.length();

		String[] integerRows = this.multiplication(new String[arrayLength], new StringBuilder(), thisInteger, otherInteger, arrayLength - 1);

		return this.asyncAddition(integerRows);

	}

	/**

		Divides integers as {@code String} logicly and sequentially.

		<br><br><b>Usage Example</b>
		<pre>{@code

			String[] result = this.operationManager.division("7", "5", 20);

		}</pre>

		The return values will be {@code result[0] = "1"} and {@code result[1] = "4"}

		@param dividend Positive integer dividend as {@code String}.
		@param divisor Positive integer divisor as {@code String}.
		@param precision Decimal precision as {@code long}.

		@return String[] Two length array which conatains the result from the division of the 
		integers, index {@code 0} contains the integer part and index {@code 1} the decimal part.

		@see math.core.Operationer#compareIntegerParts(String, String)
		@see math.core.Operationer#quotient(String, String)
		@see math.core.Operationer#calculateDecimalPartForDivision(String, String, long)

		@since v0.0.1

	*/

	protected String[] division(String dividend, String divisor, long precision) {

		if (dividend.matches("0")) return new String[] {"0", ""};

		int isGreaterThanDividend = this.compareIntegerParts(dividend, divisor);

		if (isGreaterThanDividend>=0){

			String[] quotient = this.quotient(dividend, divisor);

			if (quotient[1].isEmpty()){

				return new String[] {quotient[0], ""};

			}else{

				return new String[] {quotient[0], this.calculateDecimalPartForDivision(quotient[1]+"0", divisor, precision)};

			}

		}else{

			return new String[] {"0", this.calculateDecimalPartForDivision(dividend+"0", divisor, precision)};

		}

	}

	/**

		Divides integers as {@code String} logicly and sequentially.

		<br><br><b>Usage Example</b>
		<pre>{@code

			String[] result = this.operationManager.quotient("7", "5");

		}</pre>

		The return values will be {@code result[0] = "1"} and {@code result[1] = "2"}

		@param dividend Positive integer dividend as {@code String}.
		@param divisor Positive integer divisor as {@code String}.

		@return String[] Two length array which conatains the result from the division of the 
		integers, index {@code 0} contains the quotient and index {@code 1} the decimal remainder.

		@see math.core.Operationer#compareIntegerParts(String, String)
		@see math.core.Operationer#multiplication(String, String)
		@see math.core.Operationer#addTwoTogether(String, String, boolean)
		@see math.core.Operationer#trimZerosLeft(String)

		@since v0.0.4

	*/

	protected String[] quotient(String dividend, String divisor) {

		int index = 3 * (dividend.length() - divisor.length());

		ArrayList<String> powers = this.powersOfTwo(index);
		String factor = powers.get(powers.size() - 1);

		while(this.compareIntegerParts(this.multiplication(factor, divisor), dividend)<0){

			factor = this.addTwoTogether(factor, factor, true);

			powers.add(factor);

		}

		index = powers.size() - 1;

		String product = this.multiplication(factor, divisor);
		int isGreaterThanProduct = this.compareIntegerParts(product, dividend);
		boolean lastLower = false;

		String lastFactor = "", lastProduct = "";

		do{

			index--;

			if (isGreaterThanProduct!=0){

				lastLower = isGreaterThanProduct<0;

				if (lastLower){

					lastFactor = factor;
					lastProduct = product;

				}

				factor = this.trimZerosLeft(this.addTwoTogether(factor, powers.get(index), lastLower));

			}else{

				return new String[] {factor, ""};

			}

			product = this.multiplication(factor, divisor);
			isGreaterThanProduct = this.compareIntegerParts(product, dividend);

		}while(index>0);

		if (isGreaterThanProduct>0){

			factor = lastFactor;
			product = lastProduct;

		}

		return new String[] {factor, this.trimAllZerosLeft(this.addTwoTogether(dividend, product, false))};

	}

	/**

		Calculates the decimal part of a division using the remainder and divisor as {@code String} logicly and sequentially.

		<br><br><b>Usage Example</b>
		<pre>{@code

			String result = this.operationManager.calculateDecimalPartForDivision("20", "5", 20);

		}</pre>

		The return value will be {@code result = "4"}

		@param remainder Integer remainder as {@code String}.
		@param divisor Integer Divisor as {@code String}.
		@param precision Decimal precision as {@code long}.

		@return String Decimal part for the division.

		@see math.core.Operationer#compareIntegerParts(String, String)
		@see math.core.Operationer#addTwoTogether(String, String, boolean)
		@see math.core.Operationer#increase(String)

		@since v0.0.1

	*/

	private String calculateDecimalPartForDivision(String remainder, String divisor, long precision){

		StringBuilder result = new StringBuilder("");

		for (long i=0; i<precision; i++){

			int isGreaterThanDividend = this.compareIntegerParts(remainder, divisor);

			byte counter = 0;

			while(isGreaterThanDividend>=0){

				remainder = this.trimZerosLeft(this.addTwoTogether(remainder.toString(), divisor.toString(), false));

				counter++;

				isGreaterThanDividend = this.compareIntegerParts(remainder, divisor);

			}

			if (!remainder.matches("0")){

				result.append(counter);
				remainder+= "0";

			}else{

				if (counter<10){

					result.append(counter);

					return result.toString();

				}else{

					return this.increase(result.toString());

				}

			}

		}

		return result.toString();

	}

	@SuppressWarnings("unchecked") private CompletableFuture<String> asyncAdditionRecursive(String[] integers) {

		if (integers.length==1){

			return CompletableFuture.completedFuture(integers[0]);

		}

		int pairs = integers.length / 2;

		CompletableFuture<String>[] futures = new CompletableFuture[pairs];
		ExecutorService executor = Executors.newFixedThreadPool(pairs);

		for (int i=0; i<pairs; i++){

			int index = i * 2;

			futures[i] = CompletableFuture.supplyAsync(() -> this.addTwoTogether(integers[index], integers[index + 1], true), executor);

		}

		return CompletableFuture.allOf(futures).thenCompose(v -> {

			String[] results = new String[pairs + (integers.length % 2)];

			for (int i=0; i<pairs; i++){

				results[i] = futures[i].join();

			}

			if (integers.length%2!=0){

				results[pairs] = integers[integers.length - 1];

			}

			executor.shutdown();

			return asyncAdditionRecursive(results);

		});
	}

	private StringBuilder addTwoTogether(StringBuilder result, String thisFullNumber, String otherFullNumber, int carryIn, int carryOut, int index, int paddedIndex, boolean isAddition){

		int thisDigit = (thisFullNumber.charAt(index) - '0') - carryOut;
		int otherDigit = otherFullNumber.charAt(index) - '0';

		if (index<=paddedIndex && (isAddition ? carryIn==0 : carryOut==0)){

			result.insert(0, thisFullNumber.substring(0, index+1));

			return result;

		}

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


		if (index==0){

			if (carryIn>0){

				result.insert(0, carryIn);

			}

			return result;

		}else{

			index--;

			return this.addTwoTogether(result, thisFullNumber, otherFullNumber, carryIn, carryOut, index, paddedIndex, isAddition);

		}

	}

	private String[] multiplication(String[] integerRows, StringBuilder zero, String thisInteger, String otherInteger, int index){

		StringBuilder result = this.multiplication(new StringBuilder(), thisInteger, otherInteger.charAt(index) - '0', 0, thisInteger.length() - 1);

		result.append(zero);
		zero.append("0");

		integerRows[index] = result.toString();

		if (index==0){

			return integerRows;

		}else{

			index--;

			return this.multiplication(integerRows, zero, thisInteger, otherInteger, index);

		}

	}

	private StringBuilder multiplication(StringBuilder result, String thisInteger, int otherDigit, int carryIn, int index){

		int thisDigit = thisInteger.charAt(index) - '0';

		int multi = (thisDigit * otherDigit) + carryIn;
		carryIn = multi/10;

		result.insert(0, multi%10);

		if (index==0){

			if (carryIn>0){

				result.insert(0, carryIn);

			}

			return result;

		}else{

			index--;

			return this.multiplication(result, thisInteger, otherDigit, carryIn, index);

		}

	}

	private ArrayList<String> powersOfTwo(int k){

		ArrayList<String> powers = new ArrayList<>();

		powers.add("1");

		for(int i=1; i<k; i++){

			String factor = powers.get(i - 1);

			powers.add(this.addTwoTogether(factor, factor, true));

		}

		return powers;

	}

}