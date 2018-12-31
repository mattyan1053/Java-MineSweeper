package report;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMouseAdapter extends MouseAdapter{

	public static final int LEFT_ON = 1;
	public static final int RIGHT_ON = 2;
	public static final int BOTH_ON = 3;
	public static final int NO_CHANGE = 0;

	private boolean leftFlag;
	private boolean rightFlag;
	private boolean bothFlag;

	private boolean stateFlag;

	private int mx;
	private int my;

	MyMouseAdapter(){
		leftFlag = false;
		rightFlag = false;
		bothFlag = false;

		stateFlag = false;

		mx = 0;
		my = 0;
	}

	public void mousePressed(MouseEvent e) {
		System.out.println("Button Pressed");
		int btn = e.getButton();
		if(btn == MouseEvent.BUTTON1) {
			if(rightFlag = true) {
				leftFlag = false;
				rightFlag = false;
				bothFlag = true;
			}else {
				leftFlag = true;
			}
		}
		else if(btn == MouseEvent.BUTTON3) {
			if(leftFlag = true) {
				leftFlag = false;
				rightFlag = false;
				bothFlag = true;
			}else {
				rightFlag = true;
			}
		}
		return;
	}

	public void mouseReleased(MouseEvent e) {
		System.out.println("Button Released");
		if(bothFlag == true) {
			stateFlag = true;
			mx = e.getX();
			my = e.getY();
			return;
		}
		int btn = e.getButton();
		if(btn == MouseEvent.BUTTON1) stateFlag = true;
		else if(btn == MouseEvent.BUTTON3) stateFlag = true;
		mx = e.getX();
		my = e.getY();
		return;
	}

	public int getState() {
		if(stateFlag) {
			stateFlag = false;
			if(leftFlag) {
				leftFlag = false;
				return LEFT_ON;
			}
			if(rightFlag) {
				rightFlag = false;
				return RIGHT_ON;
			}
			if(bothFlag) {
				bothFlag  = false;
				return BOTH_ON;
			}
		}
		return NO_CHANGE;
	}

	public int getMouseX() {
		return mx;
	}

	public int getMouseY() {
		return my;
	}

}
