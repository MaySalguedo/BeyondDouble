package math.taylor;

import java.util.HashMap;
import java.util.Map;
import java.util.AbstractMap;

import math.core.Digit;

/**

	Stakeholder for non-taylor util functions that will be used throughout the math.taylor package.

	<br><br><b>Usage Example</b>
	<pre>{@code

		import math.taylor.Util;

		public class MyClass extends Util{

			//logic

		}

	}</pre>

	@author Dandelion
	@version v0.0.1
	@since v0.0.15

*/

public class Util{
	
	private static HashMap<Long, Digit> factorialMap = new HashMap<Long, Digit>(Map.ofEntries(

		new AbstractMap.SimpleEntry<Long, Digit>(0l, new Digit("1")),
		new AbstractMap.SimpleEntry<Long, Digit>(1l, new Digit("1"))

	));

	/**

		Empty constructor.

	*/

	public Util(){}

	/**

		Powers one {@code Digit} intance to a {@code long} exponent.

		<br><br><b>Usage Example</b>
		<pre>{@code

			Digit n = new Digit("2");
			Digit m = new Digit(-4);

			Digit result = Util.power(n, m);

		}</pre>

		The return value will be {@code result = 0.0625}

		@param base Digit instance.
		@param exponent Exponent.
		@return Digit Result from the power of the exponent to the {@code Digit} instance.
		@see math.core.interfaces.EnhancedOperable#inverse()
		@see math.core.interfaces.EnhancedOperable#unity()
		@see math.core.interfaces.Operable#multiply(Object)

		@since v0.0.1

	*/

	protected static Digit power(Digit base, long exponent){

		if (exponent<0){

			return Util.power(base, -exponent).inverse();

		}else if (exponent==0){

			return base.unity();

		}else if (exponent==1){

			return base;

		}else{

			return base.multiply(Util.power(base, exponent - 1));

		}

	}

	/**

		Performs the {@code long} index factorial and returns its value as an instance of type {@code Digit}.

		<br><br><b>Usage Example</b>
		<pre>{@code

			Digit result = Util.factorial(6);

		}</pre>

		The return value will be {@code result = 720}

		@param index Factorial index.
		@return Digit Result from the factorial of the index.
		@see math.core.interfaces.Operable#multiply(Object)

		@since v0.0.1

	*/

	protected static Digit factorial(long index){

		if (Util.factorialMap.size()-1>=index){

			return Util.factorialMap.get(index);

		}

		Digit n = new Digit(index).multiply(Util.factorial(index - 1));
		Util.factorialMap.put(index, n);

		n.notation = false;

		return n;

	}

}