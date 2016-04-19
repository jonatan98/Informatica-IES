package actividad_4;

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

	int y = 0;
	static int move_y = 0;
	static int multipler_y = 1;
	int x = 0;
	static int move_x = 0;
	static int multipler_x = 1;
	static int frame_height;
	int marginTop;
	BufferedImage michi;
	
	boolean stopped = false;
	
	private void moveBall(){
		if(x == 100){
			if(!stopped){
				/*
				 * Muestra dialogo
				 */
				final JComponent[] inputs = new JComponent[] {
						new JLabel("Hola, me llamo Michi"),
						new JLabel("Tengo 2 años y soy de España.")
				};
				JOptionPane.showMessageDialog(null, inputs, "Hola", JOptionPane.PLAIN_MESSAGE);
				String nombre = JOptionPane.showInputDialog(null,
                        "Como te llamas?", null);
				final JComponent[] inputs2 = new JComponent[] {
						new JLabel("Hola " + nombre)
				};
				JOptionPane.showMessageDialog(null, inputs2, "Saludo", JOptionPane.PLAIN_MESSAGE);
				String edad = JOptionPane.showInputDialog(null,
                        "Cuantos años tienes?", null);
				JOptionPane.showMessageDialog(null, new JComponent[]{
						new JLabel("¡" + edad + " años! ¡Que major!")
				}, "Que major!", JOptionPane.PLAIN_MESSAGE);
				stopped = true;
			}
		}else{
			x++;
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
		JFrame frame = new JFrame("ACTIVIDAD 4");
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
