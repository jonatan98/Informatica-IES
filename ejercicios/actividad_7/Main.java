package actividad_7;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Main extends JPanel {

	static int tiempo = 0;
	static int frame_height;
	int marginTop;
	int marginLeft = 40;
	
	private void move(){
		
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		marginTop = frame_height - frame_height/4;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		//Cambiar el color
		g2d.setColor(Color.BLUE);
		//Outline
		g2d.drawLine(marginLeft, marginTop, marginLeft + 300, marginTop);
		g2d.drawLine(marginLeft, marginTop, marginLeft, marginTop - 300);
		g2d.drawLine(marginLeft + 300, marginTop, marginLeft + 300, marginTop - 300);
		g2d.drawLine(marginLeft, marginTop - 300, marginLeft + 300, marginTop - 300);
		//Horizontales
		g2d.drawLine(marginLeft, marginTop - 250, marginLeft + 250, marginTop - 250);
		g2d.drawLine(marginLeft, marginTop - 200, marginLeft + 200, marginTop - 200);
		g2d.drawLine(marginLeft, marginTop - 150, marginLeft + 150, marginTop - 150);
		g2d.drawLine(marginLeft, marginTop - 100, marginLeft + 100, marginTop - 100);
		g2d.drawLine(marginLeft, marginTop - 50, marginLeft + 50, marginTop - 50);
		//Verticales
		g2d.drawLine(marginLeft + 250, marginTop, marginLeft + 250, marginTop - 250);
		g2d.drawLine(marginLeft + 200, marginTop, marginLeft + 200, marginTop - 200);
		g2d.drawLine(marginLeft + 150, marginTop, marginLeft + 150, marginTop - 150);
		g2d.drawLine(marginLeft + 100, marginTop, marginLeft + 100, marginTop - 100);
		g2d.drawLine(marginLeft + 50, marginTop, marginLeft + 50, marginTop - 50);
	}
	
	public static void main(String[] args) throws InterruptedException, LineUnavailableException{
		JFrame frame = new JFrame("ACTIVIDAD 7");
		Main game = new Main();
		frame.add(game);
		frame.setSize(1000, 600);
		frame.getContentPane().setBackground(Color.WHITE);
		//Start in fullscreen
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		frame_height = frame.getContentPane().getHeight();
		while(true){
			game.repaint();
			game.move();
			Thread.sleep(10);
		}
	}
}
