package ph.adoremus.log;

import android.util.Log;

public class Logger {
	public static Logger logger;
	public static String className;
	
	private Logger(){
		
	}
	
	public static Logger getInstance(String name){
		if (logger == null){
			logger = new Logger();
		}
		className = name;
		return logger;
	}
	
	public void debug(String msg){
		Log.d(className, msg);
	}
	
	public void error(String msg, Throwable t){
		Log.e(className, msg, t);
	}
	
}
