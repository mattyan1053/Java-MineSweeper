package report;

import java.awt.Container;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

/*
 * アプリケーションのウィンドウを生成するクラス
 * 中にボタンやゲーム本体のパネルなどを配置している
 */
public class MainWindow extends JFrame{

	// フレーム外枠サイズ
	public static final int MARGIN_WIDTH = 8;
	public static final int MARGIN_HEIGHT = 30;

	private Container content;
	private MainPanel mainContent;

	// ボタン情報
	private int buttonNum;
	private JButton[] button;
	public static final int BUTTON_WIDTH = 100;
	public static final int BUTTON_HEIGHT = 30;

	MainWindow(String _title){

		// ウィンドウの設定
		setTitle(_title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		content = getContentPane();
		content.setLayout(null);

		// ボタン生成
		buttonNum = 3;
		button = new JButton[buttonNum];

		// 各ボタンの設定(名称及び押下時の動作)
		/*
		 * ボタンインスタンスの生成とアクションリスナーの設定が必要
		 * テンプレート
		 * button[i] = new JButton("テキスト");
		 * button[i].addActionListener(e -> {
		 *		content.remove(mainContent);
		 *		makeMainPanel(widthNum, heightNum, bomNum, left, top);
		 * });
		 */
		button[0] = new JButton("10×10");
		button[0].addActionListener(e -> {
			content.remove(mainContent);
			makeMainPanel(10, 10, 10, 0, BUTTON_HEIGHT);
		});
		button[1] = new JButton("20×30");
		button[1].addActionListener(e -> {
			content.remove(mainContent);
			makeMainPanel(30, 20, 60, 0, BUTTON_HEIGHT);
		});
		button[2] = new JButton("40×60");
		button[2].addActionListener(e -> {
			content.remove(mainContent);
			makeMainPanel(60, 40, 240, 0, BUTTON_HEIGHT);
		});

		// ボタンの配置
		for(int i = 0; i < buttonNum; i++) {
			// フォーカスが常にいずれかのボタンにあるため、キーリスナーはボタンに登録
			button[i].addKeyListener(new MyKeyAdapter());

			button[i].setBounds(BUTTON_WIDTH * i, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
			content.add(button[i]);
		}

		// 初期ゲームパネル
		makeMainPanel(10, 10, 10, 0, BUTTON_HEIGHT);

	}

	// 画面情報群
	private int windowWidth;
	private int windowHeight;

	private int widthNum;
	private int heightNum;
	private int bomNum;
	private int left;
	private int top;

	// ゲームパネル生成
	// mainContentインスタンスを再生成する
	// 引数：横マス数、縦マス数、爆弾数、左上x座標、左上y座標
	private void makeMainPanel(int _widthNum, int _heightNum, int _bomNum, int _left, int _top) {

		// 各値設定
		widthNum = _widthNum;
		heightNum = _heightNum;
		bomNum = _bomNum;
		left = _left;
		top = _top;

		// ウィンドウのリサイズ
		windowWidth = Math.max(BUTTON_WIDTH * buttonNum, widthNum * MainPanel.BOX_SIZE + MARGIN_WIDTH);
		windowHeight = BUTTON_HEIGHT + heightNum * MainPanel.BOX_SIZE + MARGIN_HEIGHT + Status.S_HEIGHT;
		setSize(windowWidth, windowHeight);

		// ゲームパネル生成
		mainContent  = new MainPanel(widthNum, heightNum, bomNum, left, top, windowWidth, windowHeight);
		content.add(mainContent);

	}

	// キーボード入力リスナー
	// 'R'を離す入力のみ判定する
	public class MyKeyAdapter extends KeyAdapter {

		// キー押下時インスタンスの再生成
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_R) {
				content.remove(mainContent);
				makeMainPanel(widthNum, heightNum, bomNum, left, top);
			}
		}
	}

}
