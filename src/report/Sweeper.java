package report;

import java.util.Random;

/*
 * ゲーム本体の内部処理を行っているクラス
 * ゲーム開始時には必ずinitGame関数を呼び出さなければならない
 */
public class Sweeper {

	// ゲーム情報
	private int widthNum;
	private int heightNum;
	private int bomNum;

	private Square[][] sq;

	// 爆弾数ーフラグ数
	private int unFlagedNum;

	public Sweeper(int _widthNum, int _heightNum, int _bomNum){

		widthNum = _widthNum;
		heightNum = _heightNum;
		bomNum = _bomNum;
		unFlagedNum = bomNum;

		// マス目の初期化、外側をダミーとして余分に取る
		sq = new Square[heightNum + 2][widthNum + 2];
		for(int y=0; y<sq.length; y++) {
			for(int x=0; x<sq[y].length; x++) {
				sq[y][x] = new Square();
			}
		}

	}

	// ゲームの初期化
	// 爆弾を配置し、周りの爆弾数を記憶する
	// この関数は、必ずゲーム開始時に呼び出されなければならない
	// 引数：最初に開けるマス（fx, fy）
	public void initGame(int fx, int fy) {
		Random r = new Random();

		// 爆弾の配置
		for(int bom = bomNum; bom > 0; bom--) {
			boolean breakFlag = false;
			while(!breakFlag) {
				// 乱数の生成、ダミーマスを除外
				int x = r.nextInt(widthNum) + 1;
				int y = r.nextInt(heightNum) + 1;
				// 初期マスなので爆弾配置から除外
				if(x == fx && y == fy) continue;
				// まだ爆弾が置かれていないマスであれば爆弾を置いて次のループへ
				breakFlag = (sq[y][x].setBomFlag(true) == 0);
			}
		}

		// 各マスの周りの爆弾数を記憶
		for(int y=1; y<=heightNum; y++) {
			for(int x=1; x<=widthNum; x++) {
				sq[y][x].setBomNum(countBom(x, y));
			}
		}
	}

	// 座標(x, y)のマスの周りの爆弾数をカウント
	// 引数：マスの座標(x, y)
	private int countBom(int x, int y) {
		int bomNum = 0;
		for(int i=(-1); i<=1;i++) {
			for(int j=(-1);j<=1;j++) {
				// (x, y)指定されたマス自身はカウントしない
				if(i == 0 && j == 0) continue;
				if(sq[y + i][x + j].isBomFlag()) bomNum++;
			}
		}
		return bomNum;
	}

	// 座標(x, y)のマスを開ける
	// 引数：マスの座標（x, y）
	public void open(int x, int y) {
		// ゲーム画面外ならなにもしない
		if(x < 1 || x > widthNum || y < 1 || y > heightNum) return;

		// あけることのできないマス（すでに開いているor爆弾フラグがたっている）ならなにもしない
		if(sq[y][x].isOpen() || sq[y][x].isUserFlaged()) return;

		sq[y][x].setOpenFlag(true);

		// あけたマスの周りに爆弾がなく、爆弾でもなければ周りのマスも開ける
		if(sq[y][x].getBomNum() == 0 && !sq[y][x].isBomFlag()) {
			for(int i=-1; i<=1;i++) {
				for(int j=-1;j<=1;j++) {
					open(x + j, y + i);
				}
			}
		}

		return;
	}

	// フラグの数と周りの爆弾の数が一致しているときまとめて周り８マスをオープン
	// 引数：あけたい８マスの中央のマスの座標x, y
	public void openFull(int x, int y) {
		// 開いていないマスならなにもしない
		if(!sq[y][x].isOpen()) return;

		// 周りのフラグ数を数える
		int cnt = 0;
		for(int i=-1; i<=1;i++) {
			for(int j=-1;j<=1;j++) {
				if(i == 0 && j == 0) continue;
				if(sq[y + i][x + j].isUserFlaged()) cnt++;
			}
		}

		// フラグ数と爆弾数が一致していれば周り８マスをオープン
		if(cnt == sq[y][x].getBomNum()) {
			for(int i=-1; i<=1;i++) {
				for(int j=-1;j<=1;j++) {
					open(x + j, y + i);
				}
			}
		}

		return;
	}

	// 爆弾フラグを反転させ、未フラグ数を変動
	// 引数：フラグを立てたいマスの座標x, y
	public void setFlag(int x, int y) {
		// すでに開いているマスにはなにもしない
		if(sq[y][x].isOpen()) return;

		boolean flag = sq[y][x].isUserFlaged();
		if(flag) unFlagedNum++;
		else unFlagedNum--;
		sq[y][x].setUserFlag(!flag);

		return;
	}

	// ゲーム終了チェック
	// 戻り値：継続:0 クリアー:1 ゲームオーバー:2
	// 条件   ：爆弾のマスが開いている -> ゲームオーバー
	//			    爆弾でないマスが全て開いている -> クリアー
	//			   その他 -> 継続
	public int checkFinish() {
		int clearFlag = 1;
		for(int y=1; y<=heightNum; y++) {
			for(int x=1; x<=widthNum; x++) {
				// 爆弾マスが一つでも開いていたら即ゲームオーバー
				if(sq[y][x].isBomFlag() && sq[y][x].isOpen()) return 2;
				// 爆弾でないマスで開いていないところがあればクリアではない
				if(!sq[y][x].isBomFlag() && !sq[y][x].isOpen()) clearFlag = 0;
			}
		}
		return clearFlag;
	}

	// ゲッター
	public int getUnFlagedNum() {
		return unFlagedNum;
	}

	// 座標(x, y)のマスのインスタンスを返す
	// 引数：取得したいSquareインスタンスの座標x, y
	public Square getSquare(int x, int y) {
		return sq[y][x];
	}

}
