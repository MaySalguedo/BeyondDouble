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

		Digit n = new Digit("-123.45");
		Digit m = new Digit(678.9);

		System.out.print(n+" + "+m+" = "+n.add(m));//802.35//555.45
		
		System.out.print("\n\n");
		
	}

}