package Mechanics.Main;

import java.util.concurrent.ExecutionException;

import java.math.RoundingMode;

import math.core.*;
import math.taylor.*;

/**

	MainRunnable class.

	@author Dandelion

*/

public class MainRunnable extends Util{

	public static void main(String[] abc) throws InterruptedException, ExecutionException{

		System.out.print("\n\n");

		System.out.println(Trigonometry.PI());
		//System.out.print("sin(30°) = "+Trigonometry.sin(new Digit("0.52359877559829887307710723054658")));

		//taylor_util_test();

		System.out.print("\n\n");

	}

	private static void taylor_util_test() throws InterruptedException, ExecutionException{

		double a = 245;
		int b = 400;

		Digit n = new Digit(a);

		System.out.println(n+"^"+b+" = "+Util.power(n, b)+"\n");
		System.out.println(1000+"! = "+Util.factorial(1000));

		/*for(int i=0; i<=100; i++){

			System.out.println(i+"! = "+Util.factorial(i));

		}/**/

	}

	private static void core_digit_test() throws InterruptedException, ExecutionException{

		double a = 2156;
		double b = 0.0023;

		Digit n = new Digit(a);

		System.out.print(a+" + "+b+" = "+(a + b)+" | "+n.add(b)+"\n");
		System.out.print(a+" - "+b+" = "+(a - b)+" | "+n.subtract(b)+"\n");
		System.out.print(a+" * "+b+" = "+(a * b)+" | "+n.multiply(b)+"\n");
		System.out.print(a+" / "+b+" = "+(a / b)+" | "+n.divide(b, 20)+"\n");
		System.out.print(a+" % "+b+" = "+(a % b)+" | "+n.module(b));

	}

}