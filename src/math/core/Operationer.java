package math.core;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.Arrays;

/**

	Manages the operations and results of the four basic mathemathic operations. 
	Intended to be only used as a stakeholder manager for its functions.

	<br><br><b>Usage Example</b>
	<pre>{@code

		public class MyClass{

			public Operationer operationManager = new Operationer();

		}

	}</pre>

	@author Dandelion
	@version v0.0.3
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

		The return values will be {@code result} = -1

		@param a Integer number as a {@code String}
		@param b Integer number as a {@code String}

		@return int Returns an x value ranging from {@literal -1<x<1} where x belongs to integers. Where 0 if both are 
		equal, 1 if {@literal n>m} and -1 if {@literal n<m}.
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

		The return values will be {@code result} = 1

		@param a Integer number as a {@code String}
		@param b Integer number as a {@code String}

		@return int Returns an x value ranging from {@literal -1<x<1} where x belongs to integers. Where 0 if both are 
		equal, 1 if {@literal n<m} and -1 if {@literal n>m}.
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

		The return values will be {@code result} = "500"

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

		The return values will be {@code result} = "005"

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

		The return values will be {@code result} = "5"

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

		The return values will be {@code result} = "0"

		@param str String value.

		@return String Returns the trim string.
		@since v0.0.2

	*/

	protected String trimZerosLeft(String str){

		return str.replaceAll("^[0]+(?!$)", "");

	}

	/**

		Adds integers as {@code String} logicly and sequentially.

		<br><br><b>Usage Example</b>
		<pre>{@code

			String result = this.operationManager.asyncAddition(new String[] {

				"1", "2", "3"

			});

		}</pre>

		The return values will be {@code result} = "7"

		<br><br><b>Note:</b> <ol><li>The return value will have the same String length as the max String length value from the array.</li></ol>

		@param integers {@code String} array made out of integer numbers.

		@return String Result from the addition of the integers.
		@see math.core.Operationer#addTwoTogether(String, String, boolean)
		@since v0.0.3

	*/

	protected String asyncAddition(String[] integers){

		try {

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

				return asyncAdditionRecursive(integers).get();

			}

		}catch(Exception e){

			e.printStackTrace();

			return "0";

		}

	}

	/**

		Adds two integers as {@code String} logicly and sequentially.

		<br><br><b>Usage Example</b>
		<pre>{@code

			String result = this.operationManager.addTwoTogether("5", "2", true);

		}</pre>

		The return values will be {@code result} = "7"

		<br><br><b>Notes:</b>
		<ol>

			<li>The return value will have the same String length as the max String length value out of the two parameters.</li>
			<li>For subtraction, the relation between the parameters must satisfy thisInteger >= otherInteger.</li>

		</ol>

		@param thisInteger Integer number as {@code String}.
		@param otherInteger Integer number as {@code String}.
		@param isAddition Is addition or substraction as {@code boolean}.

		@return String Result from the addition of the integers.
		@see math.core.Operationer#padZerosLeft(String, int)
		@since v0.0.3

	*/

	protected String addTwoTogether(String thisInteger, String otherInteger, boolean isAddition){

		int maxLength = thisInteger.length()>otherInteger.length() ? thisInteger.length() : otherInteger.length();

		String thisFullNumber = padZerosLeft(thisInteger, maxLength);
		String otherFullNumber = padZerosLeft(otherInteger, maxLength);

		StringBuilder result = this.addTwoTogether(new StringBuilder(), thisFullNumber, otherFullNumber, 0, 0, maxLength-1, isAddition);

		return result.toString();

	}

	/**

		Increases the integer's value stored as a {@code String} by one.

		<br><br><b>Usage Example</b>
		<pre>{@code

			String result = this.operationManager.increase("0");

		}</pre>

		The return values will be {@code result} = "1"

		<br><br><b>Notes:</b>
		<ol>

			<li>The return value will have the same String length as the parameter's length.</li>
			<li>The parameter must satisfy thisInteger >= 0.</li>

		</ol>

		@param thisInteger Integer number as {@code String}.
		@return String Parameter increased by one.
		@since v0.0.3

	*/

	protected String increase(String thisInteger){

		int length = thisInteger.length() - 1;

		String paddedOne = "0".repeat(length) + "1";

		StringBuilder result = this.addTwoTogether(new StringBuilder(), thisInteger, paddedOne, 0, 0, length, true);

		return result.toString();

	}

	/**

		Decreases the integer's value stored as a {@code String} by one.

		<br><br><b>Usage Example</b>
		<pre>{@code

			String result = this.operationManager.decrease("1");

		}</pre>

		The return values will be {@code result} = "0"

		<br><br><b>Notes:</b>
		<ol>

			<li>The return value will have the same String length as the parameter's length.</li>
			<li>The parameter must satisfy thisInteger >= 1s.</li>

		</ol>

		@param thisInteger Integer number as {@code String}.
		@return String Parameter decreased by one.
		@since v0.0.3

	*/

	protected String decrease(String thisInteger){

		int length = thisInteger.length() - 1;

		String paddedOne = "0".repeat(length) + "1";

		StringBuilder result = this.addTwoTogether(new StringBuilder(), thisInteger, paddedOne, 0, 0, length, false);

		return this.trimZerosLeft(result.toString());

	}

	/**

		Multiplies integers as {@code String} logicly and sequentially.

		<br><br><b>Usage Example</b>
		<pre>{@code

			String result = this.operationManager.multiplication("12", "3");

		}</pre>

		The return values will be {@code result} = "36"

		@param thisInteger First integer as {@code String}.
		@param otherInteger Second integer as {@code String}.

		@return String Result from the multiplication of the integers.
		@see math.core.Operationer#asyncAddition(String[])
		@since v0.0.1

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

		return this.asyncAddition(integerColumn);

	}

	/**

		Divides integers as {@code String} logicly and sequentially.

		<br><br><b>Usage Example</b>
		<pre>{@code

			String[] result = this.operationManager.division("7", "5", 20);

		}</pre>

		The return values will be {@code result[0]} = "1" and {@code result[1]} = "4"

		@param dividend Integer dividend as {@code String}.
		@param divisor Integer Divisor as {@code String}.
		@param presition Decimal presition as {@code long}.

		@return String[] Result from the division of the integers.
		@see math.core.Operationer#compareIntegerParts(String, String)
		@see math.core.Operationer#calculateDecimalPartForDivision(String, String, long)
		@since v0.0.1

	*/

	protected String[] division(String dividend, String divisor, long presition){

		int isGreaterThanDividend = this.compareIntegerParts(dividend, divisor);

		long integerPartCounter = 0;

		while(isGreaterThanDividend>0){

			dividend = this.trimZerosLeft(this.addTwoTogether(dividend.toString(), divisor.toString(), false));

			integerPartCounter++;

			isGreaterThanDividend = this.compareIntegerParts(dividend, divisor);

		}

		if (isGreaterThanDividend==0){ 

			return new String[] {(integerPartCounter + 1)+"", ""};

		}else{

			return new String[] {integerPartCounter+"", this.calculateDecimalPartForDivision(dividend+"0", divisor, presition)};

		}

	}

	/**

		Calculates the decimal part of a division using the remainder and divisor as {@code String} logicly and sequentially.

		<br><br><b>Usage Example</b>
		<pre>{@code

			String result = this.operationManager.calculateDecimalPartForDivision("20", "5", 20);

		}</pre>

		The return values will be {@code result} = "4"

		@param remainder Integer remainder as {@code String}.
		@param divisor Integer Divisor as {@code String}.
		@param presition Decimal presition as {@code long}.

		@return String Decimal part for the division.

		@see math.core.Operationer#compareIntegerParts(String, String)
		@see math.core.Operationer#addTwoTogether(String, String, boolean)
		@see math.core.Operationer#increase(String)

		@since v0.0.1

	*/

	protected String calculateDecimalPartForDivision(String remainder, String divisor, long presition){

		StringBuilder result = new StringBuilder("");

		for (long i=0; i<presition; i++){

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

			try {

				String[] results = new String[pairs + (integers.length % 2)];

				for (int i=0; i<pairs; i++){

					results[i] = futures[i].get();

				}

				if (integers.length%2!=0){

					results[pairs] = integers[integers.length - 1];

				}

				executor.shutdown();

				return asyncAdditionRecursive(results);

			} catch (Exception e){

				throw new RuntimeException(e);

			}

		});
	}

	private StringBuilder addTwoTogether(StringBuilder result, String thisFullNumber, String otherFullNumber, int carryIn, int carryOut, int index, boolean isAddition){

		int thisDigit = (thisFullNumber.charAt(index) - '0') - carryOut;
		int otherDigit = otherFullNumber.charAt(index) - '0';

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


		if (carryIn>0 && index==0){

			result.insert(0, carryIn);

			return result;

		}else if (index==0){

			return result;

		}else{

			index--;

			return this.addTwoTogether(result, thisFullNumber, otherFullNumber, carryIn, carryOut, index, isAddition);

		}

	}

}