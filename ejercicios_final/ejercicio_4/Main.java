package ejercicio_4;

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
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Main extends JPanel {

	int x = 20;
	static int frame_height;
	int marginTop;
	BufferedImage michi;
	
	private void moveBall(){
		/*
		 * Muestra dialogo
		 */
		String numero = JOptionPane.showInputDialog(null,
                "¿Haste que número quieres que cuente?", null);
		int num1;
		try{
			num1 = Integer.parseInt(numero);
		}catch(NumberFormatException e){
			num1 = 0;
		}

		for(int i = 1; i <= num1; i++){
			final JComponent[] inputs = new JComponent[] {
					new JLabel(i + "")
			};
			JOptionPane.showMessageDialog(null, inputs, "Michi", JOptionPane.PLAIN_MESSAGE);
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
		JFrame frame = new JFrame("EJERCICIO 4");
		Main game = new Main();
		frame.add(game);
		frame.setSize(1000, 600);
		frame.getContentPane().setBackground(Color.WHITE);
		//Start in fullscreen
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		frame_height = frame.getContentPane().getHeight();
		game.moveBall();
		game.repaint();
	}
}
