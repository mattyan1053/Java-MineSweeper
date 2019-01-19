package report;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.border.BevelBorder;

/*
 * タイマーラベルを生成するクラス
 */
@SuppressWarnings("serial")
public class MyTimer extends JLabel{

	private Timer timer;
	private MyTask task;
	private Time time;

	public MyTimer(int left, int top, int width, int height) {
		super();
		timer = new Timer(true);
		task = new MyTask();
		time = new Time();

		// ラベルの設定
		setBounds(left, top, width, height);
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		setText("経過時間: " + String.format("%02d", time.getMinute()) + ":" + String.format("%02d", time.getSecond()));
	}

	// １秒毎にtask.run()を実行するようにスケジューリングして開始
	public void start() {
		timer.schedule(task, 0, 1000);
	}

	// スケジュール実行を停止
	public void stop() {
		task.cancel();
	}

	// スケジュール実行する内容を定義
	class MyTask extends TimerTask {

		// 実時間で１秒経つごとにTimeを１秒進める
		@Override
		public void run() {
			time.tick();
			setText("経過時間: " + String.format("%02d", time.getMinute()) + ":" + String.format("%02d", time.getSecond()));
		}

	}

}
/*
 * タイムを保持、操作するクラス
 */
class Time {
	private int second;
	private int minute;

	public Time() {
		second = 0;
		minute = 0;
	}

	// 一秒進める
	public void tick() {
		second++;
		if(second >= 60) {
			minute++;
			second = 0;
		}
	}

	// ゲッター群
	public int getSecond() {
		return second;
	}
	public int getMinute() {
		return minute;
	}

}