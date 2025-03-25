package Mechanics.Main;

import java.math.RoundingMode;

import math.core.*;

/**

	MainRunnable class.

	@author Dandelion

*/

public class MainRunnable{

	public static void main(String[] abc){

		System.out.print("\n\n");

		double a = 2156;
		double b = 0.0023;

		Digit n = new Digit(a);

		System.out.println(a+" + "+b+" = "+(a + b)+" | "+n.add(b));
		System.out.println(a+" - "+b+" = "+(a - b)+" | "+n.subtract(b));
		System.out.println(a+" * "+b+" = "+(a * b)+" | "+n.multiply(b));
		System.out.println(a+" / "+b+" = "+(a / b)+" | "+n.divide(b, 19));
		System.out.print(a+" % "+b+" = "+(a % b)+" | "+n.module(b));/**/

		System.out.print("\n\n");

	}

}