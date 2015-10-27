package connect.com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ConfigLoader {
	
	public static int connectNum;
	
	static Properties config = new Properties();
	static InputStream input = null;
	static OutputStream output = null;
	
	public static String configPath = System.getProperty("user.home") + "/AppData/Roaming/Connect4/";
	
	static File prop = new File(configPath);
	static File cfg = new File(configPath + "config.properties");
	
	public static void loadConfig() {
		try {
			input = new FileInputStream(cfg);
			config.load(input);

			connectNum = Integer.parseInt(config.getProperty("connect_how_many"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveConfig() {
		try {
			prop.mkdirs();
			if(!prop.exists()) {
				try {
					prop.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 
			
			if(!cfg.exists()) {
				try {
					cfg.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			output = new FileOutputStream(cfg);

			config.setProperty("connect_how_many", String.valueOf(connectNum));
			
			config.store(output, null);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isInt(String in) {
	    try {
	        Integer.parseInt(in);
	        return true;
	    }
	    catch(NumberFormatException e) {
	        return false;
	    }
	}
	
	public static boolean isBoolean(String in) {
	    try {
	        Boolean.parseBoolean(in);
	        return true;
	    }
	    catch(NumberFormatException e) {
	        return false;
	    }
	}
	
	public static boolean checkConfigExists() {
		return cfg.exists();
	}
	
	public static void setToDefaults() {		
		connectNum = 4;
	}

}
