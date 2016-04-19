package actividad_10;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Animation2 extends JPanel {

	static int frame_height;
	static int frame_width;
	/*
	 * Haz una linea que cruza la pantalla, y calcula el proximo angulo
	 */
	
	private void move(){
		
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		
	}
	
	public static void main(String[] args) throws InterruptedException, LineUnavailableException{
		JFrame frame = new JFrame("ACTIVIDAD 10");
		Animation2 game = new Animation2();
		frame.add(game);
		frame.setSize(1000, 600);
		//Start in fullscreen
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		while(true){
			frame_height = frame.getContentPane().getHeight();
			frame_width = frame.getContentPane().getWidth();
			game.move();
			game.repaint();
			Thread.sleep(10);
		}
	}
}
