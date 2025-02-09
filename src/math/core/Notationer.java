package math.core;

/**

	@author Dandelion

	Manages the format and convertion between differents numeric notations. 

*/

public class Notationer{

	protected static final char DECIMAL_POINT = '.';
	protected static final char THOUSANDS_SEPARATOR = ',';

	protected static String[] validateAndNormalize(StringBuilder number){

		if (number!=null ? number.isEmpty() : false){

			throw new IllegalArgumentException("Invalid Number");

		}

		String normalized = expandScientificNotation(number).toString();

		String cleaned = cleanNonNumericCharacters(trimZeros(normalized)).toString();

		return splitIntoIntegerAndDecimalParts(cleaned);

	}/**/

	protected static String format(String integerPart, String decimalPart, boolean useDecimalPoint){
		
		StringBuilder formatted = new StringBuilder();
		
		char mainSeparator = !useDecimalPoint ? DECIMAL_POINT : THOUSANDS_SEPARATOR;
		char secondarySeparator = useDecimalPoint ? DECIMAL_POINT : THOUSANDS_SEPARATOR;

		formatIntegerPart(formatted, integerPart, mainSeparator);

		if (!decimalPart.isEmpty() && !decimalPart.matches("0+")){

			formatted.append(secondarySeparator);
			formatted.append(decimalPart);

		}

		return formatted.toString();

	}/**/

	protected static StringBuilder expandScientificNotation(StringBuilder scientificNumber){

		String[] parts = scientificNumber.toString().split("[Ee]");

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

					- exponent - numbersBeforeDot

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

	}/**/

	protected static String trimZeros(String str){

		return str.replaceAll("^[0]+|[0]+$", "");

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