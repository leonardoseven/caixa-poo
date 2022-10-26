package com.adega.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

@Component("convertDate")
public class ConvertDate {

	
	public static String LocalDateTimeToString(LocalDateTime dateTime, String Format) {
		if(dateTime==null) return null;
		return dateTime.format(DateTimeFormatter.ofPattern(Format));
	}
	
	public static LocalDateTime StringToLocalDateTime(String dateTime, String Format) {
		if(dateTime==null) { 
			return null;
		}
		
		if(Format.contains("hh:mm")) {
			Format = Format.replaceAll("hh:mm", "kk:mm");
		}
		
		return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(Format));
	}
	
	public static long StringToSeconds(String time) {
		if(time.length() < 8) {
			return -999999999;
		}
		Long s = Long.valueOf(time.substring(time.lastIndexOf(":") + 1, time.length()));
		Long m = Long.valueOf(time.substring(time.indexOf(":") + 1 , time.lastIndexOf(":"))) * 60;
		Long h = Long.valueOf(time.substring(0, time.indexOf(":"))) * 3600;
		return s + m + h;
	}
	
	public static String SecondsToString(long time) {
		
		if(time <= 0) return "00:00:00";
		long lngHours = time /3600;
		String strHours = String.valueOf(lngHours);
		long lngMinutes = (time % 3600) / 60;
		String strMunites = String.valueOf(lngMinutes);
		long lngSeconds = (time % 3600) % 60;
		String strSeconds = String.valueOf(lngSeconds);
		
		strMunites = (strMunites.length() < 2 ? "0" + strMunites : strMunites);
		strSeconds = (strSeconds.length() < 2 ? "0" + strSeconds : strSeconds);
		
		return strHours + ":" + strMunites + ":" + strSeconds;
	}
	
	
	public static long diferentLocalDateTimeToLong(LocalDateTime begin, LocalDateTime end) {
		ZoneId zoneId = ZoneId.systemDefault();
		long timeBegin = begin.atZone(zoneId).toEpochSecond();
		long timeEnd = end.atZone(zoneId).toEpochSecond();
		long milisegunds = timeEnd - timeBegin ; 
		return milisegunds;
	}
	
	public static long LocalDateTimeToLong(LocalDateTime date) {
		ZoneId zoneId = ZoneId.systemDefault();
		return  date.atZone(zoneId).toEpochSecond();
	}
	
	public static LocalDateTime LongToLocalDateTime(long time) {
		 return LocalDateTime.ofInstant(Instant.ofEpochMilli(time),TimeZone.getDefault().toZoneId());  
	}

}
