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

	private static Digit pi = null;

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

		if (pi!=null){

			return pi;

		}

		Trigonometry.pi = asyncPI(27, 40).join();

		return Trigonometry.pi;

	}

	/*

		Computes the sine of an angle using Taylor series expansion.

		<p>The calculation is parallelized across available CPU cores and uses Taylor series:
		<ul>

		  <li>{@code sin(x) = x - x³/3! + x⁵/5! - x⁷/7! + ...}</li>

		</ul>

		<p><b>Usage Example:</b></p>
		<pre>{@code

			Digit PI = Trigonometry.sin(Trigonometry.PI()); // 0

		}</pre>

		@param x Angle in radians as a {@link Digit} instance
		@return Sine value as a {@link Digit} object

	*

	public static Digit sin(Digit x) {

		return asyncSin(x, 11, 29).join();

	}/**/

	@SuppressWarnings("unchecked") private static CompletableFuture<Digit> asyncSin(Digit x, int iteration, int precision) {

		int cores = Math.min(iteration, Runtime.getRuntime().availableProcessors());
		ExecutorService executor = Executors.newFixedThreadPool(cores);

		CompletableFuture<Digit>[] futures = new CompletableFuture[iteration];

		for (int n=0; n<iteration; n++) {

			final int nth_term = n;

			futures[n] = CompletableFuture.supplyAsync(() -> computeSinTerm(x, nth_term, precision), executor);

		}

		return asyncArrayAddition(futures, 0, iteration - 1).thenApplyAsync(result -> {

			executor.shutdown();
			return result;

		}, executor);

	}

	@SuppressWarnings("unchecked") private static CompletableFuture<Digit> asyncArrayAddition(CompletableFuture<Digit>[] futures, int start, int end){

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

	private static Digit computeSinTerm(Digit x, int nth_term, int precision) {

		int exponent = 2 * nth_term + 1;

		CompletableFuture<Digit> futurePower = CompletableFuture.supplyAsync(() -> power(x, exponent));

		CompletableFuture<Digit> futureFactorial = CompletableFuture.supplyAsync(() -> factorial(exponent));

		return futurePower.thenCombineAsync(futureFactorial, (powerTerm, factorialTerm) -> {

			return new Digit((nth_term % 2) == 0 ? 1 : -1).multiply(powerTerm.divide(factorialTerm, precision));

		}).join();

	}

	@SuppressWarnings("unchecked") private static CompletableFuture<Digit> asyncPI(int iteration, int precision) {

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