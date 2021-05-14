package com.shva.etl.predicate;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;

import com.shva.etl.util.StringUtils;



public class LegalFilePredicate implements Predicate<Path> {

	
	static String[] validFilesExt = new String[] {".wl"};
	static List<String> validFilesExtList =  StringUtils.stringArrToStringList(validFilesExt);
	static String[] excludePath = new String[] {".csv" };
	String runDateTime;
	public LegalFilePredicate(String runDateTime) {
		this.runDateTime = runDateTime;
	}

	@Override
	public boolean test(Path path) {

		String fname = path.toString();

		for (String validExcludePath : excludePath) {
			if (fname.indexOf(validExcludePath)>-1) {
				return false;
			}
		}
		

		
		if (Files.isDirectory(path)) {
			return false;
		}else {
			if (! fname.contains("CALC")) {
				return false;
			}
		}
		
		
		for (String validFileExt : validFilesExt) {
			if (fname.endsWith(validFileExt)) {

				LocalDateTime localDateTime = LocalDateTime.now();
				if (this.runDateTime!=null) {
					localDateTime = LocalDateTime.parse(this.runDateTime.trim());//"2021-02-06T00:39:10");
				}
				int curHour = localDateTime.getHour();
				int curDay =  localDateTime.getDayOfMonth();
				if (curHour<2) {
					localDateTime = localDateTime.minusDays(1);
				}
				String year = String.valueOf(localDateTime.getYear());
				String dateToSearch = org.apache.commons.lang3.StringUtils.leftPad(""+localDateTime.getDayOfMonth(),2,"0") + "" + org.apache.commons.lang3.StringUtils.leftPad(""+localDateTime.getMonth().getValue(),2,"0") + "" + year.substring(2);
				if (fname.indexOf(dateToSearch)>-1) {
					return true;
				}
			
			}
			
		}
		
		
		
		
		return false;
	}
	
}

