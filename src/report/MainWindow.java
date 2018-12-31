package report;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

public class MainWindow extends JFrame{

	private Container Content;

	private MyHeader header;
	private MainPanel mainContent;

	MainWindow(String title, int width, int height){
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Content = getContentPane();
		header = new MyHeader();
		mainContent = new MainPanel(10, 10, 10);

		Content.add(header, BorderLayout.NORTH);
		Content.add(mainContent);

		setVisible(true);


	}
}
