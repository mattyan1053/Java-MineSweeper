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

	private Status st;

	private int width;
	private int height;
	private int bomNum;

	private boolean startFlag;

	public static final int BOX_SIZE = 20;

	MainPanel(int width, int height, int bomNum, int left, int top, int windowWidth, int windowHeight){
		super();
		this.startFlag = false;
		this.width = width;
		this.height = height;
		this.bomNum = bomNum;

		ms = new Sweeper(this.width, this.height, this.bomNum);
		ml = new MyMouseAdapter();
		st = new Status(0, this.height * BOX_SIZE, bomNum);

		setLayout(null);
		setBounds(left, top, Math.max(windowWidth, width*BOX_SIZE), this.height*BOX_SIZE + 100);
		add(st);
		this.addMouseListener(ml);

		t = new Thread(this);
		t.start();

	}

	@Override
	public void run() {
		while(true) {
			repaint();
			if(st.setFinishString(ms.checkFinish()) != 0) {
				st.timerStop();
				break;
			}
			st.setUnFlagedNum(ms.getUnFlagedNum());
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i=1; i<=height; i++) {
			for(int j=1; j<=width; j++) {
				Square s = ms.getSquare(j , i);
				if(s.isUserFlaged()){
					g.setColor(Color.RED);
					g.fillRect((j - 1) * BOX_SIZE, (i - 1) * BOX_SIZE, BOX_SIZE, BOX_SIZE);
				}
				else if(!s.isOpen()) {
					g.setColor(Color.gray);
					g.fillRect((j - 1) * BOX_SIZE, (i - 1) * BOX_SIZE, BOX_SIZE, BOX_SIZE);
				}else {
					g.setColor(Color.DARK_GRAY);
					g.fillRect((j - 1) * BOX_SIZE, (i - 1) * BOX_SIZE, BOX_SIZE, BOX_SIZE);
					if(s.isBomFlag()) {
						g.setColor(Color.BLACK);
						g.drawString("●", (j - 1) * BOX_SIZE + BOX_SIZE / 3,  (i - 1) * BOX_SIZE + BOX_SIZE / 2);
					}
					else if(s.getBomNum() != 0) {
						g.setColor(Color.WHITE);
						g.drawString(String.valueOf(s.getBomNum()), (j - 1) * BOX_SIZE + BOX_SIZE / 3,  (i - 1) * BOX_SIZE + BOX_SIZE / 2);
					}
				}
			}
		}
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				g.setColor(Color.BLACK);
				g.drawLine(j * BOX_SIZE, i * BOX_SIZE, j * BOX_SIZE, (i + 1) * BOX_SIZE);
				g.drawLine(j * BOX_SIZE, i * BOX_SIZE, (j + 1) * BOX_SIZE, i * BOX_SIZE);
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

		@Override
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

		@Override
		public void mouseReleased(MouseEvent e) {
			System.out.println("Button Released");
			mx = e.getX();
			my = e.getY();
			// ます番号が1~width、2~heightなので調整
			mx += BOX_SIZE;
			my += BOX_SIZE;

			if(mx < BOX_SIZE || mx > BOX_SIZE * (width + 1)) {
				System.out.println("Over X");
				return;
			}
			if(my < BOX_SIZE || my > BOX_SIZE * (height + 1)) {
				System.out.println("Over Y");
				return;
			}
			if(mouseState == BOTH_ON) {
				System.out.println("both on");
				ms.openFull(mx / BOX_SIZE,  my / BOX_SIZE);
				mouseState = NO_CHANGE;
			}
			else if(mouseState == LEFT_ON) {
				System.out.println("pushed button1");
				if(startFlag == false) {
					startFlag = true;
					ms.initGame(mx / BOX_SIZE, my / BOX_SIZE);
					st.timerStart();
				}
				ms.open(mx / BOX_SIZE, my / BOX_SIZE);
				mouseState = NO_CHANGE;
			}
			else if(mouseState == RIGHT_ON) {
				System.out.println("pushed button3!");
				ms.setFlag(mx / BOX_SIZE, my / BOX_SIZE);
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
