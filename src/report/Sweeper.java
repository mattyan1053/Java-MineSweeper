package report;

import java.util.Random;

public class Sweeper {

	private int width;
	private int height;

	private int bomNum; // 爆弾数

	private Square[][] sq;

	private int unFlagedNum;

	Sweeper(int width, int height, int bomNum){
		this.width = width;
		this.height = height;

		this.bomNum = bomNum;

		unFlagedNum = bomNum;

		// マス目の設定、外側をダミーとして余分に取る
		sq = new Square[this.height + 2][this.width + 2];

		for(int i=0; i<sq.length; i++) {
			for(int j=0; j<sq[i].length; j++) {
				sq[i][j] = new Square();
			}
		}

	}

	// ゲームの初期化
	public void initGame(int _x, int _y) {
		Random r = new Random();
		for(int bom = bomNum; bom > 0; bom--) {
			boolean breakFlag = false;
			while(!breakFlag) {
				int x = r.nextInt(width) + 1;
				int y = r.nextInt(height) + 1;
				if(x == _x && y == _y) continue;
				System.out.println(y + " " + x);
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
		if(sq[y][x].isBomFlag()) return;
		if(sq[y][x].getBomNum() == 0) {
			for(int i=-1; i<=1;i++) {
				for(int j=-1;j<=1;j++) {
					if(i == 0 && j == 0) continue;
					open(x + j, y + i);
				}
			}
		}
		return;
	}

	// フラグの数と周りの爆弾の数が一致しているときまとめて周り８マスをオープン
	public void openFull(int x, int y) {
		if(!sq[y][x].isOpen()) return;
		if(sq[y][x].getBomNum() == 0) return;
		int cnt = 0;
		for(int i=-1; i<=1;i++) {
			for(int j=-1;j<=1;j++) {
				if(i == 0 && j == 0) continue;
				if(sq[y + i][x + j].isUserFlaged()) cnt++;
			}
		}
		if(cnt == sq[y][x].getBomNum()) {
			for(int i=-1; i<=1;i++) {
				for(int j=-1;j<=1;j++) {
					if(i == 0 && j == 0) continue;
					open(x + j, y + i);
				}
			}
		}
		return;
	}

	// 爆弾フラグの反転
	public void setFlag(int x, int y) {
		if(sq[y][x].isOpen()) return;
		sq[y][x].setUserFlag(!sq[y][x].isUserFlaged());
		if(sq[y][x].isUserFlaged()) unFlagedNum--;
		else unFlagedNum++;
		return;
	}

	public int getUnFlagedNum() {
		return unFlagedNum;
	}

	public Square getSquare(int x, int y) {
		return sq[y][x];
	}

	// ゲーム終了チェック 継続:0 クリアー:1 ゲームオーバー:2
	public int checkFinish() {
		int clearFlag = 1;
		for(int i=1; i<=height; i++) {
			for(int j=1; j<=width; j++) {
				if(sq[i][j].isBomFlag() && sq[i][j].isOpen()) return 2;
				if(!sq[i][j].isBomFlag() && !sq[i][j].isOpen()) clearFlag = 0;
			}
		}
		return clearFlag;
	}

}
