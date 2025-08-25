package BeyondDouble;

import java.util.concurrent.ExecutionException;

import java.math.RoundingMode;

import math.core.*;
import math.taylor.*;

/**

	Main Runnable App class.

	@author Dandelion

*/

public class App extends Util {

	public static void main(String[] abc) throws InterruptedException, ExecutionException{

		System.out.print("\n\n");

		//Digit x = new Digit("0.5");//("0.52359877559829887307710723054658");

		//System.out.println(Trigonometry.PI());
		//System.out.print("arcsin("+x+") = "+Trigonometry.arcsin(x));

		//taylor_util_test();

		trigonometry_test();

		System.out.print("\n\n");

	}

	private static void trigonometry_test() {

		/*for (int i=0; i<=360; i++) {

			System.out.println("sin("+i+") = \n"+Math.sin(i*Math.PI/180)+" | \n"+Trigonometry.sin(

				new Digit(i).multiply(Trigonometry.pi).divide(180, 20)

			)+" |\n");

		}/**/

		/*for (int i=0; i<=360; i++) {

			System.out.println("cos("+i+") = \n"+Math.cos(i*Math.PI/180)+" | \n"+Trigonometry.cos(

				new Digit(i).multiply(Trigonometry.pi).divide(180, 20)

			)+" |\n");

		}/**/

		/*for (int i=0; i<=360; i++) {

			System.out.println("tan("+i+") = \n"+Math.tan(i*Math.PI/180)+" | \n"+Trigonometry.tan(

				new Digit(i).multiply(Trigonometry.pi).divide(180, 20)

			)+" |\n");

		}/**/

		/*for (int i=0; i<=360; i++) {

			double mtan = Math.tan(i*Math.PI/180);
			Digit dtan = Trigonometry.tan(

				new Digit(i).multiply(Trigonometry.pi).divide(180, 20)

			);

			System.out.println("arctan(tan("+i+") | "+mtan+") = \n"+Math.atan(mtan)+" | \n"+Trigonometry.arctan(dtan)+" |\n");

		}/**/

		/*for (int i=0; i<=360; i++) {

			double msin = Math.sin(i*Math.PI/180);
			Digit dsin = Trigonometry.sin(

				new Digit(i).multiply(Trigonometry.pi).divide(180, 20)

			);

			System.out.println("arcsin(sin("+i+") | "+msin+") = \n"+Math.asin(msin)+" | \n"+Trigonometry.arcsin(dsin)+" |\n");

		}/**/

		/*for (int i=0; i<=360; i++) {

			double mcos = Math.cos(i*Math.PI/180);
			Digit dcos = Trigonometry.cos(

				new Digit(i).multiply(Trigonometry.pi).divide(180, 20)

			);

			System.out.println("arccos(cos("+i+") | "+mcos+") = \n"+Math.acos(mcos)+" | \n"+Trigonometry.arccos(dcos)+" |\n");

		}/**/

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