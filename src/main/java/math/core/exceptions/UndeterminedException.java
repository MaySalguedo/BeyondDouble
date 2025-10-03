package math.core.exceptions;

/**

	Thrown to indicate that a mathematical operation has resulted in an indeterminate form.

	<p>This exception extends {@link ArithmeticException} and handles cases where a mathematical expression cannot be resolved to a definite value</p>

	<p><b>Key Features:</b></p>
	<ul>

		<li>Specialized exception for indeterminate mathematical forms</li>
		<li>Provides both default and detailed message constructors</li>
		<li>Maintains standard ArithmeticException behavior</li>
		<li>Identifies undefined calculation results</li>

	</ul>

	<p><b>Usage Example:</b></p>
	<pre>{@code

		public void MyFunction(){

			//logic

			throw new UndetermineException();

		}

	}</pre>

	@author Dandelion
	@version v0.0.1
	@since v0.0.16

*/

public class UndeterminedException extends ArithmeticException {

	/**

		Constructs an UndeterminedException with the specified detail message.

		<p>The message should clearly describe the format violation and, where possible, include the invalid input.</p>
		
		@param message The detail message explaining the format violation
		@since v0.0.1

	*/

	public UndeterminedException(String message){

		super(message);

	}

}