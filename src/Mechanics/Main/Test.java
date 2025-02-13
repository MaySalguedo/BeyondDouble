package Mechanics.Main;

import math.core.*;

import java.util.Scanner;

/**

	Test class.

	@author Dandelion

*/

public class Test{

	public static void main(String[] abc){

		System.out.print("\n\n");

		Digit n = new Digit("0");

		for (int f=-100; f<=100; f++){

			for (int c=0; c<=100; c++){

				System.out.println(n+">"+f+"."+c+" = "+n.compareTo(new Digit(f+"."+c)));

			}

		}
		
		System.out.print("\n\n");
		
	}

}