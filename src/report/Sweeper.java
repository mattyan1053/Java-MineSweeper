package report;

import java.util.Random;

public class Sweeper {

	private int width; // 横幅
	private int height; // 縦幅

	private int bomNum; // 爆弾数

	private boolean endFlag; // ゲーム終了フラグ

	private Square[][] sq;

	Sweeper(int width, int height, int bomNum){
		this.width = width;
		this.height = height;

		this.bomNum = bomNum;

		this.endFlag = false;

		// マス目の設定、外側をダミーとして余分に取る
		sq = new Square[this.height + 2][this.width + 2];

		for(int i=0; i<sq.length; i++) {
			for(int j=0; j<sq.length; j++) {
				sq[i][j] = new Square();
			}
		}

		initGame();

	}

	// ゲームの初期化
	private void initGame() {
		Random r = new Random();
		for(int bom = bomNum; bom > 0; bom--) {
			boolean breakFlag = false;
			while(!breakFlag) {
				int x = r.nextInt(width) + 1;
				int y = r.nextInt(height) + 1;
				System.out.println();
				breakFlag = (sq[y][x].setBomFlag(true) == 0);
			}
		}
		for(int i=1; i<=height; i++) {
			for(int j=1; j<=width; j++) {
				sq[i][j].setBomNum(countBom(j, i));
			}
		}
	}

	private int countBom(int x, int y) {
		int bomNum = 0;
		for(int i=(-1); i<=1;i++) {
			for(int j=(-1);j<=1;j++) {
				if(i == 0 && j == 0) continue;
				if(sq[y + i][x + j].isBomFlag()) bomNum++;
			}
		}
		return bomNum;
	}

	// ますを開ける
	public void open(int x, int y) {
		if(x < 1 || x > width || y < 1 || y > height) return;
		if(sq[y][x].isOpen()) return;
		if(sq[y][x].isUserFlaged()) return;
		sq[y][x].setOpenFlag(true);
		if(sq[y][x].isBomFlag()) {
			this.endFlag = true;
			return;
		}
		if(sq[y][x].getBomNum() == 0) {
			for(int i=-1; i<=1;i++) {
				for(int j=-1;j<=1;j++) {
					if(i==j) continue;
					open(x + j, y + i);
				}
			}
		}
		return;
	}

	// 周りに爆弾がないとき一気にオープン
	public void openFull(int x, int y) {
		if(sq[y][x].isOpen()) return;
		open(x, y);
		if(sq[y][x].getBomNum() != 0) return;
		for(int i=-1; i<=1;i++) {
			for(int j=-1;j<=1;j++) {
				if(i==j) continue;
				open(x + j, y + i);
			}
		}
		return;
	}

	// 爆弾フラグの反転
	public void setFlag(int x, int y) {
		if(sq[y][x].isOpen()) return;
		sq[y][x].setUserFlag(!sq[y][x].isUserFlaged());
		return;
	}

	public Square getSquare(int x, int y) {
		return sq[y][x];
	}

	public boolean checkClear() {
		int cnt = 0;
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				if(sq[i][j].isBomFlag() && sq[i][j].isUserFlaged()) {
					cnt++;
				}
			}
		}
		return cnt == bomNum;
	}

}
