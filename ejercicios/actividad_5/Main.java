package actividad_5;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Main extends JPanel {

	int x = 0;
	static int move_x = 0;
	static int multipler_x = 1;
	static int frame_height;
	int marginTop;
	BufferedImage michi;
	
	boolean firstrun = true;
	
	private void moveBall(){
		if(firstrun){
			firstrun = false;
			/*
			 * Muestra dialogo
			 */
			String numero_1 = JOptionPane.showInputDialog(null,
                    "Introduzca numero 1", null);
			String numero_2 = JOptionPane.showInputDialog(null,
                    "Introduzca numero 2", null);
			int num1; int num2;
			try{
				num1 = Integer.parseInt(numero_1);
				num2 = Integer.parseInt(numero_2);
			}catch(NumberFormatException e){
				num1 = 0;
				num2 = 0;
			}
			int suma = num1 + num2;
			move_x = suma * 10;
			System.out.println(suma + " - " + move_x);
		}else{
			if(move_x != 0){
				x = x + multipler_x;
				move_x = move_x - multipler_x;
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
        at.translate(x, marginTop);
        g2d.drawImage(michi, at, null);
	}
	
	public static void main(String[] args) throws InterruptedException, LineUnavailableException{
		JFrame frame = new JFrame("ACTIVIDAD 5");
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
			game.moveBall();
			game.repaint();
			Thread.sleep(10);
		}
	}
}
