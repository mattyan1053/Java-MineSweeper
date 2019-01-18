package report;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

public class MyTimer extends JLabel{

	private Timer timer;
	private MyTask task;
	private Time time;

	public MyTimer() {
		super();
		timer = new Timer(true);
		task = new MyTask();
		time = new Time();
		setText("経過時間: " + String.format("%02d", time.getMinute()) + ":" + String.format("%02d", time.getSecond()));
	}

	public void start() {
		timer.schedule(task, 0, 1000);
	}

	public void stop() {
		task.cancel();
		setText("経過時間: " + String.format("%02d", time.getMinute()) + ":" + String.format("%02d", time.getSecond()));
	}

	public String getTime() {
		return String.format("%02d", time.getMinute()) + ":" + String.format("%02d", time.getSecond());
	}

	class MyTask extends TimerTask {

		@Override
		public void run() {
			time.tick();
			setText("経過時間: " + String.format("%02d", time.getMinute()) + ":" + String.format("%02d", time.getSecond()));
		}

	}

}

class Time {
	private int second;
	private int minute;

	public Time() {
		second = 0;
		minute = 0;
	}

	public void tick() {
		second++;
		if(second >= 60) {
			minute++;
			second = 0;
		}
	}

	public int getSecond() {
		return second;
	}

	public int getMinute() {
		return minute;
	}

}