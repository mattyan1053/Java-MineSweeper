package report;

public class Square {
	private boolean openFlag; // オープンフラグ
	private boolean bomFlag; // 爆弾か否か
	private boolean userFlag; // ユーザーの立てた爆弾フラグ
	private int bomNum; // 周りの爆弾の数

	// コンストラクター
	Square(){
		openFlag = false;
		bomFlag = false;
		userFlag = false;
		bomNum = 0;
	}

	// 各種セッター、ゲッター
	// 戻り値：成功0、失敗-1
	public int setOpenFlag(boolean openFlag) {
		if(this.openFlag == openFlag) {
			return -1;
		}else {
			this.openFlag = openFlag;
			return 0;
		}
	}

	public boolean isOpen() {
		return openFlag;
	}

	public int setBomFlag(boolean bomFlag) {
		if(this.bomFlag == bomFlag) {
			return -1;
		}else {
			this.bomFlag = bomFlag;
			return 0;
		}
	}

	public boolean isBomFlag() {
		return bomFlag;
	}

	public int setUserFlag(boolean userFlag) {
		if(this.userFlag == userFlag) {
			return -1;
		}else {
			this.userFlag = userFlag;
			return 0;
		}
	}

	public boolean isUserFlaged() {
		return userFlag;
	}

	public int setBomNum(int bomNum) {
		if(this.bomNum == bomNum) {
			return -1;
		}else {
			this.bomNum = bomNum;
			return 0;
		}
	}

	public int getBomNum() {
		return bomNum;
	}

}
