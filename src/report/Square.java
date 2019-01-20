package report;

/*
 * マインスイーパーの各マスのオブジェクト
 */
public class Square {

	// マスの状態
	private boolean openFlag;
	private boolean bomFlag;
	private boolean userFlag;
	private int bomNum;

	public Square(){
		openFlag = false;
		bomFlag = false;
		userFlag = false;
		bomNum = 0;
	}

	// 各種セッター
	// 戻り値：成功:0、失敗(変更なし):-1
	public int setOpenFlag(boolean _openFlag) {
		if(openFlag == _openFlag) {
			return -1;
		}else {
			openFlag = _openFlag;
			return 0;
		}
	}

	public int setBomFlag(boolean _bomFlag) {
		if(bomFlag == _bomFlag) {
			return -1;
		}else {
			bomFlag = _bomFlag;
			return 0;
		}
	}

	public int setUserFlag(boolean _userFlag) {
		if(userFlag == _userFlag) {
			return -1;
		}else {
			userFlag = _userFlag;
			return 0;
		}
	}

	public int setBomNum(int _bomNum) {
		if(bomNum == _bomNum) {
			return -1;
		}else {
			bomNum = _bomNum;
			return 0;
		}
	}

	// 各種ゲッター
	public boolean isOpen() {
		return openFlag;
	}


	public boolean isBomFlag() {
		return bomFlag;
	}


	public boolean isUserFlaged() {
		return userFlag;
	}


	public int getBomNum() {
		return bomNum;
	}

}
