package math.core.interfaces;

import math.core.interfaces.Operable;

/**

	Abstractes the enhanced arithmatic math operations.

	<br><br><b>Usage Example</b>
	<pre>{@code

		public class MyClass implements EnhancedOperable<MyClass>{

			public int compareTo(MyClass myClass){return //logic}

			public MyClass add(MyClass other){return //logic}

			public MyClass subtract(MyClass other){return //logic}

			public MyClass multiply(MyClass other){return //logic}

			public MyClass divide(MyClass other){return //logic}

			public MyClass inverse(){return //logic}

			public MyClass unity(){return //logic}

		}

	}</pre>

	@param <T> The instance's type on which operations are performed.
	@author Dandelion
	@version v0.0.1
	@since v0.0.15

*/

public interface EnhancedOperable<T> extends Operable<T>{

	/**

		Performs the inverse {@code T} intance's value.

		@return The result from the inverse {@code T} intance's value as a new instance of type {@code T}.

	*/

	public T inverse();

	/**

		Performs the unity {@code T} intance's value.

		@return The result from the unity {@code T} intance's value as a new instance of type {@code T}.

	*/

	public T unity();

}