//プロジェクト名：sample1
//パッケージ名：sample1
//クラス名：DrawSample
package sample1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class DrawSample {
	JFrame frame;
	BufferStrategy bstrategy;
	int x=200;
	int y=200;
	boolean spacekey = false;

	DrawSample () {
		frame = new JFrame("MyWindow");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.WHITE);
		frame.setResizable(false);
		frame.setVisible(true);
		Insets insets = frame.getInsets();
		frame.setSize(500+insets.left+insets.right,
				500+insets.top+insets.bottom);
		frame.setLocationRelativeTo(null);
		frame.setIgnoreRepaint(true);
		frame.createBufferStrategy(2);
		bstrategy = frame.getBufferStrategy();

		Timer t = new Timer();
		t.schedule(new MyTimerTask(), 10, 30);

		frame.addKeyListener(new MyKeyAdapter());
	}

	class MyTimerTask extends TimerTask {
		public void run() {
			Graphics g = bstrategy.getDrawGraphics();
			if (bstrategy.contentsLost() == false) {
				Insets insets = frame.getInsets();
				g.translate(insets.left, insets.top);
				if (spacekey == true) {
					y = y-1;
				} else {
					y = y+1;
				}
				g.clearRect(0,0,500,500);
				g.setColor(Color.BLUE);
				g.fillOval(x,y,100,100);
				bstrategy.show();
				g.dispose();
			}
		}
	}

	class MyKeyAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				spacekey=true;
			}
		}
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				spacekey=false;
			}
		}
	}
}