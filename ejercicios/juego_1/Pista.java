package juego_1;

import java.util.ArrayList;
import java.util.List;

public class Pista {

	public static int[] obtenerPista(float empieza, float anchura){
		return obtenerPista(empieza, anchura, -1);
	}
	public static int[] obtenerPista(float empieza, float anchura, float height){
		List<Integer> coordenadas = new ArrayList<>();
		height = Math.round(height);
		if(empieza <= 20){
			coordenadas.add(0);
			coordenadas.add(70);
			coordenadas.add(20);
			coordenadas.add(70);
		}
		if(empieza + anchura >= 40 && empieza <= 70){
			coordenadas.add(40);
			coordenadas.add(70);
			coordenadas.add(70);
			coordenadas.add(70);
		}
		if(empieza + anchura >= 60 && empieza <= 80){
			coordenadas.add(60);
			coordenadas.add(85);
			coordenadas.add(80);
			coordenadas.add(85);
		}
		if(empieza + anchura >= 90 && empieza <= 100){
			coordenadas.add(90);
			coordenadas.add(70);
			coordenadas.add(100);
			coordenadas.add(70);
		}
		if(empieza + anchura >= 110 && empieza <= 125){
			coordenadas.add(110);
			coordenadas.add(60);
			coordenadas.add(125);
			coordenadas.add(60);
		}
		int[] res = new int[coordenadas.size()];
		for(int i = 0;i < res.length; i++){
			res[i] = coordenadas.get(i);
		}
		//Elige la linea con la altura ideal
		if(height != -1 && res.length > 1){
			int[] heights = new int[Math.round(res.length/4)];
			for(int i = 0; i < heights.length; i++){
				heights[i] = res[((i+1)*4) - 3];
			}
			for(int i = 0; i < heights.length; i++){
				if(heights[i] >= height){
					if(heights[i] < res[1] || res[1] < height){
						res[1] = heights[i];
					}
				}
			}
		}
		//Borra los lineas de empieza si la altura == -1
		if(height == -1){
			for(int i = 0; i < res.length; i += 2){
				res[i] = res[i] - Math.round(empieza);
			}
		}
		if(res.length == 0){
			System.out.println(empieza + " - " + anchura + " - " + height);
		}
		return res;
	}
	
	public static int[] obtenerEstrellas(float empieza, float anchura){
		List<Integer> coordenadas = new ArrayList<>();
		if(empieza + anchura >= 50 && empieza <= 60){
			coordenadas.add(Math.round(empieza) - 50);
			coordenadas.add(30);
		}
		
		/*
		 * Convierte la lista a un int[]
		 */
		int[] res = new int[coordenadas.size()];
		for(int i = 0; i < coordenadas.size(); i++){
			res[i] = coordenadas.get(i);
		}
		return res;
	}
}
