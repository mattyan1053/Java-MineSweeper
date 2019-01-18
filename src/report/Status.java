package report;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

public class Status extends JPanel{

	private MyTimer mt;
	private JLabel unFlaged;
	private JLabel finishString;

	private int unFlagedNum;

	public static final int MARGIN = 5;

	public static final int S_WIDTH = 300;
	public static final int S_HEIGHT = 55;

	public static final int TIMER_WIDTH = 120;
	public static final int TIMER_HEIGHT = 45;

	public static final int FLAG_NUM_WIDTH = 100;
	public static final int FLAG_NUM_HEIGHT = 20;

	public static final int CS_WIDTH = 100;
	public static final int CS_HEIGHT = 20;

	public Status(int width, int height, int _unFlagedNum){
		super();
		setBorder(new LineBorder(Color.GRAY));
		setBounds(width, height, S_WIDTH, S_HEIGHT);

		unFlagedNum = _unFlagedNum;

		mt = new MyTimer();
		mt.setBounds(MARGIN, MARGIN, TIMER_WIDTH, TIMER_HEIGHT);
		mt.setBorder(new BevelBorder(BevelBorder.LOWERED));

		unFlaged = new JLabel();
		unFlaged.setBounds(MARGIN * 2 + TIMER_WIDTH, MARGIN, FLAG_NUM_WIDTH, FLAG_NUM_HEIGHT);
		unFlaged.setBorder(new BevelBorder(BevelBorder.LOWERED));

		finishString = new JLabel(" ");
		finishString.setBounds(MARGIN * 2 + TIMER_WIDTH, MARGIN * 2 + FLAG_NUM_HEIGHT, CS_WIDTH, CS_HEIGHT);
		finishString.setBorder(new BevelBorder(BevelBorder.LOWERED));

		setLayout(null);
		add(mt);
		add(unFlaged);
		add(finishString);

		unFlaged.setText("爆弾残り:" + String.format("%02d", unFlagedNum));

	}

	public void timerStart() {
		mt.start();
	}

	public void timerStop() {
		mt.stop();
	}

	public void setUnFlagedNum(int _unFlagedNum) {
		unFlagedNum = _unFlagedNum;
		unFlaged.setText("爆弾残り: " + String.format("%02d", unFlagedNum));
	}

	public String getTime() {
		return mt.getTime();
	}

	public int setFinishString(int endFlag) {
		if(endFlag == 0) {
			finishString.setText("Challenging...");
		}else if(endFlag == 1) {
			finishString.setText("CLEAR!!");
			mt.setForeground(Color.RED);
		}else {
			finishString.setText("GAME OVER!!");
		}
		return endFlag;
	}

}
