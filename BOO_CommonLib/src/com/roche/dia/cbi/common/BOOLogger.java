package com.roche.dia.cbi.common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BOOLogger {
	
	String file_path=null;
	BufferedWriter myOut=null;
	Boolean		logIsActivated=false;
	Boolean		appendFile=false;

	public static void main(String[] args) {
		BOOLogger myo = new BOOLogger("C://Temp//PublicationExt_V5.log");
		myo.writeLine("Test test");
	}
	public BOOLogger(String fileLog) {
		this (fileLog, true, false);
	}
	public BOOLogger(String fileLog, Boolean activateLog, Boolean appendFile) {
		logIsActivated = activateLog;
		if (fileLog!=null) {
			file_path = fileLog;
			try {
				init();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void init() throws IOException {
		if (logIsActivated) {
			myOut = new BufferedWriter(new FileWriter(file_path, appendFile));
		}
	}

	public void writeLine(String _str) {
			try {
				if (logIsActivated && myOut!=null) {
					myOut.write(_str); myOut.newLine(); myOut.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
		
	}
}
