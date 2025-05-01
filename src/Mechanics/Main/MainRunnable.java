package Mechanics.Main;

import java.math.RoundingMode;

import math.core.*;
import math.taylor.*;

/**

	MainRunnable class.

	@author Dandelion

*/

public class MainRunnable extends Util{

	public static void main(String[] abc){

		System.out.print("\n\n");

		double a = 2;
		long b = -4;

		Digit n = new Digit(a);

		System.out.println(Util.power(n, b)+"\n");

		for(long i=0; i<=100; i++){

			System.out.println(i+"! = "+Util.factorial(i));

		}

		System.out.print("\n\n");

	}

	private static void core_digit_test(){

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