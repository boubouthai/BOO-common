package com.roche.dia.cbi.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sxbouco1
 *
 */
public class StringTools {

	public static String[] mergeStringArray(String[] first, String[] second) {
		List<String> both = new ArrayList<String>(first.length + second.length);
		Collections.addAll(both, first);
		Collections.addAll(both, second);
		return both.toArray(new String[] {});
	}
	
	public static String vectorToStringWithSeparator(Vector<String>o, String s) {
		if (o==null || o.size()==0)
			return "";
		else
			return arrayToStringWithSeparator(o.toArray(new String[0]), s);
	}
	public static String arrayToStringWithSeparator(String[]o, String s) {
		String retVal="";
		
		for (String string : o) {
			retVal += (s+string);
		}
		
		return retVal;
	}

	// This method help retrieve the sub-trigram from any group
	// As our policy, this trigram respect the matching
	public static String getMatchingProject(String[] tt, String _src) {
		String retVal = null;
		Pattern regExpMatch = Pattern.compile("#*[a-zA-Z0-9]{3}_");
		if (_src != null && _src.length() > 4) {
			String _groupName = _src.substring(4);
			for (String string : tt) {
				if (_groupName.contains(string)) {
					Matcher myMatch = regExpMatch.matcher(_src);
					if (myMatch.find()) {
						retVal = myMatch.group();
						if (retVal != null && retVal.length() > 2) {
							retVal = retVal.substring(0, retVal.length() - 1);
							if (retVal.charAt(0) == '#')
								retVal = retVal.substring(1);
						}
					}
					break;
				}
			}
		}

		return retVal;
	}
	
	public static String getFormattedLoginName(String login) {
		String retVal = login;
		int idx = login.toLowerCase().lastIndexOf("_dev");
		idx = (idx<=1)?login.toLowerCase().lastIndexOf(".dev"):idx;
		idx = (idx<=1)?login.toLowerCase().lastIndexOf(".uat"):idx;
		idx = (idx<=1)?login.toLowerCase().lastIndexOf("_uat"):idx;
		idx = (idx<=1)?login.toLowerCase().lastIndexOf("_prd"):idx;
		idx = (idx<=1)?login.toLowerCase().lastIndexOf("_inf"):idx;
		idx = (idx<=1)?login.toLowerCase().lastIndexOf(".ft"):idx;
		idx = (idx<=1)?login.toLowerCase().lastIndexOf(".alim"):idx;
		idx = (idx<=1)?login.toLowerCase().lastIndexOf(".admin"):idx;
		if (idx>1)
			retVal = login.substring(0,idx);
		else {
			idx = login.toLowerCase().lastIndexOf("@socgen.com");
			idx = (idx<=1)? login.toLowerCase().lastIndexOf("@sgcib.com"):idx;
			if (idx>1)
				retVal = login.substring(0,idx);
		}
		
		return retVal;
	}
	
	/**
	 * Parse String, and extract result from keyword
	 * 
	 * @param _sp
	 *            variable Separator (should not be a space)
	 * @param _s
	 *            String to be parse
	 * @return Map<String,Object> with String as the keyword
	 *            
	 */
	  public static HashMap<String, String> locateKeyword(String _sp, String _s) {
		  	HashMap<String,String> retVal = new HashMap<String, String>();
//		  	_s = _s.toLowerCase();
			StringTokenizer myParam=new StringTokenizer(_s, _sp);

			while (myParam.hasMoreTokens()) {
				String keyword  = (String) myParam.nextToken();
				Pattern p = Pattern.compile("^([^=]*)=([^=]*)$");
				
				Matcher m = p.matcher(keyword);
				if (m.find())
					retVal.put(m.group(1), m.group(2));
			}

		  	return retVal;
	  }

		/**
		 * Split a String from the form a=b
		 * 
		 * @param _s
		 *            String to be parse
		 * @return Map<String,String> with String as the keyword
		 *            
		 */
	  public static HashMap<String, String> splitString(String str) {
			HashMap<String,String> retVal = new HashMap<String, String>();
			Pattern p = Pattern.compile("^([^=]*)=([^=]*)$");
			
			Matcher m = p.matcher(str);
			if (m.find())
				retVal.put(m.group(1), m.group(2));

		  
		  return retVal;
	  }
	public static void main(String[] args) {
		String tst = "abc42=123;def_zzz=999;2eff=777";
		String tst2= "abcdefgh=blabla+dfdfj200";
		HashMap<String, String> myEntry = locateKeyword(";",tst);
		for (String string : myEntry.keySet()) {
			System.out.println(string+ "   =>"+myEntry.get(string));
		}
		
		HashMap<String, String> myEntry2 = splitString(tst2);
		for (String string : myEntry2.keySet()) {
			System.out.println(string+ "   =>"+myEntry2.get(string));
		}
	}

}
