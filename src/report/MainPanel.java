package report;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/*
 * ゲーム画面クラス
 * 描画処理と内部処理は別スレッドで行う
 * Sweeperインスタンスから読み取った情報を実際に描画していく
 */
public class MainPanel extends JPanel implements Runnable{

	public static final int BOX_SIZE = 20;
	public static final int IMAGE_TIPS_SIZE = 20;

	// パネルに含むインスタンス群
	private Sweeper gameUnit;
	private Status status;
	private Thread t;

	// 画像ハンドラ
	private BufferedImage img;

	// ゲーム本体の情報
	private int widthNum;
	private int heightNum;
	private int bomNum;

	private boolean startFlag;

	// 引数：横マスの数, 縦マスの数, 爆弾の個数, パネル位置x座標, パネル位置y座標, 親フレームのxサイズ, 親フレームのyサイズ
	MainPanel(int _widthNum, int _heightNum, int _bomNum, int left, int top, int windowWidth, int windowHeight){
		super();

		// 初期化
		startFlag = false;
		widthNum = _widthNum;
		heightNum = _heightNum;
		bomNum = _bomNum;

		gameUnit = new Sweeper(widthNum, heightNum, bomNum);
		status = new Status(0, heightNum * BOX_SIZE, bomNum);

		try {
			img = ImageIO.read(new File("./src/report/box.bmp"));
		} catch (IOException e) {
			System.err.println("Failed to Load Image File.");
			e.printStackTrace();
		}

		// レイアウト
		setLayout(null);
		setBounds(left, top, Math.max(windowWidth, widthNum * BOX_SIZE), heightNum * BOX_SIZE + Status.S_HEIGHT);
		add(status);
		addMouseListener(new MyMouseAdapter());

		// スレッド操作
		t = new Thread(this);
		t.start();

	}

	// 本体の描画及びステータス欄の処理
	@Override
	public void run() {
		while(true) {

			repaint();

			// フラグ数をステータス欄に反映
			status.setUnFlagedNum(gameUnit.getUnFlagedNum());

			// ゲーム終了ならステータス欄を変更して離脱
			if(status.setFinishString(gameUnit.checkFinish()) != 0) {
				status.timerStop();
				break;
			}
		}
	}

	// ゲーム本体描画部分
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for(int y=1; y<=heightNum; y++) {
			for(int x=1; x<=widthNum; x++) {
				Square s = gameUnit.getSquare(x , y);
				// マスの描画開始位置(destination)
				int dx = (x - 1) * BOX_SIZE;
				int dy = (y - 1) * BOX_SIZE;

				// 各マスの描画処理
				if(s.isUserFlaged()){
					g.drawImage(img, dx, dy, dx + BOX_SIZE, dy + BOX_SIZE, IMAGE_TIPS_SIZE * 2, 0, IMAGE_TIPS_SIZE * 3, IMAGE_TIPS_SIZE, this);
				}
				else if(!s.isOpen()) {
					g.drawImage(img, dx, dy, dx + BOX_SIZE, dy + BOX_SIZE, 0, 0, IMAGE_TIPS_SIZE, IMAGE_TIPS_SIZE, this);
				}
				else {
					if(s.isBomFlag()) {
						g.drawImage(img, dx, dy, dx + BOX_SIZE, dy + BOX_SIZE, IMAGE_TIPS_SIZE * 3, 0, IMAGE_TIPS_SIZE * 4, IMAGE_TIPS_SIZE, this);
					}
					else {
						// まわりの爆弾の数にあわせて数字の画像を表示
						int bNum = s.getBomNum();
						if(bNum == 0) {
							g.drawImage(img, dx, dy, dx + BOX_SIZE, dy + BOX_SIZE, IMAGE_TIPS_SIZE, 0, IMAGE_TIPS_SIZE * 2, IMAGE_TIPS_SIZE, this);
						}
						else if(bNum <= 4) {
							g.drawImage(img, dx, dy, dx + BOX_SIZE, dy + BOX_SIZE, IMAGE_TIPS_SIZE * (bNum - 1), IMAGE_TIPS_SIZE, IMAGE_TIPS_SIZE * bNum, IMAGE_TIPS_SIZE * 2, this);
						}
						else {
							g.drawImage(img, dx, dy, dx + BOX_SIZE, dy + BOX_SIZE, IMAGE_TIPS_SIZE * (bNum - 5), IMAGE_TIPS_SIZE * 2, IMAGE_TIPS_SIZE * (bNum - 4), IMAGE_TIPS_SIZE * 3, this);
						}
					}
				}
			}
		}
	}

	// MainPanelクラスのメンバ変数を扱うためインナークラスとした
	// 右クリック、左クリック、同時クリックの３種類について処理
	public class MyMouseAdapter extends MouseAdapter{

		// マウスの状態
		private int mouseState;
		public static final int LEFT_ON = 1;
		public static final int RIGHT_ON = 2;
		public static final int BOTH_ON = 3;
		public static final int NO_CHANGE = 0;

		private int mx;
		private int my;

		MyMouseAdapter(){
			// 初期化
			mouseState = NO_CHANGE;
			mx = 0;
			my = 0;
		}

		// マウスボタン押下時の処理
		// どのボタンが押下されているかをmouseStateに保持
		@Override
		public void mousePressed(MouseEvent e) {

			int btn = e.getButton();

			// 押下ボタンをチェックして処理。すでに左右どちらかのボタンが押されていれば同時押しと判定
			// 左ボタン(=BUTTON1)
			if(btn == MouseEvent.BUTTON1) {
				if(mouseState == RIGHT_ON) mouseState = BOTH_ON;
				else mouseState = LEFT_ON;
			}
			// 右ボタン(=BUTTON3)
			else if(btn == MouseEvent.BUTTON3) {
				if(mouseState == LEFT_ON) mouseState = BOTH_ON;
				else mouseState = RIGHT_ON;
			}

			return;
		}

		// ボタンが離されたとき、mouseStateの状態に応じてゲーム処理
		@Override
		public void mouseReleased(MouseEvent e) {

			mx = e.getX();
			my = e.getY();

			// マスの番号が1~widthNum、1~heightNumなので調整
			mx += BOX_SIZE;
			my += BOX_SIZE;

			// ゲーム画面外
			if(mx < BOX_SIZE || mx > BOX_SIZE * (widthNum + 1)) return;
			if(my < BOX_SIZE || my > BOX_SIZE * (heightNum + 1)) return;

			// 各種処理
			if(startFlag) {
				if(mouseState == LEFT_ON) {
					gameUnit.open(mx / BOX_SIZE, my / BOX_SIZE);
				}
				else if(mouseState == RIGHT_ON) {
					gameUnit.setFlag(mx / BOX_SIZE, my / BOX_SIZE);
				}
				else if(mouseState == BOTH_ON) {
					gameUnit.openFull(mx / BOX_SIZE,  my / BOX_SIZE);
				}
			}
			// 最初の左クリック時ゲーム開始
			else if(mouseState == LEFT_ON){
				startFlag = true;
				gameUnit.initGame(mx / BOX_SIZE, my / BOX_SIZE);
				status.timerStart();
				gameUnit.open(mx / BOX_SIZE, my / BOX_SIZE);
			}

			mouseState = NO_CHANGE;

			return;
		}

	}

}
