package report;


// v1.0.0
public class Main {

	public static void main(String[] args) {

		MainWindow mw = new MainWindow("MineSweeper");
		// ゲーム画面のサイズを大きくしたときデフォルト位置だと多くのディスプレイではみ出てしまう
		// 画面をディスプレイ左上に表示するように
		mw.setLocation(100, 100);

	}
}
