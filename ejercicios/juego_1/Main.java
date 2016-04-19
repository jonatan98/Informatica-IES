package juego_1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Main extends JPanel {

	/*
	 * Imagenes
	 */
	static BufferedImage jugador;
	static BufferedImage tierra;
	static BufferedImage estrella;
	/*
	 * Data sobre el posicion y la velocidad horizontal
	 */
	static float[] jugador_coord = new float[]{0, 0};
	static float[] jugador_multiplicador = new float[]{0, 0};
	int[] pantalla_coord = new int[]{0,0};
	int pantalla_multiplicador = 0;
	
	static int frame_width;
	static int frame_height;
	
	static float tiempo = 0;
	static float tiempo_initial = -1;
	static float[] jugador_coord_initial = new float[]{-1, -1};
	float[] jugador_coord_prev = new float[]{-1, -1};
	boolean first_after_death = true;
	
	/*
	 * Velocidades
	 */
	float gravedad = 9.8f;
	static float velocidad_salto = 0;
	static float velocidad_salto_x = 40;
	static float velocidad_andar_x = 0;
	static float velocidad_salto_andar = 0;
	static float velocidad_andar = velocidad_andar_x;
	
	static float jugador_anchura = -1;
	
	/*
	 * Contadores
	 */
	static int muertes = 0;
	
	private void moveBall(){
		if(first_after_death){
			jugador_coord[0] = 0;
			int jugador_altura = 204;
			if(jugador != null){
				jugador_altura = jugador.getHeight();
			}
			jugador_coord[1] = percent(Pista.obtenerPista(0, 0)[1], false) - jugador_altura;
			velocidad_andar = velocidad_andar_x;
			first_after_death = false;
		}else{
			if(jugador != null && jugador_coord[1] >= frame_height && jugador != null){
				//El jugador ha muerto
				muertes++;
				first_after_death = true;
				pararSalto();
				jugador_coord[1] = percent(Pista.obtenerPista(0, 0)[1], false) - jugador.getHeight();
				jugador_coord[0] = 0;
				pantalla_coord[0] = 0;
			}
			if(jugador_anchura == -1){
				jugador_anchura = percent(jugador.getWidth(), true, true);
			}
			int[] pista = Pista.obtenerPista(percent(jugador_coord[0] + pantalla_coord[0], true, true), 
					jugador_anchura, 
					percent(jugador_coord[1] + jugador.getHeight(), false, true));
			System.out.println("PISTA " + percent(jugador_coord[0] - jugador.getWidth(), true, true) + " - " + 
					percent(jugador_anchura, true, true) + " - " + 
					percent(jugador_coord[1] + jugador.getHeight(), false, true));
			if(tiempo_initial != -1){
				//y = y_init + v_init*t + t*t*a
				/*
				 * Calcular la caida, y ve si la caida ha terminado
				 */
				//La formula de la caida
				jugador_coord[1] = (float) (jugador_coord_initial[1] - 
						velocidad_salto * 3 * (tiempo - tiempo_initial) + 
						(tiempo - tiempo_initial) * (tiempo - tiempo_initial) * gravedad * 3);
				if(pista.length > 1){
					if(percent(jugador_coord_prev[1] + jugador.getHeight(), false, true) <= pista[1] && 
							percent(jugador_coord[1] + jugador.getHeight(), false, true) >= pista[1] &&
							jugador_coord[1] != jugador_coord_prev[1]){
						//Para
						pararSalto();
						System.out.println("Para porfi");
						jugador_coord[1] = Math.round(Math.round(percent(pista[1], false)) - Math.round(jugador.getHeight()));
					}else{
						
					}
				}else{
					System.out.println("No hay pista " + pista.length);
					System.out.println(percent(jugador_coord[0] - jugador.getWidth(), true, true) + " - " + 
					percent(jugador_anchura, true, true) + " - " + 
					percent(jugador_coord[1] + jugador.getHeight(), false, true));
					//empezarSalto(true);
				}
				
			}else{
				/*
				 * Empieza caida si no esta justo arriba del suelo
				 */
				if(pista.length > 1 && jugador_coord[1] + jugador.getHeight() == Math.round(percent(pista[1], false))){
					
				}else{
					empezarSalto(true);
				}
			}
			if(pantalla_coord[0] < 0){
				pantalla_coord[0] = 0;
			}
			if(jugador_coord[0] + (jugador.getWidth() / 2) < percent(50, true) || 
					(pantalla_coord[0] == 0 && jugador_multiplicador[0] < 0)){
				jugador_coord[0] = jugador_coord[0] + jugador_multiplicador[0];
				//Arreglar errores
				if(jugador_coord[0] < 0){
					jugador_coord[0] = 0;
				}
			}else{
				pantalla_coord[0] += jugador_multiplicador[0];
			}
			
			jugador_coord_prev[1] = jugador_coord[1];
			jugador_coord_prev[0] = jugador_coord[0];
		}
	}
	
	private void pararSalto(){
		tiempo_initial = -1;
		velocidad_andar = velocidad_andar_x;
		if(jugador_multiplicador[0] > 0){
			jugador_multiplicador[0] = velocidad_andar;
		}else if(jugador_multiplicador[0] < 0){
			jugador_multiplicador[0] = -velocidad_andar;
		}
	}
	private static void empezarSalto(boolean fall){
		tiempo_initial = tiempo;
		jugador_coord_initial[1] = jugador_coord[1];
		velocidad_salto = velocidad_salto_x;
		if(fall){
			velocidad_salto = 0;
		}
		//velocidad_salto = vel_salto;
		velocidad_andar = velocidad_salto_andar;
		if(jugador_multiplicador[0] > 0){
			jugador_multiplicador[0] = velocidad_andar;
		}else if(jugador_multiplicador[0] < 0){
			jugador_multiplicador[0] = -velocidad_andar;
		}
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		try {
			jugador = ImageIO.read(getClass().getResource("/michi.png"));
			tierra = ImageIO.read(getClass().getResourceAsStream("/supermario_ground.jpg"));
			estrella = ImageIO.read(getClass().getResource("/michi.png"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		AffineTransform jugador_at = new AffineTransform();
        jugador_at.translate(jugador_coord[0], jugador_coord[1]);
        //jugador_at.concatenate(AffineTransform.getScaleInstance(jugador_rotation, 1));
        
        int[] rectas = Pista.obtenerPista(percent(pantalla_coord[0], true, true), percent(frame_width, true, true));
        for(int i = 0; i < rectas.length; i = i + 4){
        	int height = frame_height - Math.round(percent(rectas[i+3], false));
        	int width = Math.round(percent(rectas[i+2], true)) - Math.round(percent(rectas[i+0], true));
        	if(height != 0 && width != 0){
	        	Image ground = tierra.getScaledInstance(width, height, 0);
	        	AffineTransform ground_at = new AffineTransform();
	        	ground_at.translate(Math.round(percent(rectas[i+0], true)), 
	        			Math.round(percent(rectas[i+1], false)));
	        	g2d.drawImage(ground, ground_at, null);
        	}
        }
        //Contadores
        g2d.setColor(Color.WHITE);
        g2d.fillRect(5, 5, 200, 100);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(5, 5, 200, 100);
        g2d.drawString("Muertes: " + muertes, 20, 30);
        //Al final para no aparecer en el fondo
        g2d.drawImage(jugador, jugador_at, null);
        g2d.drawOval(Math.round(jugador_coord[0]) - 5, Math.round(jugador_coord[1]) - 5, 10, 10);
	}
	
	public static void main(String[] args) throws InterruptedException, LineUnavailableException{
		JFrame frame = new JFrame("JUEGO 1");
		Main game = new Main();
		frame.add(game);
		frame.setSize(1000, 600);
		//Start in fullscreen
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(new KeyListener() {
	        @Override
	        public void keyTyped(KeyEvent e) {
	        	
	        }

	        @Override
	        public void keyPressed(KeyEvent e) {
	        	/*
	             * W = 81
	             * E = 69
	             * R = 82
	             * Space = 32
	             * Flecha izquierda = 37
	             * Flecha derecha = 39
	             * Flecha arriba = 38
	             * Flecha abajo = 40
	             */
	        	if(e.getKeyCode() == 39){
	        		jugador_multiplicador[0] = velocidad_andar;
	        	}else if(e.getKeyCode() == 37){
	        		jugador_multiplicador[0] = -velocidad_andar;
	        	}else if(e.getKeyCode() == 32){
	        		//Init salto
	        		if(tiempo_initial == -1){
	        			empezarSalto(false);
	        		}
	        	}
	        }

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == 39){
	        		if(jugador_multiplicador[0] > 0){
	        			jugador_multiplicador[0] = 0;
	        		}
	        	}else if(e.getKeyCode() == 37){
	        		if(jugador_multiplicador[0] < 0){
	        			jugador_multiplicador[0] = 0;
	        		}
	        	}
			}
        });
		
		while(true){
			frame_height = frame.getContentPane().getHeight();
			frame_width = frame.getContentPane().getWidth();
			velocidad_andar_x = percent(0.3f, true);
			velocidad_salto_andar = velocidad_andar_x / 2;
			if(jugador != null){
				game.moveBall();
				tiempo += 0.02;
				Thread.sleep(10);
			}
			game.repaint();
		}
	}
	
	private static float percent(float percent, boolean horizontal){
		return percent(percent, horizontal, false);
	}
	private static float percent(float percent, boolean horizontal, boolean reverse){
		if(!reverse){
			/*
			 * Return the pixel-value of a percent value
			 */
			if(horizontal){
				return (float) (((float) percent / 100) * frame_width);
			}else{
				return (float) (((float) percent / 100) * frame_height);
			}
		}else{
			/*
			 * Return the percent value of a pixel-value
			 */
			float pixels = percent;
			if(horizontal){
				return (((float) pixels / frame_width) * 100);
			}else{
				return (((float) pixels / frame_height) * 100);
			}
		}
	}
}
