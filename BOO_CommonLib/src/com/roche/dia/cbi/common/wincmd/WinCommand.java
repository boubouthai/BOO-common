package com.roche.dia.cbi.common.wincmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WinCommand {
	String 	commandLine;
	String 	outputRes;
	String 	errorRes;
	int		errorCode=0;
	
	public int runProcess(String command) {
		commandLine = command;
		outputRes = errorRes = "";
		errorCode = 0;
		int retVal=0;
		Process p=null;
		
		try {
			p = Runtime.getRuntime().exec(command);

			StringBuffer strBuff=new StringBuffer();
			p.waitFor();
			 BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		     String line = ""; 
		     while ((line=reader.readLine())!=null) {
		    	 strBuff.append(line+"\n");
		     }
		     outputRes = strBuff.toString();
			 BufferedReader readerErr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		     String line2 = ""; strBuff= new StringBuffer("");
		     while ((line2=readerErr.readLine())!=null) {
		    	 strBuff.append(line2+"\n");
		     }
	    	 errorRes = strBuff.toString();
			retVal = p.exitValue();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		errorCode=retVal;
		return retVal;
	}
	
	public String getOutputRes() { return outputRes; }
	public String getErrorRes()  { return errorRes; }
}
