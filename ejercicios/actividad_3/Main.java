package actividad_3;

import java.awt.Color;
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
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Main extends JPanel {

	int y = 0;
	static int move_y = 0;
	static int multipler_y = 1;
	int x = 0;
	static int move_x = 0;
	static int multipler_x = 1;
	static int frame_height;
	int marginTop;
	BufferedImage michi;
	
	static boolean stopped = false;
	
	private void moveBall(){
		if(!stopped){
			if(move_x != 0){
				x = x + multipler_x;
				move_x = move_x - multipler_x;
			}
			if(move_y != 0){
				y = y + multipler_y;
				move_y = move_y - multipler_y;
			}
		}
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		try {
			michi = ImageIO.read(getClass().getResource("/michi.png"));
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
		marginTop = frame_height - frame_height/4 - michi.getHeight();
		AffineTransform at = new AffineTransform();
        at.translate(x, marginTop - y);
        g2d.drawImage(michi, at, null);
	}
	
	public static void main(String[] args) throws InterruptedException, LineUnavailableException{
		JFrame frame = new JFrame("ACTIVIDAD 3");
		Main game = new Main();
		frame.add(game);
		frame.setSize(1000, 600);
		frame.getContentPane().setBackground(Color.WHITE);
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
	        		move_x = 20;
	        		multipler_x = 1;
	        	}else if(e.getKeyCode() == 37){
	        		move_x = -20;
	        		multipler_x = -1;
	        	}else if(e.getKeyCode() == 38){
	        		move_y = 20;
	        		multipler_y = 1;
	        	}else if(e.getKeyCode() == 40){
	        		move_y = -20;
	        		multipler_y = -1;
	        	}else if(e.getKeyCode() == 32){
	        		if(stopped){
	        			stopped = false;
	        		}else{
	        			stopped = true;
	        		}
	        	}
	        	
	        }

	        @Override
	        public void keyReleased(KeyEvent e) {
	        }
	    });
		
		frame_height = frame.getContentPane().getHeight();
		while(true){
			game.moveBall();
			game.repaint();
			Thread.sleep(10);
		}
	}
}
