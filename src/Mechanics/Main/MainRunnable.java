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

		System.out.print(a+" + "+b+" = "+(a + b)+" | "+n.add(b)+"\n");
		System.out.print(a+" - "+b+" = "+(a - b)+" | "+n.subtract(b)+"\n");
		System.out.print(a+" * "+b+" = "+(a * b)+" | "+n.multiply(b)+"\n");
		System.out.print(a+" / "+b+" = "+(a / b)+" | "+n.divide(b, 20)+"\n");
		System.out.print(a+" % "+b+" = "+(a % b)+" | "+n.module(b));/**/

		System.out.print("\n\n");

	}

}