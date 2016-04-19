package actividad_10;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Animation1 extends JPanel {

	static int frame_height;
	static int frame_width;
	int marginTop;
	int marginLeft = 100;
	static int tiempo = 0;
	int[] line_length = new int[]{0, 0, 0, 0, 0, 0};
	String[] colors = new String[]{"FF00FF", "00FFFF", "FFFF00", "FF0000", "0000FF", "00FF00"};
	int velocidad = 10;
	
	int multipler = 1;
	
	private void move(){
		
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		marginTop = frame_height - frame_height/4;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		//Outline
		int max_index = (line_length[0] / 50);
		for(int i = 0; i < line_length.length; i++){
			if(multipler > 0 && (line_length[i] < 50 * i || (i == 0 && line_length[i] < 250)) && i <= max_index){
				line_length[i] = line_length[i] + multipler;
			}
			if((multipler < 0 && (line_length[i] >= line_length[line_length.length - 1] && i != 0) || 
					(i == 0 && line_length[line_length.length - 1] == 0)) && line_length[i] != 0){
				line_length[i] = line_length[i] + multipler;
			}
			g2d.setColor(Color.decode("#" + colors[i]));
			if(line_length[i] != 0){
				if(i != 0){
					marginLeft++;
					marginTop--;
				}
				g2d.drawLine(marginLeft + 50*i, marginTop, marginLeft + 50*i, marginTop - line_length[i]);
				g2d.drawLine(marginLeft, marginTop - 50*i, marginLeft + line_length[i], marginTop - 50*i);
				if(i != 0){
					marginLeft--;
					marginTop++;
				}
			}
			if(line_length[line_length.length - 1] == 250){
				multipler = -1;
			}else if(line_length[0] == 0){
				multipler = 1;
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException, LineUnavailableException{
		JFrame frame = new JFrame("ACTIVIDAD 10");
		Animation1 game = new Animation1();
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
			tiempo += 10;
		}
	}
}
