package math.core.exceptions;

/**

	Thrown to indicate that a number string does not conform to the required numeric format expected by mathematical operations.

	<p>This exception extends {@link NumberFormatException} and provides specialized handling for numeric format errors in mathematical contexts.</p>

	<p><b>Key Features:</b></p>
	<ul>

		<li>Custom exception for number formatting issues</li>
		<li>Provides both default and detailed message constructors</li>
		<li>Maintains standard NumberFormatException behavior</li>

	</ul>

	<p><b>Usage Example:</b></p>
	<pre>{@code

		public void MyFunction(){

			//logic

			throw new IllegalNumberFormatException();

		}

	}</pre>

	@author Dandelion
	@version v0.0.1
	@since v0.0.16

*/

public class IllegalNumberFormatException extends NumberFormatException{

	/**

		Constructs an IllegalNumberFormatException with no detail message.

		<p>Recommended for cases where the specific error context is provided through other means.</p>

		@since v0.0.1

	*/

	public IllegalNumberFormatException(){

		super();

	}

	/**

		Constructs an IllegalNumberFormatException with the specified detail message.

		<p>The message should clearly describe the format violation and, where possible, include the invalid input string.</p>
		
		@param message The detail message explaining the format violation
		@since v0.0.1

	*/

	public IllegalNumberFormatException(String message){

		super(message);

	}

}