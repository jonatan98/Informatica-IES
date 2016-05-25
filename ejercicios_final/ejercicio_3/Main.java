package ejercicio_3;

import java.util.Random;
import java.util.Scanner;

public class Main {

	/*
	 * Examen de las tablas de multiplicar
	 */
	
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		pl("Introduce el número minimo de la tabla de multiplicar");
		int num_min = s.nextInt();
		if(num_min < 1){
			num_min = 1;
			pl("Número introducido menor a 1, cambiado a 1");
		}
		pl("Introduce el número maximo de la tabla de multiplicar");
		int num_max = s.nextInt();
		if(num_min < 1){
			num_min = 1;
			pl("Número introducido menor a 1, cambiado a 1");
		}
		if(num_min <= num_max){
			pl("El número minimo debe ser menor al número maximo");
		}
		
		int correct = 0;
		
		Random r = new Random();
		for(int i = 0; i < 10; i++){
			int num1 = r.nextInt(num_max - num_min) + num_min;
			int num2 = r.nextInt(num_max - num_min) + num_min;
			pl(num1 + " * " + num2);
			if(s.nextInt() == num1 * num2){
				correct++;
			}
		}
		
		if(correct >= 5){
			pl("¡Muy bien. has aprobado, tu nota es " + correct + "!");
		}else{
			pl("Estudia mas.");
		}
		
		s.close();
	}
	
	private static void pl(String s){
		System.out.println(s);
	}
}
