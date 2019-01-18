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

	private static final int buttonWidth = 100;
	private static final int buttonHeight = 30;

	MainWindow(String title, int width, int height, int bomNum){
		setTitle(title);

		this.width = width;
		this.height = height;
		this.bomNum = bomNum;
		this.windowWidth = (width + 2) * MainPanel.BOX_SIZE;
		this.windowHeight = 50 + (height + 2) * MainPanel.BOX_SIZE;

		setSize(windowWidth, windowHeight);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		content = getContentPane();
		mainContent = new MainPanel(width, height, bomNum, 0, buttonHeight);

		JButton button1010 = new JButton("10×10");
		button1010.setBounds(0 ,0 ,buttonWidth ,buttonHeight);
		button1010.addActionListener(e -> {
			System.out.println("Changed 1010");
			this.width = 10;
			this.height = 10;
			this.bomNum = 10;
			this.windowWidth = (this.width + 2) * MainPanel.BOX_SIZE;
			this.windowHeight = 50 + (this.height + 2) * MainPanel.BOX_SIZE;

			setSize(windowWidth, windowHeight);
			content.remove(mainContent);
			mainContent  = new MainPanel(this.width, this.height, this.bomNum, 0, buttonHeight);
			content.add(mainContent);
		});
		JButton button2030 = new JButton("20×30");
		button2030.setBounds(buttonWidth, 0, buttonWidth, buttonHeight);
		button2030.addActionListener(e -> {
			System.out.println("Changed 2030");
			this.width = 30;
			this.height = 20;
			this.bomNum = 60;
			this.windowWidth = (this.width + 2) * MainPanel.BOX_SIZE;
			this.windowHeight = 50 + (this.height + 2) * MainPanel.BOX_SIZE;

			setSize(windowWidth, windowHeight);
			content.remove(mainContent);
			mainContent  = new MainPanel(this.width, this.height, this.bomNum, 0, buttonHeight);
			content.add(mainContent);
		});
		JButton button4060 = new JButton("40×60");
		button4060.setBounds(buttonWidth * 2, 0, buttonWidth, buttonHeight);
		button4060.addActionListener(e -> {
			System.out.println("Changed 4060");
			this.width = 60;
			this.height = 40;
			this.bomNum = 240;
			this.windowWidth = (this.width + 2) * MainPanel.BOX_SIZE;
			this.windowHeight = 50 + (this.height + 2) * MainPanel.BOX_SIZE;

			setSize(windowWidth, windowHeight);
			content.remove(mainContent);
			mainContent  = new MainPanel(this.width, this.height, this.bomNum, 0, buttonHeight);
			content.add(mainContent);
		});

		content.setLayout(null);
		content.add(button1010);
		content.add(button2030);
		content.add(button4060);
		content.add(mainContent);

		setVisible(true);

	}

}
