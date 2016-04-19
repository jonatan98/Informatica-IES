package actividad_1;

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
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Main extends JPanel {

	int x = 0;
	int y = 0;
	static int frame_height;
	int marginTop;
	
	//El numero que "add" a el x y y cada 10ms
	int multipler = 1;
	double multipler_rotation = 0;
	double rotation = 1;
	static boolean pause = false;
	
	BufferedImage avion;
	
	static int view_margins = 0;
	
	//Musica
	static Clip clip;
	
	private void moveBall(){
		if(!pause){
			x = x + multipler;
			rotation = rotation - multipler_rotation;
			
			/*
			 * Y axis
			 */
			if(x == 400){
				multipler = -1;
			}else if(x >= 200){
				y = y + multipler;
			}else if(x == 0){
				multipler = 1;
			}
			
			/*
			 * Rotation
			 */
			if(x == 300){
				if(multipler > 0){
					multipler_rotation = 0.02;
				}
			}else if(x == 400){
				multipler_rotation = 0;
			}else if(x == 100){
				if(multipler < 0){
					multipler_rotation = - 0.02;
				}
			}else if(x == 0){
				multipler_rotation = 0;
			}
			
			/*
			 * Musica
			 */
			if(x == 200){
				actualizaSonido(multipler);
			}
		}
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		try {
			avion = ImageIO.read(getClass().getResource("/ejercicio1/avion.jpg"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		marginTop = frame_height - frame_height/4 - avion.getHeight();
		
		//Animarlo
		AffineTransform at = new AffineTransform();
        at.translate(x + avion.getWidth(), marginTop - y);
		at.concatenate(AffineTransform.getScaleInstance(rotation, 1));
        
		if(view_margins == 1){
			//Start position vertical
			g2d.drawLine(avion.getWidth(), 0, avion.getWidth(), frame_height);
			//Start position horizontal
			g2d.drawLine(avion.getWidth(), marginTop + avion.getHeight(), avion.getWidth() + 400, marginTop + avion.getHeight());
			//End position vertical
			g2d.drawLine(avion.getWidth() + 400, 0, avion.getWidth() + 400, frame_height);
			//End position horizontal
			g2d.drawLine(avion.getWidth(), marginTop - 200, avion.getWidth() + 400, marginTop - 200);
		}else if(view_margins == 2){
			int line_width = 30;
			//Bottom left corner
			g2d.drawLine(avion.getWidth(), marginTop + avion.getHeight() - line_width, avion.getWidth(), marginTop + avion.getHeight());
			g2d.drawLine(avion.getWidth(), marginTop + avion.getHeight(), avion.getWidth() + line_width, marginTop + avion.getHeight());
			//Top right corner
			g2d.drawLine(avion.getWidth() + 400, marginTop - 200 + line_width, avion.getWidth() + 400, marginTop - 200);
			g2d.drawLine(avion.getWidth() + 400, marginTop - 200, avion.getWidth() + 400 - line_width, marginTop - 200);
		}
		
        g2d.drawImage(avion, at, null);
	}
	
	public static void main(String[] args) throws InterruptedException, LineUnavailableException{
		JFrame frame = new JFrame("ACTIVIDAD 1");
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
	             */
	        	if(e.getKeyCode() == 82){
	        		view_margins = 0;
	        	}else if(e.getKeyCode() == 87){
	        		view_margins = 1;
	        	}else if(e.getKeyCode() == 69){
	        		view_margins = 2;
	        	}else if(e.getKeyCode() == 32){
	        		if(pause){
	        			pause = false;
	        			clip.start();
	        		}else{
	        			pause = true;
	        			clip.stop();
	        		}
	        	}
	        }

	        @Override
	        public void keyReleased(KeyEvent e) {
	        }
	    });
		
		final JComponent[] inputs = new JComponent[] {
				new JLabel("W = First margin mode"),
				new JLabel("E = Second margin mode"),
				new JLabel("R = Turn off margins"),
				new JLabel("Space = Pause")
		};
		JOptionPane.showMessageDialog(null, inputs, "Keyboard shortcuts", JOptionPane.PLAIN_MESSAGE);
		
		/*
		 * Init musica
		 */
		clip = AudioSystem.getClip();
		while(true){
			frame_height = frame.getContentPane().getHeight();
			game.moveBall();
			game.repaint();
			Thread.sleep(10);
		}
	}
	
	private void actualizaSonido(int multipler){
		if(!clip.isRunning()){
			clip.close();
			try {
				AudioInputStream inputStream;
		        if(multipler > 0){
		        	//Sonida de un avion
		        	inputStream = AudioSystem.getAudioInputStream(
		        			Main.class.getResourceAsStream("/ejercicio1/avion_despegar.wav"));
		        }else{
		        	//Sonida de ruedas
		        	inputStream = AudioSystem.getAudioInputStream(
		        			Main.class.getResourceAsStream("/ejercicio1/avion_despegar.wav"));
		        }
		        clip.open(inputStream);
		        clip.start();
		      }catch (Exception e){
		    	  //System.err.println(e.getMessage());
		    	  e.printStackTrace();
		      }
		}
	}
}
