package report;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

/*
 * ステータス欄のパネル
 */
@SuppressWarnings("serial")
public class Status extends JPanel{

	// 定数群
	// 内部パネルごとの余白
	public static final int MARGIN = 5;

	// 各パネルサイズ
	public static final int S_WIDTH = 300;
	public static final int S_HEIGHT = 55;

	public static final int TIMER_WIDTH = 120;
	public static final int TIMER_HEIGHT = 45;

	public static final int FLAG_NUM_WIDTH = 150;
	public static final int FLAG_NUM_HEIGHT = 20;

	public static final int CS_WIDTH = 150;
	public static final int CS_HEIGHT = 20;

	private MyTimer timer;
	private JLabel unFlaged;
	private JLabel finishString;

	private int unFlagedNum;

	// ステータス欄のパネル
	public Status(int left, int top, int _unFlagedNum){
		super();
		unFlagedNum = _unFlagedNum;

		// パネル設定
		setBorder(new LineBorder(Color.GRAY));
		setBounds(left, top, S_WIDTH, S_HEIGHT);


		// 各種ラベル生成
		// タイマー
		timer = new MyTimer(MARGIN, MARGIN, TIMER_WIDTH, TIMER_HEIGHT);

		// 未フラグ爆弾数
		unFlaged = new JLabel();
		unFlaged.setBounds(MARGIN * 2 + TIMER_WIDTH, MARGIN, FLAG_NUM_WIDTH, FLAG_NUM_HEIGHT);
		unFlaged.setBorder(new BevelBorder(BevelBorder.LOWERED));
		unFlaged.setText("爆弾残り:" + String.format("%02d", unFlagedNum));

		// 状態表示文字列
		finishString = new JLabel(" ");
		finishString.setBounds(MARGIN * 2 + TIMER_WIDTH, MARGIN * 2 + FLAG_NUM_HEIGHT, CS_WIDTH, CS_HEIGHT);
		finishString.setBorder(new BevelBorder(BevelBorder.LOWERED));

		setLayout(null);
		add(timer);
		add(unFlaged);
		add(finishString);


	}

	// タイマーの開始、停止
	public void timerStart() {
		timer.start();
	}
	public void timerStop() {
		timer.stop();
	}

	// 未フラグ爆弾数表示の設定
	// 引数：爆弾数-フラグ数
	// 戻り値：引数と同じ値
	public int setUnFlagedNum(int _unFlagedNum) {
		unFlagedNum = _unFlagedNum;
		unFlaged.setText("爆弾残り: " + String.format("%02d", unFlagedNum));
		return unFlagedNum;
	}

	// 状態文字列の設定
	// 引数：状態（0:継続、1:クリア、2:ゲームオーバー）
	// 戻り値：引数と同じ値
	public int setFinishString(int endFlag) {
		if(endFlag == 0) {
			finishString.setText("Challenging...");
		}else if(endFlag == 1) {
			finishString.setText("CLEAR!!");
			timer.setForeground(Color.RED);
		}else {
			finishString.setText("GAME OVER!!");
		}
		return endFlag;
	}

}
