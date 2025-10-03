package math.core;

/**

	Manages the format and convertion between differents numeric notations. 
	Intended to be used only as a stakeholder manager for its functions.<br><br>

	Provides comprehensive number formatting, validation, and conversion utilities for different numeric notations.
	This class handles conversion between scientific notation and standard form, manages thousands separators
	and decimal points, cleans non-numeric characters, and splits numbers into integer/decimal components.

	<p><b>Key Features:</b></p>
	<ul>

		<li><b>Scientific Notation Expansion</b> - Converts exponential format ({@code 1.23E4}) to standard form ({@code 12300})</li>
		<li><b>Number Validation and Cleaning</b> - Removes invalid characters and normalizes number formats</li>
		<li><b>Multi-Notation Support</b> - Formats numbers using either decimal point ({@code 1,234.56}) or comma ({@code 1.234,56}) notation</li>
		<li><b>Component Separation</b> - Splits numbers into integer and decimal parts</li>
		<li><b>Zero Trimming</b> - Removes insignificant leading/trailing zeros</li>
		<li><b>Thousands Formatting</b> - Adds appropriate separators for readability</li>

	</ul>

	<p><b>Use Cases:</b></p>
	<ol>

		<li>Normalizing user-input numbers with mixed formats</li>
		<li>Converting scientific notation for display purposes</li>
		<li>Formatting large numbers with proper thousands separators</li>
		<li>Preparing numbers for arithmetic operations by standardizing formats</li>
		<li>Cleaning malformed numeric strings from external sources</li>

	</ol>

	<table border="1">

		<caption><b>Notation Systems Supported:</b></caption>

		<tr><th>System</th><th>Integer Separator</th><th>Decimal Separator</th><th>Example</th></tr>
		<tr><td>Decimal Point Notation</td><td>Comma ({@code ,})</td><td>Period ({@code .})</td><td>{@code 1,234.56}</td></tr>
		<tr><td>Decimal Comma Notation</td><td>Period ({@code .})</td><td>Comma ({@code ,})</td><td>{@code 1.234,56}</td></tr>

	</table>

	<p><b>Usage Example:</b></p>
	<pre>{@code

		public class MyClass{

			private Notationer notationManager = new Notationer();

		}

	}</pre>

	@author Dandelion
	@version v0.0.6
	@since v0.0.1

*/

public class Notationer{

	/**

		The {@code char DECIMAL_POINT} with value {@code '.'} represents the decimal point for the decimal point notation.

	*/

	public char DECIMAL_POINT = '.';

	/**

		The {@code char THOUSANDS_SEPARATOR} with value {@code ','} represents the thousands separator for the decimal point notation.

	*/

	public char THOUSANDS_SEPARATOR = ',';

	/**

		Empty constructor.

	*/

	public Notationer(){}

	/**

		Recieves a {@code String} parameter and validates if it is a real number.
		
		<br><br><b>Usage Example</b>
		<pre>{@code

			boolean isValid = this.notationManager.isValidNumber("1.234E-5");

		}</pre>
		
		The return values will be {@code isValid = true}

		@param number Number as a {@code String}.
		@return boolean Returns either {@code true} if it is a valid number or {@code false} if it is not.
		@since v0.0.6

	*/

	public boolean isValidNumber(String number){

		if (number!=null ? number.isEmpty() : true) return false;

		return number.matches("^[-+]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][-+]?\\d+)?$");

	}

	/**

		Recieves a number as a {@code StringBuilder} parameter and normalizes it to make it readable in case it is not on a standard notation.
		
		<br><br><b>Usage Example</b>
		<pre>{@code

			String[] parts = this.notationManager.validateAndNormalize(new StringBuilder(

				"1234.56"

			));

		}</pre>
		
		The return values will be {@code parts[0] = 1234} and {@code parts[1] = 56}

		@param number Number as a {@code StringBuilder}.
		@return String[] Returns a {@code String} array with the integer part and the decimal part of the number.
		@see math.core.Notationer#expandScientificNotation(StringBuilder)
		@see math.core.Notationer#trimZeros(String)
		@see math.core.Notationer#cleanNonNumericCharacters(String, boolean)
		@see math.core.Notationer#splitIntoIntegerAndDecimalParts(String)
		@since v0.0.6

	*/

	public String[] Normalize(StringBuilder number){

		String normalized = expandScientificNotation(number).toString();

		String cleaned = cleanNonNumericCharacters(trimZeros(normalized), true).toString();

		return splitIntoIntegerAndDecimalParts(cleaned);

	}

	/**

		Recieves a number as a {@code StringBuilder} parameter, validates it to make sure it is infact a number and normalizes it 
		to make it readable in case it is not on a standard notation.
		
		<br><br><b>Usage Example</b>
		<pre>{@code

			String[] parts = this.notationManager.validateAndNormalize(new StringBuilder(

				"1234.56"

			));

		}</pre>
		
		The return values will be {@code parts[0] = 1234} and {@code parts[1] = 56}

		@param number Number as a {@code StringBuilder}.
		@return String[] Returns a {@code String} array with the integer part and the decimal part of the number.
		@exception IllegalArgumentException if {@code StringBuilder number} is either {@code null} or empty.
		@see math.core.Notationer#expandScientificNotation(StringBuilder)
		@see math.core.Notationer#trimZeros(String)
		@see math.core.Notationer#cleanNonNumericCharacters(String, boolean)
		@see math.core.Notationer#splitIntoIntegerAndDecimalParts(String)
		@since v0.0.1

	*/

	protected String[] validateAndNormalize(StringBuilder number){

		if (number!=null ? number.isEmpty() : true){

			throw new IllegalArgumentException("Invalid Number");

		}

		String normalized = expandScientificNotation(number).toString();

		String cleaned = cleanNonNumericCharacters(trimZeros(normalized), false).toString();

		return splitIntoIntegerAndDecimalParts(cleaned);

	}

	/**

		Recieves a number as two {@code String} parameters, one representing the integer part and the other representing its decimal part 
		and a {@code boolean} parameter that tells if the number uses decimal point notation. This parameter is {@code true} if it does.
		
		<br><br><b>Usage Example</b>
		<pre>{@code

			String n = this.notationManager.format("1234", "56");

		}</pre>
		
		The return values will be either {@code n = 1,234.56} in case the {@code boolean useDecimalPointNotation} parameter if {@code true} or 
		{@code n = 1.234,56} in case is {@code false}.

		@param integerPart Integer Part of a number as a {@code String}.
		@param decimalPart Decimal Part of a number as a {@code String}.
		@param useDecimalPointNotation Tells if it uses or not decimal point notation as a {@code boolean}.

		@return String Returns a number as a {@code String} with the specified notation declared on the {@code boolean useDecimalPointNotation} parameter.
		@see math.core.Notationer#formatIntegerPart(StringBuilder, String, char)
		@since v0.0.1

	*/

	protected String format(String integerPart, String decimalPart, boolean useDecimalPointNotation){
		
		StringBuilder formatted = new StringBuilder();
		
		char mainSeparator = !useDecimalPointNotation ? DECIMAL_POINT : THOUSANDS_SEPARATOR;
		char secondarySeparator = useDecimalPointNotation ? DECIMAL_POINT : THOUSANDS_SEPARATOR;

		formatIntegerPart(formatted, integerPart, mainSeparator);

		if (!decimalPart.isEmpty() && !decimalPart.matches("0+")){

			formatted.append(secondarySeparator);
			formatted.append(decimalPart);

		}

		return formatted.toString();

	}

	/**

		Recieves a number as a {@code StringBuilder} parameter, validates it to make sure it is infact in scientific notation, if so 
		it will return the number expanded, if not it will return itself.
		
		<br><br><b>Usage Example</b>
		<pre>{@code

			StringBuilder n = this.notationManager.expandScientificNotation(new StringBuilder(

				"1.23456E3"

			));

		}</pre>
		
		The return values will be {@code n = 1234.56}

		@param scientificNumber Number in scientific notation as a {@code StringBuilder}.
		@return StringBuilder Returns a {@code StringBuilder} with the number expanded.
		@since v0.0.1

	*/

	protected StringBuilder expandScientificNotation(StringBuilder scientificNumber){

		String[] parts = scientificNumber.toString().split("[Ee]");

		if (parts.length!=2) return scientificNumber;

		int exponent = Integer.parseInt(parts[1]);

		int originalDotIndex = parts[0].indexOf('.');
		int numbersAfterDot = originalDotIndex==-1 ? 0 : parts[0].length() - originalDotIndex - 1;
		int numbersBeforeDot = originalDotIndex==-1 ? parts[0].length() : originalDotIndex;
		
		StringBuilder result = new StringBuilder(parts[0].replace(".", ""));

		if (exponent==0){

			return new StringBuilder(parts[0]);

		}else if (exponent>0){

			if (exponent>numbersAfterDot){

				//Example: 1.23456E7 -> 12345600

				result.append("0".repeat(exponent - numbersAfterDot));
				
				return result;

			}else{

				//Example: 1.23456E2 -> 123.456

				result.insert(numbersBeforeDot + exponent, ".");

				return result;

			}

		}else{

			if (-exponent>numbersBeforeDot){

				//Example: either 1.23456E-7 -> 0.00000123456 or 123456E-7 -> 0.0123456

				result.insert(0, "0." + "0".repeat(

					- exponent - (numbersBeforeDot!=0 ? numbersBeforeDot : numbersAfterDot)

				));
				
				return result;

			}else{

				//Example: 123.456E-2 -> 1.23456

				result.insert(numbersBeforeDot + exponent, ".");

				return result;

			}

		}

	}

	/**

		Recieves a number as a {@code String} parameter, validates its back and front, and then removes every non numeric characters.
		
		<br><br><b>Usage Example</b>
		<pre>{@code

			StringBuilder n = this.notationManager.cleanNonNumericCharacters("--.012++", false);

		}</pre>
		
		The return values will be {@code parts[0] = 0.0123}

		@param number Number as a {@code String}.
		@param hasBeenValidated {@code boolean} parameter indicating if it has already been validated for a numberic format.
		@return StringBuilder Returns a {@code StringBuilder} number that is a full readable.
		@since v0.0.1

	*/

	protected StringBuilder cleanNonNumericCharacters(String number, boolean hasBeenValidated){

		StringBuilder cleaned = new StringBuilder(number);

		boolean decimalPointFound = false;

		if (cleaned.charAt(0)=='.'){

			cleaned.insert(0, "0");

		}

		int length = cleaned.length();

		if (cleaned.charAt(length-1)=='.'){

			cleaned.deleteCharAt(length-1);

			length--;

		}

		if (hasBeenValidated) return cleaned;

		for (int i=length-1; i>=0; i--){

			if (!decimalPointFound && cleaned.charAt(i)=='.') {

				decimalPointFound = true;

				continue;

			}

			if (!Character.isDigit(cleaned.charAt(i))){

				cleaned.deleteCharAt(i);

			}

		}

		return cleaned;

	}

	/**

		Recieves a number as a {@code String} parameter, validates if it has a decimal part, if not it will fills it with empty to make 
		sure it always return a 2 length {@code String} array.
		
		<br><br><b>Usage Example</b>
		<pre>{@code

			String[] parts = this.notationManager.splitIntoIntegerAndDecimalParts("1234.56");

		}</pre>
		
		The return values will be {@code parts[0] = 1234} and {@code parts[1] = 56}

		@param number Number as a {@code String}.
		@return String[] Returns a {@code String} array with the integer and decimal part.
		@since v0.0.1

	*/

	protected String[] splitIntoIntegerAndDecimalParts(String number){

		String[] parts = number.split("\\.");

		return parts.length==2 ? parts : new String[] {parts[0], ""};

	}

	/**

		Recieves a number as a {@code String} parameter, validates if it has a decimal point to extend the regrex statement to not trim 
		the zeros at the end if it is not a decimal number.
		
		<br><br><b>Usage Example</b>
		<pre>{@code

			String n = this.notationManager.trimZeros("00000001234.56000000000");

		}</pre>
		
		The return values will be {@code n = 1234.56}

		@param str Number as a {@code String}.
		@return String Returns a {@code String} with the zeros trimed.
		@since v0.0.1

	*/

	protected String trimZeros(String str){

		return str.replaceAll("^[0]+(?!$)"+(str.contains(".") ? "|[0]+$" : ""), "");

	}

	/**

		Recieves a empty {@code StringBuilder} recipient parameter, the integer part of a number as a {@code String} and {@code char} separator.
		
		<br><br><b>Usage Example</b>
		<pre>{@code

			StringBuffer n = new StringBuilder();
			char separator = ',';

			this.notationManager.formatIntegerPart(n, "1234", separator);

		}</pre>
		
		The new {@code n} value will be {@code 1,234}

		@param formatted Empty recipient as a {@code StringBuilder}.
		@param integerPart Integer part of a number as a {@code integerPart}.
		@param separator Separator as a {@code char}.
		
		@since v0.0.1

	*/

	protected void formatIntegerPart(StringBuilder formatted, String integerPart, char separator){

		int length = integerPart.length();

		for (int i=0; i<length; i++){

			if (i>0 && (length - i)%3==0){

				formatted.append(separator);

			}

			formatted.append(integerPart.charAt(i));

		}

	}

}