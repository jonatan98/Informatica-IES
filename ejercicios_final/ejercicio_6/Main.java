package ejercicio_6;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Main extends JPanel {

	int michi_x_origen = 0;
	int michi_x = michi_x_origen;
	int michi_y_origen = 300;
	int michi_y = michi_y_origen;
	int[] pelota_coord = new int[]{300, 0};
	int pelota_multipler_y = 2;
	int pelota_multipler_x = 3;
	static int multipler_michi_x = 0;
	static int multipler_michi_y = 0;
	static int frame_height;
	static int frame_width;
	BufferedImage michi;
	BufferedImage pelota;
	
	boolean won = false;
	
	private void moveBall(){
		//Pelotas
		if(pelota_coord[1] <= 0){
			if(pelota_multipler_y < 0){
				pelota_multipler_y *= -1;
			}
		}else if(pelota != null && pelota_coord[1] >= frame_height - pelota.getHeight()){
			if(pelota_multipler_y > 0){
				pelota_multipler_y *= -1;
			}
		}
		pelota_coord[1] += pelota_multipler_y;
		if(pelota_coord[0] <= 0){
			if(pelota_multipler_x < 0){
				pelota_multipler_x *= -1;
			}
		}else if(pelota != null && pelota_coord[0] >= frame_width - pelota.getHeight()){
			if(pelota_multipler_x > 0){
				pelota_multipler_x *= -1;
			}
		}
		pelota_coord[0] += pelota_multipler_x;
		//Calcula tocas
		if(michi != null){
			if(pelota_coord[0] < michi_x + michi.getWidth() && michi_x < pelota_coord[0] + pelota.getWidth()){
				if(pelota_coord[1] < michi_y + michi.getHeight() && michi_y < pelota_coord[1] + pelota.getHeight()){
					//Ha ganado
					boolean won = true;
				}
			}
		}
		//Bloquear movimiento de michi afuera de la pantalla
		if(multipler_michi_x > 0){
			if(michi != null && michi_x >= frame_width - michi.getWidth()){
				//Ha llegado al otro lado de la pantalla
				
			}
		}else{
			if(michi != null && michi_x <= 0){
				michi_x = 0;
				multipler_michi_x = 0;
			}
		}
		if(multipler_michi_y > 0){
			if(michi != null && michi_y >= frame_height - michi.getHeight()){
				multipler_michi_y = 0;
				michi_y = frame_height - michi.getHeight();
			}
		}else{
			if(michi != null && michi_y <= 0){
				multipler_michi_y = 0;
				michi_y = 0;
			}
		}
		michi_x = michi_x + multipler_michi_x;
		michi_y = michi_y + multipler_michi_y;
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		try {
			michi = ImageIO.read(getClass().getResource("/michi.png"));
			pelota = ImageIO.read(getClass().getResource("/pelota.png"));
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
		AffineTransform michi_at = new AffineTransform();
        michi_at.translate(michi_x, michi_y);
        g2d.drawImage(michi, michi_at, null);
        //Pelota
    	AffineTransform pelota_at = new AffineTransform();
        pelota_at.translate(pelota_coord[0], pelota_coord[1]);
        g2d.drawImage(pelota, pelota_at, null);
	}
	
	public static void main(String[] args) throws InterruptedException, LineUnavailableException{
		JFrame frame = new JFrame("ACTIVIDAD 9");
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
	        		multipler_michi_x = 3;
	        	}else if(e.getKeyCode() == 37){
	        		multipler_michi_x = -3;
	        	}else if(e.getKeyCode() == 38){
        			multipler_michi_y = -3;
	        	}else if(e.getKeyCode() == 40){
	        		multipler_michi_y = 3;
	        	}
	        }

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == 39 || e.getKeyCode() == 37){
	        		multipler_michi_x = 0;
	        	}else if(e.getKeyCode() == 38 || e.getKeyCode() == 40){
	        		multipler_michi_y = 0;
	        	}
			}
        });
		
		while(true){
			frame_height = frame.getContentPane().getHeight();
			frame_width = frame.getContentPane().getWidth();
			game.moveBall();
			game.repaint();
			Thread.sleep(10);
		}
	}
}
