package ejercicio_5;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Main extends JPanel {

	static int frame_width;
	static int frame_height;
	int marginTop;
	int marginLeft;
	
	/*
	 * 
	 */
	int[] hexagonos = new int[]{20, 40, 60, 80, 100, 120};
	
	private void moveBall(){
		/*
		 * Increase length of lines and change start_positions for lines in hexagons
		 */
		for(int i = 0; i < hexagonos.length; i++){
			hexagonos[i] += 20;
		}
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		marginTop = (frame_height / 2);
		marginLeft = (frame_width / 2);
        /*
         * Paint lines to form 5 hexagons
         */
		for(int i = 0; i < hexagonos.length; i++){
			//pythagoras
			int pyth = (int) Math.round(Math.sqrt(hexagonos[i]* hexagonos[i] - (hexagonos[i]/2)*(hexagonos[i]/2)));
			//Left
			g2d.drawLine(marginLeft, marginTop - hexagonos[i], marginLeft - pyth, marginTop - hexagonos[i]/2);
			g2d.drawLine(marginLeft - pyth, marginTop - hexagonos[i] / 2, marginLeft - pyth, marginTop + hexagonos[i] / 2);
			g2d.drawLine(marginLeft - pyth, marginTop + hexagonos[i] / 2, marginLeft, marginTop + hexagonos[i]);
			//Right
			g2d.drawLine(marginLeft, marginTop - hexagonos[i], marginLeft + pyth, marginTop - hexagonos[i]/2);
			g2d.drawLine(marginLeft + pyth, marginTop - hexagonos[i] / 2, marginLeft + pyth, marginTop + hexagonos[i] / 2);
			g2d.drawLine(marginLeft + pyth, marginTop + hexagonos[i] / 2, marginLeft, marginTop + hexagonos[i]);
		} 
	}
	
	public static void main(String[] args) throws InterruptedException, LineUnavailableException{
		JFrame frame = new JFrame("EJERCICIO 5");
		Main game = new Main();
		frame.add(game);
		frame.setSize(1000, 600);
		//Start in fullscreen
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		while(true){
			frame_height = frame.getContentPane().getHeight();
			frame_width = frame.getContentPane().getWidth();
			game.moveBall();
			game.repaint();
			Thread.sleep(100);
		}
	}
}
