package math.taylor;

import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

import java.util.HashMap;
import java.util.Map;
import java.util.AbstractMap;

import math.taylor.Util;
import math.core.Digit;

/**

	Provides trigonometric functions and their inverses using Taylor series approximations
	with arbitrary-precision arithmetic. This class computes trigonometric ratios (sin, cos, tan, 
	csc, sec, cot) and inverse trigonometric functions (arcsin, arccos, arctan, arccsc, arcsec, arccot) 
	along side mathematical constant π.

	<p>Key features:</p>
	<ul>
		<li>Arbitrary-precision calculations using {@link math.core.Digit}</li>
		<li>Parallel computation for performance optimization</li>
		<li>Asynchronous implementations using {@link CompletableFuture}</li>
		<li>Precision-controlled results</li>
	</ul>

	<p><b>Usage Example:</b></p>
	<pre>{@code

		Digit PI = Trigonometry.PI(); // 3.1415926535897932384626433832795028841988

	}</pre>

	@author Dandelion
	@version v0.0.1
	@since v0.0.16

*/

public class Trigonometry extends Util{

	/**

		The {@code Digit pi} contant stores a π value up to 38 decimals.

	*/

	public static final Digit pi = new Digit("3.1415926535897932384626433832795028841972");

	/**

		Empty constructor.

	*/

	public Trigonometry(){}

	/**
		Returns the mathematical constant π (pi) with arbitrary precision.
		<p>
		This method uses caching for efficiency - the first call computes π using a parallelized Machin-like formula, 
		while subsequent calls return the cached value. The computation uses:
		<ul>

		  <li>π {@code = 4 * (4 * arctan(1/5) - arctan(1/239))}</li>

		</ul>

		<p><b>Usage Example:</b></p>
		<pre>{@code

			Digit PI = Trigonometry.PI(); // 3.1415926535897932384626433832795028841988

		}</pre>

		@return π constant as a {@link Digit} object

	*/

	public static final Digit PI() {

		return asyncPI(27, 40).join();

	}

	/**

		Computes the sine of an angle using Taylor series expansion.

		<p>The calculation is parallelized across available CPU cores and uses Taylor series:
		<ul>

			<li>{@code sin(x) = x - x³/3! + x⁵/5! - x⁷/7! + ...}</li>

		</ul>

		<p><b>Usage Example:</b></p>
		<pre>{@code

			Digit sin = Trigonometry.sin(Trigonometry.PI()); // 0

		}</pre>

		@param x Angle in radians as a {@link Digit} instance
		@return Sine value as a {@link Digit} object

	*/

	public static Digit sin(Digit x) {

		return asyncSin(x, 11, 29).join();

	}

	/**

		Computes the cosine of an angle using Taylor series expansion.

		<p>The calculation is parallelized across available CPU cores and uses Taylor series:
		<ul>

			<li>{@code cos(x) = x - x²/2! + x⁴/4! - x⁶/6! + ...}</li>

		</ul>

		<p><b>Usage Example:</b></p>
		<pre>{@code

			Digit cos = Trigonometry.cos(Trigonometry.PI()); // -1

		}</pre>

		@param x Angle in radians as a {@link Digit} instance
		@return Cosine value as a {@link Digit} object

	*/

	public static Digit cos(Digit x) {

		return asyncCos(x, 11, 29).join();

	}

	/**

		Computes the tangent of an angle using Taylor series expansion.

		<p>The calculation is parallelized across available CPU cores and uses Taylor series:
		<ul>

			<li>{@code tan(x) = sin(x)/cos(x)}</li>

		</ul>

		<p><b>Usage Example:</b></p>
		<pre>{@code

			Digit tan = Trigonometry.tan(Trigonometry.PI()); // 0

		}</pre>

		@param x Angle in radians as a {@link Digit} instance
		@return Tangent value as a {@link Digit} object

	*/

	public static Digit tan(Digit x) {

		return asyncTanOrCot(x, 11, 29, true).join();

	}

	/**

		Computes the cosecant of an angle using Taylor series expansion.

		<p>The calculation is parallelized across available CPU cores and uses Taylor series:
		<ul>

			<li>{@code csc(x) = 1/cos(x)}</li>

		</ul>

		<p><b>Usage Example:</b></p>
		<pre>{@code

			Digit csc = Trigonometry.csc(new Digit("0.52359877559829887307710723054658")); // 2

		}</pre>

		@param x Angle in radians as a {@link Digit} instance
		@return Cosecant value as a {@link Digit} object

	*/

	public static Digit csc(Digit x) {

		return asyncSin(x, 11, 29).join().inverse(29);

	}

	/**

		Computes the secant of an angle using Taylor series expansion.

		<p>The calculation is parallelized across available CPU cores and uses Taylor series:
		<ul>

			<li>{@code sec(x) = 1/sin(x)}</li>

		</ul>

		<p><b>Usage Example:</b></p>
		<pre>{@code

			Digit sec = Trigonometry.sin(new Digit("0.52359877559829887307710723054658")); // 2

		}</pre>

		@param x Angle in radians as a {@link Digit} instance
		@return Secant value as a {@link Digit} object

	*/

	public static Digit sec(Digit x) {

		return asyncCos(x, 11, 29).join().inverse();

	}

	/**

		Computes the cotangent of an angle using Taylor series expansion.

		<p>The calculation is parallelized across available CPU cores and uses Taylor series:
		<ul>

			<li>{@code cot(x) = cos(x)/sin(x)}</li>

		</ul>

		<p><b>Usage Example:</b></p>
		<pre>{@code

			Digit cot = Trigonometry.cot(new Digit("0.78539816339744830961566084581988")); // 1

		}</pre>

		@param x Angle in radians as a {@link Digit} instance
		@return Cotangent value as a {@link Digit} object

	*/

	public static Digit cot(Digit x) {

		return asyncTanOrCot(x, 11, 29, false).join();

	}

	/**
		Computes the arcsine of a value using Taylor series expansion.

		<p>The calculation is parallelized across available CPU cores and uses Taylor series:
		<ul>

			<li>{@code arcsin(x) = x + (1/2)(x³/3) + (1*3)/(2*4)(x⁵/5) + (1*3*5)/(2*4*6)(x⁷/7) + ...}</li>

		</ul>

		<p><b>Usage Example:</b></p>
		<pre>{@code

			Digit arcsin = Trigonometry.arcsin(new Digit("0.5")); // π/6 ≈ 0.5235987755982989

		}</pre>

		@param x Value between -1 and 1 as a {@link Digit} instance
		@return Arcsine value in radians as a {@link Digit} object

	*/

	public static Digit arcsin(Digit x) {

		return asyncArcsin(x, 15, 30).join();

	}

	/**
		Computes the arccosine of a value using Taylor series expansion.

		<p>The calculation is parallelized across available CPU cores and uses Taylor series:
		<ul>

			<li>{@code arccos(x) = π/2 - arcsin(x)}</li>

		</ul>

		<p><b>Usage Example:</b></p>
		<pre>{@code

			Digit arccos = Trigonometry.arccos(new Digit("0")); // 1

		}</pre>

		@param x Value in radians as a {@link Digit} instance
		@return Arccosine value in radians as a {@link Digit} object

	*/

	public static Digit arccos(Digit x) {

		return Trigonometry.pi.divide(2, 38).subtract(asyncArcsin(x, 15, 30).join());

	}

	/**

		Computes the arctangent of a tan(x) value using Taylor series expansion.

		<p>The calculation is parallelized across available CPU cores and uses Taylor series:
		<ul>

			<li>{@code arctan(x) = x - x³/3 + x⁵/5 - x⁷/7 + ...}</li>

		</ul>

		<p><b>Usage Example:</b></p>
		<pre>{@code

			Digit arctan = Trigonometry.arctan(0); // 0

		}</pre>

		@param x Vlue in radians as a {@link Digit} instance
		@return Arctangent value as a {@link Digit} object

	*/

	public static Digit arctan(Digit x) {

		if (x.abs().compareToOne()>0){

			Digit result = asyncActan_v2(x.abs(), 11, 29).join();

			return x.isNegative ? result.negate() : result;

		}

		return asyncArctan(x, 11, 29).join();

	}

	/**
		Computes the arccosecant of a value using Taylor series expansion.

		<p>The calculation is parallelized across available CPU cores and uses Taylor series:
		<ul>

			<li>{@code arccsc(x) = 1/arcsin(x)}</li>

		</ul>

		<p><b>Usage Example:</b></p>
		<pre>{@code

			Digit arccsc = Trigonometry.arcsin(new Digit("0.5")); // 6/π ≈ 1.909859317102

		}</pre>

		@param x Value between -1 and 1 as a {@link Digit} instance
		@return Arccsc value in radians as a {@link Digit} object

	*/

	public static Digit arccsc(Digit x) {

		return asyncArcsin(x, 15, 30).join().inverse();

	}

	/**
		Computes the arcsecant of a value using Taylor series expansion.

		<p>The calculation is parallelized across available CPU cores and uses Taylor series:
		<ul>

			<li>{@code arcsec(x) = 1/arccos(x)}</li>

		</ul>

		<p><b>Usage Example:</b></p>
		<pre>{@code

			Digit arccos = Trigonometry.arcsec(new Digit("0")); // 2/π ≈ 0.63631977236758

		}</pre>

		@param x Value in radians as a {@link Digit} instance
		@return Arcsec value in radians as a {@link Digit} object

	*/

	public static Digit arcsec(Digit x) {

		return Trigonometry.pi.divide(2, 38).subtract(asyncArcsin(x, 15, 30).join()).inverse();

	}

	/**

		Computes the arctangent of a tan(x) value using Taylor series expansion.

		<p>The calculation is parallelized across available CPU cores and uses Taylor series:
		<ul>

			<li>{@code arccot(x) = arccos(x)/arcsin(x)}</li>

		</ul>

		<p><b>Usage Example:</b></p>
		<pre>{@code

			Digit arctan = Trigonometry.arctan(0); // 0

		}</pre>

		@param x Value in radians as a {@link Digit} instance
		@return Arccotangent value as a {@link Digit} object

	*/

	public static Digit arccot(Digit x) {

		return arccos(x).divide(arcsin(x), 38);

	}

	@SuppressWarnings("unchecked") private static CompletableFuture<Digit> asyncArcsin(Digit x, int iteration, int precision) {

		int cores = Math.min(iteration, Runtime.getRuntime().availableProcessors());
		ExecutorService executor = Executors.newFixedThreadPool(cores);

		CompletableFuture<Digit>[] futures = new CompletableFuture[iteration];

		for (int n = 0; n < iteration; n++) {

			final int nth_term = n;

			futures[n] = CompletableFuture.supplyAsync(() -> computeArcsinTerm(x, nth_term, precision), executor);

		}

		return asyncArrayAddition(futures, 0, iteration - 1).thenApplyAsync(result -> {

			executor.shutdown();
			return result;

		}, executor);

	}

	private static CompletableFuture<Digit> asyncActan_v2(Digit x, int iteration, int precision){

		int cores = Math.min(iteration, Runtime.getRuntime().availableProcessors());
		ExecutorService executor = Executors.newFixedThreadPool(cores);

		CompletableFuture<Digit> halfPi = CompletableFuture.supplyAsync(() -> Trigonometry.pi.divide(2, 38), executor);
		CompletableFuture<Digit> inverse = CompletableFuture.supplyAsync(() -> x.inverse(38), executor);

		return halfPi.thenCombineAsync(inverse, (a, b) -> a.subtract(arctan(b)));

	}

	@SuppressWarnings("unchecked") private static CompletableFuture<Digit> asyncArctan(Digit x, int iteration, int precision) {

		int cores = Math.min(iteration, Runtime.getRuntime().availableProcessors());
		ExecutorService executor = Executors.newFixedThreadPool(cores);

		CompletableFuture<Digit>[] futures = new CompletableFuture[iteration];

		for (int n=0; n<iteration; n++) {

			final int nth_term = n;

			futures[n] = CompletableFuture.supplyAsync(() -> computeArctanTerm(x, nth_term, precision), executor);

		}

		return asyncArrayAddition(futures, 0, iteration - 1).thenApplyAsync(result -> {

			executor.shutdown();
			return result;

		}, executor);

	}

	private static CompletableFuture<Digit> asyncTanOrCot(Digit x, int iteration, int precision, boolean isTanOrCot){

		CompletableFuture<Digit> sin = asyncSin(x, iteration, precision);
		CompletableFuture<Digit> cos = asyncCos(x, iteration, precision);

		if (!isTanOrCot){

			return cos.thenCombineAsync(sin, (a, b) -> a.divide(b, precision));

		}

		return sin.thenCombineAsync(cos, (a, b) -> a.divide(b, precision));

	}

	@SuppressWarnings("unchecked") private static CompletableFuture<Digit> asyncCos(Digit x, int iteration, int precision) {

		int cores = Math.min(iteration, Runtime.getRuntime().availableProcessors());
		ExecutorService executor = Executors.newFixedThreadPool(cores);

		CompletableFuture<Digit>[] futures = new CompletableFuture[iteration];

		for (int n=0; n<iteration; n++) {

			final int nth_term = n;

			futures[n] = CompletableFuture.supplyAsync(() -> computeSinOrCosTerm(x, nth_term, precision, false), executor);

		}

		return asyncArrayAddition(futures, 0, iteration - 1).thenApplyAsync(result -> {

			executor.shutdown();
			return result;

		}, executor);

	}

	@SuppressWarnings("unchecked") private static CompletableFuture<Digit> asyncSin(Digit x, int iteration, int precision) {

		int cores = Math.min(iteration, Runtime.getRuntime().availableProcessors());
		ExecutorService executor = Executors.newFixedThreadPool(cores);

		CompletableFuture<Digit>[] futures = new CompletableFuture[iteration];

		for (int n=0; n<iteration; n++) {

			final int nth_term = n;

			futures[n] = CompletableFuture.supplyAsync(() -> computeSinOrCosTerm(x, nth_term, precision, true), executor);

		}

		return asyncArrayAddition(futures, 0, iteration - 1).thenApplyAsync(result -> {

			executor.shutdown();
			return result;

		}, executor);

	}

	private static CompletableFuture<Digit> asyncArrayAddition(CompletableFuture<Digit>[] futures, int start, int end){

		final int intervalue = end - start + 1;

		if (intervalue==1){

			return futures[start];

		}else if (intervalue==2){

			return futures[start].thenCombine(futures[end], (a, b) -> a.add(b));

		}

		final int mid = (start + end) / 2;

		CompletableFuture<Digit> left = asyncArrayAddition(futures, start, mid);
		CompletableFuture<Digit> right = asyncArrayAddition(futures, mid + 1, end);

		return left.thenCombineAsync(right, (a, b) -> a.add(b));

	}

	private static Digit computeSinOrCosTerm(Digit x, int nth_term, int precision, boolean isSinOrCos) {

		int exponent = 2 * nth_term + (isSinOrCos ? 1 : 0);

		CompletableFuture<Digit> futurePower = CompletableFuture.supplyAsync(() -> power(x, exponent));

		CompletableFuture<Digit> futureFactorial = CompletableFuture.supplyAsync(() -> factorial(exponent));

		return futurePower.thenCombineAsync(futureFactorial, (powerTerm, factorialTerm) -> {

			return new Digit((nth_term % 2) == 0 ? 1 : -1).multiply(powerTerm.divide(factorialTerm, precision));

		}).join();

	}

	/*private static Digit computeArcsinTerm(Digit x, int nth_term, int precision, ExecutorService executor) {

		// 2*n + 1
		int exponent = 2 * nth_term + 1;

		// (2n)!
		CompletableFuture<Digit> futureFactorial = CompletableFuture.supplyAsync(() -> factorial(2*nth_term));

		// 4^n
		CompletableFuture<Digit> future4thPower = CompletableFuture.supplyAsync(() -> power(new Digit(4), exponent));

		// (n!)^2
		CompletableFuture<Digit> futurePowerFactorial = CompletableFuture.supplyAsync(() -> power(factorial(nth_term), 2));

		// x^(2*n + 1)
		CompletableFuture<Digit> futurePower = CompletableFuture.supplyAsync(() -> power(x, exponent));

		CompletableFuture<Digit> coefficient = futureFactorial.thenCombineAsync(futurePower, (a, b) ->  a.multiply(b), executor);

		CompletableFuture<Digit> denominator = future4thPower.thenCombineAsync(futurePowerFactorial, (a, b) ->  a.multiply(b.multiply(exponent)), executor);

		return coefficient.thenCombineAsync(denominator, (a, b) ->  a.divide(b, precision), executor).join();

	}/**/

	private static Digit computeArcsinTerm(Digit x, int nth_term, int precision) {

		int exponent = 2 * nth_term + 1;

		// (2n)! / (4^n * (n!)^2)
		CompletableFuture<Digit> futureCoefficient = CompletableFuture.supplyAsync(() -> {

			Digit numerator = factorial(2 * nth_term);
			Digit denominator = power(new Digit(4), nth_term).multiply(power(factorial(nth_term), 2));

			return numerator.divide(denominator, precision);

		});

		// x^(2*n + 1)
		CompletableFuture<Digit> futurePower = CompletableFuture.supplyAsync(() -> power(x, exponent));

		return futureCoefficient.thenCombineAsync(futurePower, (coefficient, powerTerm) ->  coefficient.multiply(powerTerm).divide(new Digit(exponent), precision)).join();

	}/**/

	private static Digit computeArctanTerm(Digit x, int nth_term, int precision) {

		int exponent = 2 * nth_term + 1 ;

		CompletableFuture<Digit> futurePower = CompletableFuture.supplyAsync(() -> power(x, exponent));

		CompletableFuture<Digit> divident = CompletableFuture.supplyAsync(() -> new Digit(exponent));

		return futurePower.thenCombineAsync(divident, (powerTerm, dividentTerm) -> {

			return new Digit((nth_term % 2) == 0 ? 1 : -1).multiply(powerTerm.divide(dividentTerm, precision));

		}).join();

	}

	private static CompletableFuture<Digit> asyncPI(int iteration, int precision) {

		int cores = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(cores);

		Digit sign = new Digit(1);

		CompletableFuture<Digit> arctan1_5 = CompletableFuture.supplyAsync(() -> parallelArctan(5, iteration, sign, precision, executor), executor);

		CompletableFuture<Digit> arctan1_239 = CompletableFuture.supplyAsync(() -> parallelArctan(239, 11, sign, precision, executor), executor);

		return arctan1_5.thenCombineAsync(arctan1_239, (a, b) -> {

			executor.shutdown();

			final Digit four = new Digit(4);

			return four.multiply(

				four.multiply(a).subtract(b)

			);

		}, executor);

	}

	@SuppressWarnings("unchecked") private static Digit parallelArctan(int x, int iterations, Digit sign, int precision, ExecutorService executor) {

		if (iterations <= 30) {

			return computePartialArctan(x, 0, iterations, sign, precision);

		}/**/

		int chunks = Runtime.getRuntime().availableProcessors();
		int chunkSize = (iterations + chunks - 1) / chunks;

		CompletableFuture<Digit>[] futures = new CompletableFuture[chunks];

		for (int i = 0; i < chunks; i++) {

			int start = i * chunkSize;
			int end = Math.min(start + chunkSize, iterations);

			if (start >= iterations) {

				futures[i] = CompletableFuture.completedFuture(new Digit(0));
				continue;

			}

			Digit chunkInitialSign = sign;

			if (start % 2 == 1) {

				chunkInitialSign = chunkInitialSign.negate();

			}

			final int finalStart = start;
			final int finalEnd = end;
			final Digit finalChunkSign = chunkInitialSign;
			futures[i] = CompletableFuture.supplyAsync(() -> computePartialArctan(x, finalStart, finalEnd, finalChunkSign, precision), executor);

		}

		return CompletableFuture.allOf(futures).thenApply(v -> {

			Digit result = new Digit(0);

			for (int i = 0; i < chunks; i++) {

				result = result.add(futures[i].join());

			}

			return result;

		}).join();

	}

	private static Digit computePartialArctan(int x, int start, int end, Digit initialSign, int precision) {

		if (start >= end) {

			return new Digit(0);

		}

		Digit partial = new Digit(0);
		Digit termSign = initialSign;
		Digit oneOverX = new Digit(1).divide(new Digit(x), precision);
		Digit oneOverXSquared = oneOverX.multiply(oneOverX);
		Digit currentTerm = power(oneOverX, 2 * start + 1);

		for (int n = start; n < end; n++) {

			int denominator = 2 * n + 1;
			Digit term = currentTerm.divide(new Digit(denominator), precision);
			term = term.multiply(termSign);
			partial = partial.add(term);
			
			currentTerm = currentTerm.multiply(oneOverXSquared);
			termSign = termSign.negate();

		}

		return partial;

	}

}