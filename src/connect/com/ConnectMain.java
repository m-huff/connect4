package connect.com;

import connect.window.MainWindow;

public class ConnectMain {

	public static void main(String[] args) {
		if (!ConfigLoader.checkConfigExists()) {
			ConfigLoader.setToDefaults();
			ConfigLoader.saveConfig();
		}
		ConfigLoader.loadConfig();
		
		final MainWindow c4 = new MainWindow();
	}
}
