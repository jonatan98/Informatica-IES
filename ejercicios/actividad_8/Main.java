package actividad_8;

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

	int michi_x = 0;
	int pelota_x = 500;
	static int move_x = 0;
	static int multipler_michi_x = 1;
	static int multipler_pelota_x = 0;
	static int frame_height;
	int marginTop;
	BufferedImage michi;
	BufferedImage pelota = null;
	
	private void moveBall(){
		if(pelota != null){
			if(michi_x + michi.getWidth() >= pelota_x){
				multipler_michi_x = 0;
				multipler_pelota_x = 1;
			}
		}
		
		michi_x = michi_x + multipler_michi_x;
		pelota_x = pelota_x + multipler_pelota_x;
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
		marginTop = frame_height - frame_height/4 - michi.getHeight();
		AffineTransform michi_at = new AffineTransform();
        michi_at.translate(michi_x, marginTop);
        g2d.drawImage(michi, michi_at, null);
        //Pelota
        AffineTransform pelota_at = new AffineTransform();
        pelota_at.translate(pelota_x, marginTop);
        g2d.drawImage(pelota, pelota_at, null);
	}
	
	public static void main(String[] args) throws InterruptedException, LineUnavailableException{
		JFrame frame = new JFrame("ACTIVIDAD 8");
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
