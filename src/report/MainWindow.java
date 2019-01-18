package report;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainWindow extends JFrame{

	private Container content;
	private MainPanel mainContent;

	private int width;
	private int height;
	private int bomNum;

	private int windowWidth;
	private int windowHeight;

	private int buttonNum;
	public static final int BUTTON_WIDTH = 100;
	public static final int BUTTON_HEIGHT = 30;

	public static final int MARGIN_WIDTH = 8;
	public static final int MARGIN_HEIGHT = 30;

	private JButton[] button;

	MainWindow(String title){

		// ウィンドウの設定
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		content = getContentPane();
		content.setLayout(null);

		// ボタン生成
		buttonNum = 3;
		button = new JButton[buttonNum];

		// 各ボタンの設定(名称及び押下時の動作)
		button[0] = new JButton("10×10");
		button[0].addActionListener(e -> {
			System.out.println("Changed 1010");
			content.remove(mainContent);
			makeMainPanel(10, 10, 10, 0, BUTTON_HEIGHT);
		});
		button[1] = new JButton("20×30");
		button[1].addActionListener(e -> {
			System.out.println("Changed 2030");
			content.remove(mainContent);
			makeMainPanel(30, 20, 60, 0, BUTTON_HEIGHT);
		});
		button[2] = new JButton("40×60");
		button[2].addActionListener(e -> {
			System.out.println("Changed 4060");
			content.remove(mainContent);
			makeMainPanel(60, 40, 240, 0, BUTTON_HEIGHT);
		});

		// ボタンの配置
		for(int i = 0; i < buttonNum; i++) {
			button[i].setBounds(BUTTON_WIDTH * i, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
			content.add(button[i]);
		}

		makeMainPanel(10, 10, 10, 0, BUTTON_HEIGHT);

	}

	private void makeMainPanel(int width, int height, int bomNum, int left, int top) {
		this.width = width;
		this.height = height;
		this.bomNum = bomNum;
		this.windowWidth = Math.max(BUTTON_WIDTH * buttonNum, width * MainPanel.BOX_SIZE + MARGIN_WIDTH);
		this.windowHeight = BUTTON_HEIGHT + height * MainPanel.BOX_SIZE + MARGIN_HEIGHT + Status.S_HEIGHT;

		setSize(windowWidth, windowHeight);
		mainContent  = new MainPanel(this.width, this.height, this.bomNum, left, top, windowWidth, windowHeight);
		content.add(mainContent);
	}

}
