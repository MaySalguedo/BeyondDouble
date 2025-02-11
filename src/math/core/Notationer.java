package math.core;

/**

	Manages the format and convertion between differents numeric notations. 

	@author Dandelion
	@version 0.0.2
	@since 0.0.1

*/

public class Notationer{

	protected static final char DECIMAL_POINT = '.';
	protected static final char THOUSANDS_SEPARATOR = ',';

	/**

		Recieves a number as a {@code StringBuilder} paramether, validates it to make sure it is infact a number and normalizes it 
		to make readable in case it is not on a standard notation.
		
		<p>Example of use:</p>
		<pre>{@code

			String[] parts = Notationer.validateAndNormalize(new StringBuilder(

				"1234.56"

			));

		}</pre>
		
		The return values will be {@code parts[0]} = 1234 and {@code parts[1]} = 56

		@param number Number as a {@code StringBuilder}.
		@return String[] Returns a {@code String} array with the integer part and the decimal part of the number.
		@throw IllegalArgumentException if {@code StringBuilder number} is either {@code null} or empty.
		@since 0.0.1

	*/

	protected static String[] validateAndNormalize(StringBuilder number){

		if (number!=null ? number.isEmpty() : false){

			throw new IllegalArgumentException("Invalid Number");

		}

		String normalized = expandScientificNotation(number).toString();

		String cleaned = cleanNonNumericCharacters(trimZeros(normalized)).toString();

		return splitIntoIntegerAndDecimalParts(cleaned);

	}

	/**

		Recieves a number as two {@code String} paramethers, one representing the integer part and the other representing its decimal part 
		and a {@code boolean} paramether that tells if the number uses decimal point notation. This paramether is {@code true} if it does.
		
		<p>Example of use:</p>
		<pre>{@code

			String n = Notationer.format("1234", "56");

		}</pre>
		
		The return values will be either {@code n} = 1,234.56 in case the {@code boolean useDecimalPointNotation} paramether if {@code true} or 
		{@code n} = 1.234,56 in case is {@code false}.

		@param integerPart Integer Part of a number.
		@param DecimalPart Decimal Part of a number.
		@return String Returns a number as a {@code String} with the specified notation declared on the {@code boolean useDecimalPointNotation} paramether.
		@since 0.0.1

	*/

	protected static String format(String integerPart, String decimalPart, boolean useDecimalPointNotation){
		
		StringBuilder formatted = new StringBuilder();
		
		char mainSeparator = !useDecimalPointNotation ? DECIMAL_POINT : THOUSANDS_SEPARATOR;
		char secondarySeparator = useDecimalPointNotation ? DECIMAL_POINT : THOUSANDS_SEPARATOR;

		formatIntegerPart(formatted, integerPart, mainSeparator);

		if (!decimalPart.isEmpty() && !decimalPart.matches("0+")){

			formatted.append(secondarySeparator);
			formatted.append(decimalPart);

		}

		return formatted.toString();

	}/**/

	protected static StringBuilder expandScientificNotation(StringBuilder scientificNumber){

		String[] parts = scientificNumber.toString().split("[Ee]");
		//System.out.println(parts.length);
		if (parts.length!=2) return scientificNumber;

		int exponent = Integer.parseInt(parts[1]);

		int originalDotIndex = parts[0].indexOf('.');
		int numbersAfterDot = originalDotIndex==-1 ? 0 : parts[0].length() - originalDotIndex - 1;
		int numbersBeforeDot = originalDotIndex==-1 ? 0 : originalDotIndex;
		
		StringBuilder result = new StringBuilder(parts[0].replace(".", ""));

		if (exponent>0){

			if (exponent>numbersAfterDot){

				result.append("0".repeat(exponent - numbersAfterDot));
				
				return result;

			}else{

				result.insert(numbersBeforeDot + exponent, ".");

				return result;

			}

		}else{

			if (-exponent>numbersBeforeDot){

				result.insert(0, "0." + "0".repeat(

					- exponent - (numbersBeforeDot!=0 ? numbersBeforeDot : 1)

				));
				
				return result;

			}else{

				result.insert(numbersBeforeDot + exponent, ".");

				return result;

			}

		}

	}

	protected static StringBuilder cleanNonNumericCharacters(String number){

		StringBuilder cleaned = new StringBuilder(trimZeros(number));

		int length = cleaned.length();
		boolean decimalPointFound = false;

		if (cleaned.charAt(0)=='.'){

			cleaned.insert(0, "0");

		}

		if (cleaned.charAt(length-1)=='.'){

			cleaned.deleteCharAt(length-1);

		}

		length = cleaned.length();

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

	protected static String[] splitIntoIntegerAndDecimalParts(String number){

		String[] parts = number.split("\\.");

		return parts.length==2 ? parts : new String[] {parts[0], ""};

	}

	protected static String trimZeros(String str){

		return str.replaceAll("^[0]+(?!$)"+(str.contains(".") ? "|[0]+$" : ""), "");

	}

	protected static void formatIntegerPart(StringBuilder formatted, String integerPart, char separator){

		int length = integerPart.length();

		for (int i=0; i<length; i++){

			if (i>0 && (length - i)%3==0){

				formatted.append(separator);

			}

			formatted.append(integerPart.charAt(i));

		}

	}

}