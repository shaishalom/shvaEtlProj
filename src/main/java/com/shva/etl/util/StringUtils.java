package com.shva.etl.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;




public class StringUtils {

	
	public static String TAB="\t";
	
    public  static String cleanField(String strOrigion){
   	 
	   	 String str = strOrigion.replaceAll("/\\*", "");
	   	 str = str.replaceAll("\\*/", "");
		 str = str.replaceAll("\'", "");
		 str = str.replaceAll("\"", "");
		 str = str.replaceAll("\\|", "");
	   	 str = str.trim();
	   	 return str;
	}
    
    public static String indent(int size){
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append("\t");
    	for (int i = 0; i < size; i++) {  
    		sb.append("\t");
    	}
    	return sb.toString();
    	
    }
    
    public static int stringToInt(String str,int defaultInt) {
    	int num=defaultInt;
    	try {
    		num=Integer.parseInt(str);
    	}catch(NumberFormatException nfe) {
    		num=defaultInt;
    	}
    	return num;
    }
    
   public static String indent(int size,String tab){
    	
    	StringBuffer sb = new StringBuffer();
    	//sb.append(tab).append("|");
    	for (int i = 0; i < size; i++) {  
    		//sb.append(tab).append("|");
    		sb.append("|").append(tab);
    	}
    	return sb.toString();
    	
    }    
    
    public static String reverseHebrewText(String sentence)
    {
    			if (org.apache.commons.lang3.StringUtils.isEmpty(sentence)) {
    				return sentence;
    			}
        		if(isContainsOnlyEnglishAndNumbersExt(sentence)) { //for english only 
        			return sentence;
        		}	
        		
                StringBuilder sb = new StringBuilder(sentence.length() + 1);
                String[] words = sentence.split(" ");
                for (int i = words.length - 1; i >= 0; i--){
                            String str = words[i].toString();
                            if(isAnyLetterInHebrew(str)){ //for hebrew
                            			if (str.indexOf(".")>-1) {
	                            			String[] numOfWordsInsideDot = str.split("\\.");
	                            			if (isContainsOneLetterHebrew(numOfWordsInsideDot)) { //if one letter in hebrew do reverse to all sentence
	                            				//oposite Sentence
	                            				String wordWithDot = reverseSentenceAndReverseWordIfNeeded(numOfWordsInsideDot,".");
	                            				sb.append(wordWithDot).append(" ");
	                            			}else {
	                            				String wordWithDot = concatSentenceAndReverseWordIfNeeded(numOfWordsInsideDot,".");
	                            				sb.append(wordWithDot).append(" ");
	                            				
	                            			}
	                            			
                            			}else { //regular word without .
                                        	String rev = reverseWord(str);
                                        	sb.append(rev).append(" ");
                            			}  
                            }else{ //for english & signs
                                        sb.append(words[i]).append(" ");                                  
                            }
    
                }
                sb.setLength(sb.length() - 1);  // Strip trailing space
                return sb.toString();
    }

    
    public static String reverseSentenceAndReverseWordIfNeeded(String[] words,String delimiter) {
    	
    	StringBuilder sb = new StringBuilder();    	
        for (int j = words.length - 1; j >= 0; j--) {
			

            if (isAnyLetterInHebrew(words[j])) { //if hebrew oposite only the word inside the .
            	String rev = reverseWord(words[j]);
            	sb.append(rev);
            }else { //english/number word
            	sb.append(words[j]);
            }
            
            if (j>0) {
            	sb.append(delimiter);
            }
			
		}
        return sb.toString();
    }
    
    public static String concatSentenceAndReverseWordIfNeeded(String[] words,String delimiter) {
    	
    	StringBuilder sb = new StringBuilder();    	
    	for (int j = 0; j < words.length; j++) {

            if (isAnyLetterInHebrew(words[j])) { //if hebrew oposite only the word inside the .
            	String rev = reverseWord(words[j]);
            	sb.append(rev);
            }else { //english/number word
            	sb.append(words[j]);
            }
            
            if (j<(words.length-1)) {
            	sb.append(delimiter);
            }
			
		}
        return sb.toString();
    }    
    
    
    public static  String reverseWord(String word) {
		StringBuilder rev = new StringBuilder(word);
        rev.reverse();
        return rev.toString();
    }

    public static  boolean isAnyLetterInHebrew(String text) {
    	boolean b = !isContainsOnlyEnglishAndNumbersExt(text);
    	return b;
    }
    
    public static  boolean isContainsOnlyEnglishAndNumbersExt(String text) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9-_:;().'\",!@#$%^&?*\\\\/. |]+$");
        boolean b = pattern.matcher(text).matches();
        return b;
    }

    public static  boolean isContainsOneLetterHebrew(String[] words) {
    	boolean isContainsOneLetterHebrew= false;
    	for (String word : words) {
			if (!isContainsOnlyEnglishAndNumbersExt(word)) {
				return true;
			}
		}
    	return isContainsOneLetterHebrew;
    }
    
    
    public static String cleanAndReverseFieldDesc(String str){
	 
	     str = cleanField(str);
	  	 // for hebrew text
	     
	     return reverseHebrewText(str);	     
    }
    
    public static String getAttributeOfLine(String line, String attribute){
		 String[] elements = line.split(attribute);
		 if (elements==null){
			 return null;
		 }
		 if (elements.length<=1){
			 return null;
		 }
		 String value = elements[1].trim();
		 value = value.replaceAll("\"", "");
		 return value;
		 
	}	

    public static String getAttributeOfLineWithoutQuates(String line, String attribute){
		 String[] elements = line.split(attribute);
		 if (elements==null){
			 return null;
		 }
		 if (elements.length<=1){
			 return null;
		 }
		 String value = elements[1].trim();
		 return value;
		 
	}	
    
    /**
     * print the value using optional
     * @param obj
     * @return
     */
    public static String printObjectValue(Object obj){
    	
    	Optional<Object> op = Optional.ofNullable(obj);
    	if (op.isPresent()){
    		return (String) op.get().toString();
    	}
    	return (String) op.orElse(null);
    	
    }
    
    public static String getDateTime(){
    	Date currentDate = new Date();
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
    	String dateTimeFormat = dateTimeFormatter.format(currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    	return dateTimeFormat;
    	
    }

    
    public static List<String> stringArrToStringList(String[] arrList){
    	List<String> list= Arrays.stream(arrList).collect(Collectors.toList());
    	return list;
    	
    }

    public static List<Object> objectArrToObjectList(Object[] arrList){
    	if (arrList==null) {
    		return null;
    	}
    	List<Object> list= Arrays.stream(arrList).collect(Collectors.toList());
    	return list;
    	
    }
    
    
    
    public static String printArrayList(List<String> list){
    	StringBuilder sb = new StringBuilder();
    	list.forEach(item-> { sb.append(item).append("\n"); });
    	String output = sb.toString();
    	return output;
    	
    }
    
   public static int countLineLevelByPipes(String _line)
    {
          StringTokenizer strTok = new StringTokenizer(_line, "|");
          int pipes = strTok.countTokens();
          if (!_line.contains("|")) {
        	  pipes=0;
          }
          return pipes;
    }
    

    
   	public static Boolean sendToFile(String filePath,String str) {
		Boolean isSuccess = false;
   		try {
   				//do not append, create only
				BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
				writer.append(str);
				writer.close();
				isSuccess = true;
		} catch (IOException e) {
			System.err.println("file not sound:"+filePath);
		}
   		return isSuccess;
   	}
   	
   	public static int getRandomNumber() {
   		return getRandomNumber(1000);
   	}

   	public static int getRandomNumber(int maxNum) {
		int rand = new Random().nextInt(maxNum);
		return rand;
   	}

   	public static String getDateAsString() { 
   		//hours comes 0-23. need to increase by hour
   		LocalDateTime nowDate = LocalDateTime.now().plusHours(1L);
   		return getDateAsString(nowDate);
   	}

   	public static String getDateAsStringUsingPattern(String pattern) {
   		//hours comes 0-23.need to increase by hour
   		LocalDateTime currentTime = LocalDateTime.now();//.plusHours(1L); 
		DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern(pattern);
		String currentTimeStr = currentTime.format(dateTimeFormatter);
		return currentTimeStr;
   	}
   	
   	public static String getDateAsString(LocalDateTime currentTime) {
		DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String currentTimeStr = currentTime.format(dateTimeFormatter);
		return currentTimeStr;
   	}
   	
   	public static String getDateAsString(Date date) {
   		if (date==null) return null;
   		LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
   		return getDateAsString(ldt);
   	}

   	
   	public static Date getDate(String dateTimeStr,String pattern) {
   		if (dateTimeStr==null) return null;
   		System.out.println("date:"+dateTimeStr);
   		//dateTimeStr = dateTimeStr.replace("T", " ").replace("Z", " ");
		DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern(pattern);
//		DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
   		LocalDateTime ldt = LocalDateTime.parse(dateTimeStr, dateTimeFormatter);
   		Date outDate = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
   		return outDate;
   	}
   	
   	public static Date getDate(String dateTimeStr) {
   		if (dateTimeStr==null) return null;
   		//dateTimeStr = dateTimeStr.replace("T", " ").replace("Z", " ");
		DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
   		LocalDateTime ldt = LocalDateTime.parse(dateTimeStr, dateTimeFormatter);
   		Date outDate = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
   		return outDate;
   	}
   	
	public static String getStringBetween(String str,String strFrom,String strTo) {
		Pattern p = Pattern.compile(strFrom+"(.*?)"+strTo);
		Matcher matcher = p.matcher(str);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

	public static String getFromString(String str,String strFrom) {
		Pattern p = Pattern.compile(strFrom);
		Matcher matcher = p.matcher(str);
		if (matcher.find()) {
			return str.substring(matcher.end());
		}
		return null;
	}

	public static String getFromStartToString(String str,String strTo) {
		return getStringBetween(str,"^",strTo);
	}
	
	public static boolean hasMatch(String str,String patternMatch) {
		Pattern p = Pattern.compile(patternMatch);
		Matcher matcher = p.matcher(str);
		return matcher.find();
	}

    public static boolean createFolder(String dir){
    	File f = new File(dir);
    	if (!f.exists()) {
    		boolean b=f.mkdir();
    		return b;
    	}
    	return false;
    }

    public static boolean createEmptyFile(String fullFileName){
    	Path path = Paths.get(fullFileName);
    	try (BufferedWriter writer = Files.newBufferedWriter(path,Charset.defaultCharset())){ //override if exists
    	}catch (IOException ioe)	{
    		System.err.format("IOException: %s%n",ioe);
    		return false;
    	}
    	return true;
    	
    }
    
    public static List<String> listElementToLowerCase(List<String> list){
    	if (list==null) {
    		return null;
    	}
    	List<String> newList = list.stream().map(String::trim).map(String::toLowerCase).collect(Collectors.toList());
    	return newList;
    }
    
    public static List<String> listElementToUpperCase(List<String> list){
    	if (list==null) {
    		return null;
    	}
    	List<String> newList = list.stream().map(String::trim).map(String::toUpperCase).collect(Collectors.toList());
    	return newList;
    }
    
	public static String[] stringToStringArr(String str) {
		return stringToStringArr(str,",");
		
	}
    

	public static String[] stringToStringArr(String str, String delimiter) {

		String[] strArr = {};
		if (org.apache.commons.lang3.StringUtils.isEmpty(str)){
			return strArr;
		}
		String[] strArrTemp = str.split("\\s*"+delimiter+"\\s*"); 
		strArr= org.apache.commons.lang3.StringUtils.stripAll(strArrTemp); //remove trims
		return strArr;
		
	}

	public static Object jsonToObject(String jsonStr, Class c) {
		Object oc = null;
		 try {
			oc = new ObjectMapper().readValue(jsonStr,c);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return oc; 
	}
	

   	public static String toJson (Object obj) {
//		ObjectMapper mapper = new ObjectMapper();
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		String jsonStr = null;
	try {
		jsonStr = mapper.writeValueAsString(obj);
	} catch (JsonProcessingException e) {
		e.printStackTrace();
	}
		return jsonStr;
	}

   	
}

