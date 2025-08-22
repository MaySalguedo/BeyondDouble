package math.taylor;

import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Executors;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import math.core.Digit;

/**

	Stakeholder for non-taylor util functions that will be used throughout the math.taylor package.<br><br>

	Provides utility functions for advanced mathematical computations, particularly
	supporting Taylor series implementations through optimized factorial and power operations.
	This class features parallel computation capabilities for improved performance with
	large numbers and exponents.

	<p><b>Key Features:</b></p>
	<ul>

		<li><b>Parallel Factorial Computation</b> - Efficiently calculates factorials using divide-and-conquer approach</li>
		<li><b>Exponentiation</b> - Optimized power operations with support for negative exponents</li>
		<li><b>Asynchronous Processing</b> - Utilizes ForkJoinPool and CompletableFuture for concurrent execution</li>
		<li><b>Recursive Algorithms</b> - Implements binary exponentiation and range-based product calculation</li>

	</ul>

	<p><b>Use Cases:</b></p>
	<ol>

		<li>Calculating large factorials for Taylor series coefficients</li>
		<li>Computing powers for polynomial terms in series expansions</li>
		<li>Optimizing computationally intensive operations through parallelism</li>

	</ol>

	<p><b>Technical Implementation Notes:</b></p>
	<ul>

		<li>Static utility class designed for extension</li>
		<li>Thread-safe through immutable Digit instances</li>
		<li>Uses ForkJoinPool for factorial computation</li>

	</ul>

	<p><b>Usage Example:</b></p>
	<pre>{@code

		public class MyClass extends Util{

			//logic

		}

	}</pre>

	@author Dandelion
	@version v0.0.2
	@since v0.0.15

*/

public class Util{
	
	/*private static HashMap<Long, Digit> factorialMap = new HashMap<Long, Digit>(Map.ofEntries(

		new AbstractMap.SimpleEntry<Long, Digit>(0l, new Digit("1")),
		new AbstractMap.SimpleEntry<Long, Digit>(1l, new Digit("1"))

	));*/

	private static final ForkJoinPool factorialPool = new ForkJoinPool();
	private static final int SEQUENTIAL_THRESHOLD = 3;

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

	protected static Digit power(Digit base, int exponent) {

		if (exponent<0){

			return Util.power(base, -exponent).inverse();

		}else if (exponent==0){

			return base.unity();

		}else if (exponent==1){

			return base;

		}

		return asyncBinaryPower(base, exponent).join();

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

	protected static Digit factorial(long index) {

		if (index <= 1) {

			return new Digit(1);

		}

		return asyncFactorialRange(1, index).join();

	}

	@SuppressWarnings("unchecked") private static CompletableFuture<Digit> asyncFactorialRange(long start, long end) {

		return CompletableFuture.supplyAsync(() -> {

			long range = end - start + 1;

			if (range <= SEQUENTIAL_THRESHOLD) {

				return sequentialProduct(start, end);

			}

			long mid = (start + end) / 2;
			CompletableFuture<Digit> left = asyncFactorialRange(start, mid);
			CompletableFuture<Digit> right = asyncFactorialRange(mid + 1, end);

			return left.thenCombine(right, (l, r) -> l.multiply(r)).join();

		}, factorialPool);

	}

	private static Digit sequentialProduct(long start, long end) {

		Digit product = new Digit(1);

		for (long i = start; i <= end; i++) {

			product = product.multiply(new Digit(i));

		}

		return product;

	}

	@SuppressWarnings("unchecked") private static CompletableFuture<Digit> asyncBinaryPower(Digit base, int exponent) {

		if (exponent<=2) {

			return CompletableFuture.completedFuture(

				exponent == 2 ? base.multiply(base) : base

			);

		}
		
		int halfExponent = exponent / 2;
		int extra = exponent % 2;
		
		CompletableFuture<Digit> halfPower = CompletableFuture.supplyAsync(() -> asyncBinaryPower(base, halfExponent).join());
		
		return halfPower.thenComposeAsync(half -> {

			Digit squared = half.multiply(half);

			if (extra == 1) {

				return CompletableFuture.completedFuture(squared.multiply(base));

			}

			return CompletableFuture.completedFuture(squared);

		});

	}

}