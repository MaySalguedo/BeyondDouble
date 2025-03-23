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

		double a = 7.2;
		double b = 7;

		Digit n = new Digit(a);

		System.out.print(a+"%"+b+" = "+(a%b)+" | "+n.module(b));

		System.out.print("\n\n");
		
	}

}