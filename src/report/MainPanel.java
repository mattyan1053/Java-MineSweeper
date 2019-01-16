package report;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class MainPanel extends JPanel implements Runnable{

	private Sweeper ms;
	private MyMouseAdapter ml;
	private Thread t;

	private int width;
	private int height;
	private int bomNum;

	private final int boxSize = 30;

	MainPanel(int width, int height, int bomNum){
		super();
		this.width = width;
		this.height = height;
		this.bomNum = bomNum;
		ms = new Sweeper(this.width, this.height, this.bomNum);
		ml = new MyMouseAdapter();
		this.addMouseListener(ml);

		t = new Thread(this);
		t.start();

	}

	public void run() {
		while(true) {
			repaint();
			if(ms.checkFinish() != 0) break;
		}
	}

	public void paintComponent(Graphics g) {
		for(int i=1; i<=height; i++) {
			for(int j=1; j<=width; j++) {
				Square s = ms.getSquare(j , i);
				if(s.isUserFlaged()){
					g.setColor(Color.RED);
					g.fillRect(j * boxSize, i * boxSize, boxSize, boxSize);
				}
				else if(!s.isOpen()) {
					g.setColor(Color.gray);
					g.fillRect(j * boxSize, i * boxSize, boxSize, boxSize);
				}else {
					g.setColor(Color.DARK_GRAY);
					g.fillRect(j * boxSize, i * boxSize, boxSize, boxSize);
					if(s.isBomFlag()) {
						g.setColor(Color.BLACK);
						g.drawString("●", j * boxSize + boxSize / 3,  i * boxSize + boxSize / 2);
					}
					else if(s.getBomNum() != 0) {
						g.setColor(Color.WHITE);
						g.drawString(String.valueOf(s.getBomNum()), j * boxSize + boxSize / 3,  i * boxSize + boxSize / 2);
					}
				}
			}
			int isEnd = ms.checkFinish();
			if(isEnd == 1) {
				g.setColor(Color.BLACK);
				g.drawString("Game Clear!", boxSize * width, boxSize * (height + 2));
			}
			else if(isEnd == 2) {
				g.setColor(Color.BLACK);
				g.drawString("Game Over!", boxSize * 2, boxSize * (height + 2));
			}
		}
		for(int i=1; i<=height; i++) {
			for(int j=1; j<=width; j++) {
				g.setColor(Color.BLACK);
				g.drawLine(j * boxSize, i * boxSize, j * boxSize, (i + 1) * boxSize);
				g.drawLine(j * boxSize, i * boxSize, (j + 1) * boxSize, i * boxSize);
			}
		}
	}

	public class MyMouseAdapter extends MouseAdapter{

		public static final int LEFT_ON = 1;
		public static final int RIGHT_ON = 2;
		public static final int BOTH_ON = 3;
		public static final int NO_CHANGE = 0;

		private int mouseState = 0;

		private int mx;
		private int my;

		MyMouseAdapter(){
			mouseState = NO_CHANGE;

			mx = 0;
			my = 0;
		}

		public void mousePressed(MouseEvent e) {
			System.out.println("Button Pressed");
			int btn = e.getButton();
			if(btn == MouseEvent.BUTTON1) {
				if(mouseState == RIGHT_ON) {
					mouseState = BOTH_ON;
				}else {
					mouseState = LEFT_ON;
				}
			}
			else if(btn == MouseEvent.BUTTON3) {
				if(mouseState == LEFT_ON) {
					mouseState = BOTH_ON;
				}else {
					mouseState = RIGHT_ON;
				}
			}
			return;
		}

		public void mouseReleased(MouseEvent e) {
			System.out.println("Button Released");
			mx = e.getX();
			my = e.getY();
			if(mx < boxSize || mx > boxSize * (width + 1)) {
				System.out.println("Over X");
				return;
			}
			if(my < boxSize || my > boxSize * (height + 1)) {
				System.out.println("Over Y");
				return;
			}
			int btn = e.getButton();
			if(mouseState == BOTH_ON) {
				System.out.println("both on");
				ms.openFull(mx / boxSize,  my / boxSize);
				mouseState = NO_CHANGE;
			}
			else if(mouseState == LEFT_ON) {
				System.out.println("pushed button1");
				ms.open(mx / boxSize, my / boxSize);
				mouseState = NO_CHANGE;
			}
			else if(mouseState == RIGHT_ON) {
				System.out.println("pushed button3!");
				ms.setFlag(mx / boxSize, my / boxSize);
				mouseState = NO_CHANGE;
			}
			return;
		}

		public int getMouseX() {
			return mx;
		}

		public int getMouseY() {
			return my;
		}

	}

}
