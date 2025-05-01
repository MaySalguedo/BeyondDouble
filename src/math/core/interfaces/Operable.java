package math.core.interfaces;

/**

	Abstracts the four basic mathematical operations.

	<br><br><b>Usage Example</b>
	<pre>{@code

		public class MyClass implements Operable<MyClass>{

			public int compareTo(MyClass myClass){return //logic}

			public MyClass add(MyClass other){return //logic}

			public MyClass subtract(MyClass other){return //logic}

			public MyClass multiply(MyClass other){return //logic}

			public MyClass divide(MyClass other){return //logic}

		}

	}</pre>

	@param <T> The instance's type on which operations are performed.
	@author Dandelion
	@version v0.0.1
	@since v0.0.15

*/

public interface Operable<T> extends Comparable<T>{


	/**

		Performs addition between this instance and another instance of type {@code T}.

		@param other {@code T} instance to be added.
		@return The result of the addition as a new instance of type {@code T}.

	*/

	public T add(T other);

	/**

		Performs subtraction between this instance and another instance of type {@code T}.

		@param other {@code T} instance to be subtracted.
		@return The result of the subtraction as a new instance of type {@code T}.

	*/

	public T subtract(T other);

	/**

		Performs multiplication between this instance and another instance of type {@code T}.

		@param other {@code T} instance to be multiplied.
		@return The result of the multiplication as a new instance of type {@code T}.

	*/

	public T multiply(T other);

	/**

		Performs division between this instance and another instance of type {@code T}.

		@param other {@code T} instance to be divideed.
		@return The result of the division as a new instance of type {@code T}.

	*/

	public T divide(T other);

}