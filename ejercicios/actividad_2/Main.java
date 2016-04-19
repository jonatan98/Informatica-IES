package actividad_2;

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
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Main extends JPanel {

	int y = 0;
	static int frame_height;
	int marginTop;
	
	//El numero que "add" a el x y y cada 10ms
	int multipler = 1;
	
	BufferedImage pelota;
	
	static int count = 0;
	
	private void moveBall(){
		if(y == 200){
			multipler = -2;
		}else if(y <= 0 && multipler == -2){
			multipler = 1;
			count++;
		}
		y = y + multipler;
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		try {
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
		marginTop = frame_height - frame_height/4 - pelota.getHeight();
		AffineTransform at = new AffineTransform();
        at.translate(300, marginTop - y);
        g2d.drawImage(pelota, at, null);
	}
	
	public static void main(String[] args) throws InterruptedException, LineUnavailableException{
		JFrame frame = new JFrame("ACTIVIDAD 2");
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
			if(count == 3){
				break;
			}
		}
	}
}
