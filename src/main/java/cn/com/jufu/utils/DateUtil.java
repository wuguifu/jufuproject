package cn.com.jufu.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class DateUtil {

	/**
	 * 转换评论的日期，如果传入字符串的格式是包含在方法声明的非标准格式的时间类型中则直接调用该方法即可获取标准的时间格式字符串
	 * 如果没有包含则在该方法中添加对需要处理的时间字符串的处理
	 * @param sDate:转换前的非标准格式的日期字符串
	 * @param crawlDate：当前时间
	 * @return
	 */
	public static String conevertCommentDateStr(String sDate, Date crawlDate) {

		Date hasYearDate = null;
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//标准格式
		SimpleDateFormat df = new SimpleDateFormat("MM月dd日HH:mm");//非标准格式时间类型
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");//非标准格式时间类型
		SimpleDateFormat df3 = new SimpleDateFormat("MM-dd HH:mm");//非标准格式时间类型
		SimpleDateFormat df4 = new SimpleDateFormat("yyyy年MM月dd日HH:mm");//非标准格式时间类型，2014年02月17日 21:30
		SimpleDateFormat df5 = new SimpleDateFormat("yyyy-MM-dd HH:mm");//非标准格式时间类型，2014-02-17 09:13
		SimpleDateFormat df6 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");//非标准格式时间类型，2014年02月14日 18:25
		SimpleDateFormat df7 = new SimpleDateFormat("yyyy.MM.dd HH:mm");//非标准格式时间类型，2014.02.14 18:25
		SimpleDateFormat df8 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//非标准格式时间类,2014/02/24 13:50:04
		SimpleDateFormat df9 = new SimpleDateFormat("yyyy/MM/dd HH:mm");//非标准格式时间类,2014/02/25 04:26
		//标准格式
		try {
			hasYearDate = df1.parse(sDate);
		} catch (ParseException e) {

		}
		if (hasYearDate != null) {
			return sDate;
		}
		//2014年02月17日 21:30
		try {
			hasYearDate = df4.parse(sDate);
		} catch (ParseException e) {

		}
		if (hasYearDate != null) {
			// 当前系统的年份
			Calendar ca = Calendar.getInstance();
			ca.setTime(hasYearDate);
			return df1.format(ca.getTime());
		}
		//2014/02/25 04:26
		try {
			hasYearDate = df9.parse(sDate);
		} catch (ParseException e) {

		}
		if (hasYearDate != null) {
			Calendar ca = Calendar.getInstance();
			ca.setTime(hasYearDate);
			return df1.format(ca.getTime());
		}
		//2014.02.14 18:25
		try {
			hasYearDate = df7.parse(sDate);
		} catch (ParseException e) {

		}
		if (hasYearDate != null) {
			// 当前系统的年份
			Calendar ca = Calendar.getInstance();
			ca.setTime(hasYearDate);
			return df1.format(ca.getTime());
		}
		//2014/02/24 13:50:04
		try {
			hasYearDate = df8.parse(sDate);
		} catch (ParseException e) {

		}
		if (hasYearDate != null) {
			// 当前系统的年份
			Calendar ca = Calendar.getInstance();
			ca.setTime(hasYearDate);
			return df1.format(ca.getTime());
		}
		//非标准格式时间类型，2014年02月14日 18:25
		try {
			hasYearDate = df6.parse(sDate);
		} catch (ParseException e) {

		}
		if (hasYearDate != null) {
			// 当前系统的年份
			Calendar ca = Calendar.getInstance();
			ca.setTime(hasYearDate);
			return df1.format(ca.getTime());
		}
		//2014-02-17 09:13
		try {
			hasYearDate = df5.parse(sDate);
		} catch (ParseException e) {

		}
		if (hasYearDate != null) {
			// 当前系统的年份
			Calendar ca = Calendar.getInstance();
			ca.setTime(hasYearDate);
			return df1.format(ca.getTime());
		}

		//没有年份的：例如（5月20日13:30）
		Date noneYearDate = null;
		try {
			noneYearDate = df.parse(sDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		}
		if (noneYearDate != null) {
			// 当前系统的年份
			Calendar ca = Calendar.getInstance();
			ca.setTime(new java.util.Date());
			int year = ca.get(Calendar.YEAR);
			ca.setTime(noneYearDate);
			ca.set(Calendar.YEAR, year);
			return df1.format(ca.getTime());
		}
		
		//没有年份的：例如02-18 11:16
		Date noneYearDate1 = null;
		try {
			noneYearDate1 = df3.parse(sDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		}
		if (noneYearDate1 != null) {
			// 当前系统的年份
			Calendar ca = Calendar.getInstance();
			ca.setTime(new java.util.Date());
			int year = ca.get(Calendar.YEAR);
			ca.setTime(noneYearDate1);
			ca.set(Calendar.YEAR, year);
			return df1.format(ca.getTime());
		}
		
		// 几分钟前的，例如（55分钟前）
		if (sDate.indexOf("分钟前") > -1) {
			try {
				// 当前系统的年份
				Calendar ca = Calendar.getInstance();
				ca.setTime(crawlDate);

				int minute = Integer.parseInt(sDate.substring(0, sDate
						.lastIndexOf("分钟前")));
				ca.add(Calendar.MINUTE, -minute);
				return df1.format(ca.getTime());
			} catch (Exception e) {

			}
		}
		// 今天的:例如（今天16:38）
		if (sDate.indexOf("今天") > -1) {
			try {
				String today = df2.format(crawlDate);

				return today + " " + sDate.substring(2, sDate.length());

			} catch (Exception e) {

			}
		}
		return "";

	}
	
	/**
	 * 转化新闻页面上的日期格式统一为：yyyy-MM-dd HH:mm:ss
	 * 包含：
	 * yyyy年MM月dd日 HH:mm:ss，yyyy年MM月dd日HH:mm:ss，yyyy年MM月dd日 HH:mm，yyyy年MM月dd日HH:mm，yyyy年MM月dd日，
	 * yyyy-MM-dd HH:mm，yyyy-MM-dd，MM-dd HH:mm:ss，MM-dd HH:mm，MM-dd，
	 * yyyy/MM/dd HH:mm:ss，yyyy/MM/dd HH:mm，yyyy/MM/dd，MM/dd HH:mm:ss，MM/dd HH:mm，MM/dd，
	 * MM月dd日 HH:mm:ss，MM月dd日HH:mm:ss，MM月dd日 HH:mm，MM月dd日HH:mm，MM月dd日，
	 * 几秒前，几分钟前，几小时前，几天前，今天12：12，昨天：12:12，前天12:12，
	 * 时间戳（1392510270000）
	 * @param date
	 * @return
	 */

	public static String changeDateStr(String date){
		SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sd2 = null;
		String s = "";
		try {
			if(date == null || date.isEmpty()){
				return "";
			}
			if(date.contains("年") && date.contains("月") && date.contains("日")){
				if(date.split(":").length==3){
					if(date.contains("\\s")){
						sd2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
						Date d = sd2.parse(date);
						s = sd1.format(d);
					}else if(date.contains(" ")){
						sd2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
						Date d = sd2.parse(date);
						s = sd1.format(d);
					}
					else{
						sd2 = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
						Date d = sd2.parse(date);
						s = sd1.format(d);
					}
				}
				else if(date.split(":").length==2){
					if(date.contains("\\s")){
						sd2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
						Date d = sd2.parse(date);
						s = sd1.format(d);
					}else if(date.contains(" ")){
						sd2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
						Date d = sd2.parse(date);
						s = sd1.format(d);
					}
					else{
						sd2 = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
						Date d = sd2.parse(date);
						s = sd1.format(d);
					}
				}
				else{
					sd2 = new SimpleDateFormat("yyyy年MM月dd日");
					Date d = sd2.parse(date);
					s = sd1.format(d);
				}
				
			}
			if(date.split("-").length == 3){
				if(date.split(":").length == 3){
					s = date;
				}else if(date.split(":").length == 2){
					sd2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					Date d = sd2.parse(date);
					s = sd1.format(d);
				}else{
					sd2 = new SimpleDateFormat("yyyy-MM-dd");
					Date d = sd2.parse(date);
					s = sd1.format(d);
				}
				
			}
			if(date.split("-").length == 2){
				if(date.split(":").length == 3){
					sd2 = new SimpleDateFormat("MM-dd HH:mm:ss");
					Date d = sd2.parse(date);
					Calendar ca = Calendar.getInstance();
					int year = ca.get(Calendar.YEAR);
					ca.setTime(d);
					ca.set(Calendar.YEAR,year);
					s = sd1.format(ca.getTime());
				}else if(date.split(":").length == 2){
					sd2 = new SimpleDateFormat("MM-dd HH:mm");
					Date d = sd2.parse(date);
					Calendar ca = Calendar.getInstance();
					int year = ca.get(Calendar.YEAR);
					ca.setTime(d);
					ca.set(Calendar.YEAR,year);
					s = sd1.format(ca.getTime());
				}else{
					sd2 = new SimpleDateFormat("MM-dd");
					Date d = sd2.parse(date);
					Calendar ca = Calendar.getInstance();
					int year = ca.get(Calendar.YEAR);
					ca.setTime(d);
					ca.set(Calendar.YEAR,year);
					s = sd1.format(ca.getTime());
				}
				
			}
			if(date.split("/").length == 3){
				if(date.split(":").length == 3){
					sd2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date d = sd2.parse(date);
					s = sd1.format(d);
				}else if(date.split(":").length == 2){
					sd2 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
					Date d = sd2.parse(date);
					s = sd1.format(d);
				}else{
					sd2 = new SimpleDateFormat("yyyy/MM/dd");
					Date d = sd2.parse(date);
					s = sd1.format(d);
				}
			}
			if(date.split("/").length == 2){
				if(date.split(":").length == 3){
					sd2 = new SimpleDateFormat("MM/dd HH:mm:ss");
					Date d = sd2.parse(date);
					Calendar ca = Calendar.getInstance();
					int year = ca.get(Calendar.YEAR);
					ca.setTime(d);
					ca.set(Calendar.YEAR,year);
					s = sd1.format(ca.getTime());
				}else if(date.split(":").length == 2){
					sd2 = new SimpleDateFormat("MM/dd HH:mm");
					Date d = sd2.parse(date);
					Calendar ca = Calendar.getInstance();
					int year = ca.get(Calendar.YEAR);
					ca.setTime(d);
					ca.set(Calendar.YEAR,year);
					s = sd1.format(ca.getTime());
				}else{
					sd2 = new SimpleDateFormat("MM/dd");
					Date d = sd2.parse(date);
					Calendar ca = Calendar.getInstance();
					int year = ca.get(Calendar.YEAR);
					ca.setTime(d);
					ca.set(Calendar.YEAR,year);
					s = sd1.format(ca.getTime());
				}
				
			}
			if(date.indexOf("年")<0 && date.contains("月") && date.contains("日")){
				if(date.split(":").length==3){
					if(date.contains("\\s")){
						sd2 = new SimpleDateFormat("MM月dd日 HH:mm:ss");
						Date d = sd2.parse(date);
						Calendar ca = Calendar.getInstance();
						int year = ca.get(Calendar.YEAR);
						ca.setTime(d);
						ca.set(Calendar.YEAR,year);
						s = sd1.format(ca.getTime());
					}else if(date.contains(" ")){
						sd2 = new SimpleDateFormat("MM月dd日 HH:mm:ss");
						Date d = sd2.parse(date);
						Calendar ca = Calendar.getInstance();
						int year = ca.get(Calendar.YEAR);
						ca.setTime(d);
						ca.set(Calendar.YEAR,year);
						s = sd1.format(ca.getTime());
					}
					else{
						sd2 = new SimpleDateFormat("MM月dd日HH:mm:ss");
						Date d = sd2.parse(date);
						Calendar ca = Calendar.getInstance();
						int year = ca.get(Calendar.YEAR);
						ca.setTime(d);
						ca.set(Calendar.YEAR,year);
						s = sd1.format(ca.getTime());
					}
				}
				else if(date.split(":").length==2){
					if(date.contains("\\s")){
						sd2 = new SimpleDateFormat("MM月dd日 HH:mm");
						Date d = sd2.parse(date);
						Calendar ca = Calendar.getInstance();
						int year = ca.get(Calendar.YEAR);
						ca.setTime(d);
						ca.set(Calendar.YEAR,year);
						s = sd1.format(ca.getTime());
					}else if(date.contains(" ")){
						sd2 = new SimpleDateFormat("MM月dd日 HH:mm");
						Date d = sd2.parse(date);
						Calendar ca = Calendar.getInstance();
						int year = ca.get(Calendar.YEAR);
						ca.setTime(d);
						ca.set(Calendar.YEAR,year);
						s = sd1.format(ca.getTime());
					}
					else{
						sd2 = new SimpleDateFormat("MM月dd日HH:mm");
						Date d = sd2.parse(date);
						Calendar ca = Calendar.getInstance();
						int year = ca.get(Calendar.YEAR);
						ca.setTime(d);
						ca.set(Calendar.YEAR,year);
						s = sd1.format(ca.getTime());
					}
				}
				else{
					sd2 = new SimpleDateFormat("MM月dd日");
					Date d = sd2.parse(date);
					Calendar ca = Calendar.getInstance();
					int year = ca.get(Calendar.YEAR);
					ca.setTime(d);
					ca.set(Calendar.YEAR,year);
					s = sd1.format(ca.getTime());
				}
				
			}
			
			
			if(date.indexOf("秒前") > -1){
				Calendar ca = Calendar.getInstance();
				int second = Integer.parseInt(date.substring(0, date.indexOf("秒前")));
				ca.add(Calendar.SECOND, -second);
				s = sd1.format(ca.getTime());
			}
			if(date.indexOf("分钟前") > -1){
				Calendar ca = Calendar.getInstance();
				int minute = Integer.parseInt(date.substring(0, date.indexOf("分钟前")));
				ca.add(Calendar.MINUTE, -minute);
				s = sd1.format(ca.getTime());
			}
			if(date.indexOf("小时前") > -1){
				Calendar ca = Calendar.getInstance();
				int hour = Integer.parseInt(date.substring(0, date.indexOf("小时前")));
				ca.add(Calendar.HOUR, -hour);
				s = sd1.format(ca.getTime());
			}
			if(date.indexOf("天前") > -1){
				Calendar ca = Calendar.getInstance();
				int day = Integer.parseInt(date.substring(0, date.indexOf("天前")));
				ca.add(Calendar.DAY_OF_MONTH, -day);
				s = sd1.format(ca.getTime());
			}
			if(date.indexOf("今天")>-1){
				Calendar ca = Calendar.getInstance();
				String time = date.substring(2, date.length());
				if(time.contains(":") && time.split(":").length ==3){
					sd2 = new SimpleDateFormat("HH:mm:ss");
					Date d = sd2.parse(time);
					int year = ca.get(Calendar.YEAR);
					int month = ca.get(Calendar.MONTH)+1;
					int day = ca.get(Calendar.DAY_OF_MONTH);
					ca.setTime(d);
					ca.set(Calendar.YEAR,year);
					ca.set(Calendar.MONTH,month);
					ca.set(Calendar.DAY_OF_MONTH,day);
					s = sd1.format(ca.getTime());
				}else if(time.contains(":") && time.split(":").length ==2){
					sd2 = new SimpleDateFormat("HH:mm");
					Date d = sd2.parse(time);
					int year = ca.get(Calendar.YEAR);
					int month = ca.get(Calendar.MONTH)+1;
					int day = ca.get(Calendar.DAY_OF_MONTH);
					ca.setTime(d);
					ca.set(Calendar.YEAR,year);
					ca.set(Calendar.MONTH,month);
					ca.set(Calendar.DAY_OF_MONTH,day);
					s = sd1.format(ca.getTime());
				}
				else if(time.contains("-") && time.split("-").length ==3){
					sd2 = new SimpleDateFormat("HH-mm-ss");
					Date d = sd2.parse(time);
					int year = ca.get(Calendar.YEAR);
					int month = ca.get(Calendar.MONTH)+1;
					int day = ca.get(Calendar.DAY_OF_MONTH);
					ca.setTime(d);
					ca.set(Calendar.YEAR,year);
					ca.set(Calendar.MONTH,month);
					ca.set(Calendar.DAY_OF_MONTH,day);
					s = sd1.format(ca.getTime());
					
				}
				else if(time.contains("-") && time.split("-").length ==2){
					sd2 = new SimpleDateFormat("HH-mm");
					Date d = sd2.parse(time);
					int year = ca.get(Calendar.YEAR);
					int month = ca.get(Calendar.MONTH)+1;
					int day = ca.get(Calendar.DAY_OF_MONTH);
					ca.setTime(d);
					ca.set(Calendar.YEAR,year);
					ca.set(Calendar.MONTH,month);
					ca.set(Calendar.DAY_OF_MONTH,day);
					s = sd1.format(ca.getTime());
				}
				
			}
			if(date.indexOf("昨天")>-1){
				Calendar ca = Calendar.getInstance();
				String time = date.substring(2, date.length());
				if(time.contains(":") && time.split(":").length ==3){
					sd2 = new SimpleDateFormat("HH:mm:ss");
					Date d = sd2.parse(time);
					int year = ca.get(Calendar.YEAR);
					int month = ca.get(Calendar.MONTH)+1;
					int day = ca.get(Calendar.DAY_OF_MONTH);
					ca.setTime(d);
					ca.set(Calendar.YEAR,year);
					ca.set(Calendar.MONTH,month);
					ca.set(Calendar.DAY_OF_MONTH,day-1);
					s = sd1.format(ca.getTime());
				}else if(time.contains(":") && time.split(":").length ==2){
					sd2 = new SimpleDateFormat("HH:mm");
					Date d = sd2.parse(time);
					int year = ca.get(Calendar.YEAR);
					int month = ca.get(Calendar.MONTH)+1;
					int day = ca.get(Calendar.DAY_OF_MONTH);
					ca.setTime(d);
					ca.set(Calendar.YEAR,year);
					ca.set(Calendar.MONTH,month);
					ca.set(Calendar.DAY_OF_MONTH,day-1);
					s = sd1.format(ca.getTime());
				}
				else if(time.contains("-") && time.split("-").length ==3){
					sd2 = new SimpleDateFormat("HH-mm-ss");
					Date d = sd2.parse(time);
					int year = ca.get(Calendar.YEAR);
					int month = ca.get(Calendar.MONTH)+1;
					int day = ca.get(Calendar.DAY_OF_MONTH);
					ca.setTime(d);
					ca.set(Calendar.YEAR,year);
					ca.set(Calendar.MONTH,month);
					ca.set(Calendar.DAY_OF_MONTH,day-1);
					s = sd1.format(ca.getTime());
					
				}
				else if(time.contains("-") && time.split("-").length ==2){
					sd2 = new SimpleDateFormat("HH-mm");
					Date d = sd2.parse(time);
					int year = ca.get(Calendar.YEAR);
					int month = ca.get(Calendar.MONTH)+1;
					int day = ca.get(Calendar.DAY_OF_MONTH);
					ca.setTime(d);
					ca.set(Calendar.YEAR,year);
					ca.set(Calendar.MONTH,month);
					ca.set(Calendar.DAY_OF_MONTH,day-1);
					s = sd1.format(ca.getTime());
				}
				
			}
			if(date.indexOf("前天")>-1){
				Calendar ca = Calendar.getInstance();
				String time = date.substring(2, date.length());
				if(time.contains(":") && time.split(":").length ==3){
					sd2 = new SimpleDateFormat("HH:mm:ss");
					Date d = sd2.parse(time);
					int year = ca.get(Calendar.YEAR);
					int month = ca.get(Calendar.MONTH)+1;
					int day = ca.get(Calendar.DAY_OF_MONTH);
					ca.setTime(d);
					ca.set(Calendar.YEAR,year);
					ca.set(Calendar.MONTH,month);
					ca.set(Calendar.DAY_OF_MONTH,day-2);
					s = sd1.format(ca.getTime());
				}else if(time.contains(":") && time.split(":").length ==2){
					sd2 = new SimpleDateFormat("HH:mm");
					Date d = sd2.parse(time);
					int year = ca.get(Calendar.YEAR);
					int month = ca.get(Calendar.MONTH)+1;
					int day = ca.get(Calendar.DAY_OF_MONTH);
					ca.setTime(d);
					ca.set(Calendar.YEAR,year);
					ca.set(Calendar.MONTH,month);
					ca.set(Calendar.DAY_OF_MONTH,day-2);
					s = sd1.format(ca.getTime());
				}
				else if(time.contains("-") && time.split("-").length ==3){
					sd2 = new SimpleDateFormat("HH-mm-ss");
					Date d = sd2.parse(time);
					int year = ca.get(Calendar.YEAR);
					int month = ca.get(Calendar.MONTH)+1;
					int day = ca.get(Calendar.DAY_OF_MONTH);
					ca.setTime(d);
					ca.set(Calendar.YEAR,year);
					ca.set(Calendar.MONTH,month);
					ca.set(Calendar.DAY_OF_MONTH,day-2);
					s = sd1.format(ca.getTime());
					
				}
				else if(time.contains("-") && time.split("-").length ==2){
					sd2 = new SimpleDateFormat("HH-mm");
					Date d = sd2.parse(time);
					int year = ca.get(Calendar.YEAR);
					int month = ca.get(Calendar.MONTH)+1;
					int day = ca.get(Calendar.DAY_OF_MONTH);
					ca.setTime(d);
					ca.set(Calendar.YEAR,year);
					ca.set(Calendar.MONTH,month);
					ca.set(Calendar.DAY_OF_MONTH,day-2);
					s = sd1.format(ca.getTime());
				}
				
			}
			if(date.matches("\\d{13}")){
				s = sd1.format(new Date(Long.parseLong(date)));
			}
			
		} catch (Exception e) {
			System.out.println("处理时间格式异常,时间为："+date);
			e.printStackTrace();
		}
		return s;
	}
	
	@SuppressWarnings("unused")
	private static Date DTStringtoDate(String dtToDate) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		java.util.Date datetime = formatter.parse(dtToDate, pos);
		java.sql.Timestamp ts = null;

		if (datetime != null) {

			ts = new java.sql.Timestamp(datetime.getTime());
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(df.format(ts));
		return ts;
	}

	public static boolean isBefore(Date date1, Date date2) {
		if (date1.getTime() > date2.getTime()) {

			return false;
		} else {
			return true;
		}
	}

	public static boolean isAfter(Date date1, Date date2) {
		if (date1.getTime() < date2.getTime()) {

			return false;
		} else {
			return true;
		}
	}

	/**
	 * 格式化日期
	 * 
	 * @param timeMillis
	 * @param formatType
	 *            0:yyyy-MM-dd 1:yyyy-MM-dd HH:mm 2:MM月dd日HH:mm 3:yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String dateFormat(long timeMillis, int formatType) {
		String val = "";
		try {
			String dateFormate = null;
			switch (formatType) {
			case 1:
				dateFormate = "yyyy-MM-dd HH:mm";
				break;
			case 2:
				dateFormate = "MM月dd日HH:mm";
				break;
			case 3:
				dateFormate = "yyyy-MM-dd HH:mm:ss";
				break;
			default:
				dateFormate = "yyyy-MM-dd";
				break;
			}

			SimpleDateFormat df = new SimpleDateFormat(dateFormate);
			val = df.format(new Date(timeMillis)).toString();
		} catch (Exception e) {
			val = Long.toString(timeMillis);
		}
		return val;
	}
	
	/**
	 *  当天时间之前的时间
	 * @param timeMillis
	 * @return
	 */
	public static boolean isBeforetoday(long timeMillis)
	{
		// 当天时间的开始  当天00:00:00
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);

		return timeMillis < todayStart.getTime().getTime() ? true:false;
	}
	
	/**
	 * 获取与当前时间间隔的时间
	 * @param interval
	 * @return
	 */
	public static String getIntervalCurrentTime(int interval){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, interval); 
		return format.format(calendar.getTime());
	}
	
	/**
	 * 获取一个i位的随机数字
	 * @param i
	 * @return
	 */
	public static String getRandom(int i)
	{
		Random jjj = new Random();

		if (i == 0)
			return "";
		String jj = "";
		for (int k = 0; k < i; k++)
		{
			jj = jj + jjj.nextInt(9);
		}
		return jj;
	}
	

	public static Date parseDate(String dateStr) {
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return df1.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当前日期的字符串格式
	 * @return
	 */
	public static String getCurrentDateString()
	{
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return df1.format(date);
	}
	
	/**
	 * 获取当前时间
	 */
	public static String getCurrentTimeString()
	{
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return df1.format(date);
	}
	
	/**
	 * 获取当前时间
	 */
	public static String getCurrentTimeFormatString()
	{
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date date = new Date();
		return df1.format(date);
	}
	
	/**
	 * 获取昨天日期
	 * @return
	 */
	public static String getYesterdayString()
	{
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(System.currentTimeMillis()-86400000);
		return df1.format(date);
	}
	
	/**
	 * 获取几天前的日期
	 * @param befort
	 * @return
	 */
	public static String getBefortDateString(int befort)
	{
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(System.currentTimeMillis()-(86400000*befort));
		return df1.format(date);
	} 
	
	public static String dd() throws IOException
	{
		Runtime runtime = Runtime.getRuntime();
		String cmdString = "";
		runtime.exec(cmdString);
		return null;
	}
	/**
	 * 获取时间的数字
	 * @param timpstepp
	 * @return
	 */
	public static int getCurrentDateHour(long timpstepp)
	{
		SimpleDateFormat df = new SimpleDateFormat("HH");
		
		String str = df.format(new Date(timpstepp));
		if(str.startsWith("0"))
			str = str.substring(1, str.length());
		
		return Integer.parseInt(str);
	}
	/**
	 * 获取小时
	 * @param timpstepp
	 * @return
	 */
	public static int getCurrentHour()
	{
		SimpleDateFormat df = new SimpleDateFormat("HH");
		
		String str = df.format(new Date());
		if(str.startsWith("0"))
			str = str.substring(1, str.length());
		
		return Integer.parseInt(str);
	} 
	
	/**
	 * 获取现在时间
	 * 
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static Date getNowDate()
	{
		setTimeZone();
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 
	 * @return yyyy-MM-dd 获取现在时间
	 * @return返回短时间格式 yyyy-MM-dd
	 */
	public static Date getNowDateShort()
	{
		setTimeZone();
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate()
	{
		setTimeZone();

		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getStringDateShort()
	{
		setTimeZone();
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd HH:MM
	 */
	public static String getStringDateShortmm()
	{
		setTimeZone();
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取时间 小时:分;秒 HH:mm:ss
	 * 
	 * @return
	 */
	public static String getTimeShort()
	{
		setTimeZone();
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate)
	{
		setTimeZone();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(java.util.Date dateDate)
	{
		setTimeZone();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 * 
	 * @param dateDate
	 * @param k
	 * @return
	 */
	public static String dateToStr(java.util.Date dateDate)
	{
		setTimeZone();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate)
	{
		setTimeZone();
		SimpleDateFormat formatter;
		if (strDate == null || "".equals(strDate))
			return null;
		if (strDate.indexOf("-") > 0)
			formatter = new SimpleDateFormat("yyyy-MM-dd");
		else
			formatter = new SimpleDateFormat("dd/MM/yyyy");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 得到现在时间
	 * 
	 * @return
	 */
	public static Date getNow()
	{
		setTimeZone();
		Date currentTime = new Date();
		return currentTime;
	}

	/**
	 * 提取一个月中的最后一天
	 * 
	 * @param day
	 * @return
	 */
	public static Date getLastDate(long day)
	{
		setTimeZone();
		Date date = new Date();
		long date_3_hm = date.getTime() - 3600000 * 34 * day;
		Date date_3_hm_date = new Date(date_3_hm);
		return date_3_hm_date;
	}

	/**
	 * 得到现在时间
	 * 
	 * @return 字符串 yyyyMMdd HHmmss
	 */
	public static String getStringToday()
	{
		setTimeZone();
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 得到现在时间
	 * 
	 * @return 字符串 yyyyMMdd HH:mm:ss
	 */
	public static String getStringTodayA()
	{
		setTimeZone();
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	/**
	 * 得到今天的时间的短日期形如：110425
	 * @return
	 */
	public static String getShortStringToday()
	{
	    setTimeZone();
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyMM");
        String dateString = formatter.format(currentTime);
        return dateString;
	}
	
	   /**
     * 得到今天的时间的短日期形如：110425
     * @return
     */
    public static String getNowDateStr()
    {
        setTimeZone();
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
	
	/**
	 * 得到现在时间
	 * 
	 * @return String 字符串 yyyyMMddHHmmss毫秒
	 */
    public static String getStringTodayB()
    {
        return getStringTodayA() + Calendar.getInstance().get(Calendar.MILLISECOND);
    }

	/**
	 * 得到现在小时
	 */
	public static String getHour()
	{
		setTimeZone();
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String hour;
		hour = dateString.substring(11, 13);
		return hour;
	}

	/**
	 * 得到现在分钟
	 * 
	 * @return
	 */
	public static String getTime()
	{
		setTimeZone();
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String min;
		min = dateString.substring(14, 16);
		return min;
	}

	/**
	 * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
	 * 
	 * @param sformat
	 *            yyyyMMddhhmmss
	 * @return
	 */
	public static String getUserDate(String sformat)
	{
		setTimeZone();
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(sformat);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟 st1 要大于 st2才能返回正确值
	 */
	public static String getTwoHour(String st1, String st2)
	{
		String[] kk = null;
		String[] jj = null;
		kk = st1.split(":");
		jj = st2.split(":");
		if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
			return "0";
		else
		{
			double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1])
					/ 60;
			double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1])
					/ 60;
			if ((y - u) > 0)
				return y - u + "";
			else
				return "0";
		}
	}

	/**
	 * 得到二个日期间的间隔天数 sj1>sj2 返回正数
	 */
	public static String getTwoDay(String sj1, String sj2)
	{
		setTimeZone();
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try
		{
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e)
		{
			return "";
		}
		return day + "";
	}

	/**
	 * 时间前推或后推分钟,其中JJ表示分钟.
	 */
	public static String getPreTime(String sj1, String jj)
	{
		setTimeZone();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mydate1 = "";
		try
		{
			Date date1 = format.parse(sj1);
			long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
			date1.setTime(Time * 1000);
			mydate1 = format.format(date1);
		} catch (Exception e)
		{
		}
		return mydate1;
	}

	/**
	 * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数 负数是往前推，正数是往后延
	 */
	public static String getNextDay(String nowdate, String delay)
	{
		setTimeZone();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String mdate = "";
		Date d = strToDate(nowdate);
		long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60
				* 60;
		d.setTime(myTime * 1000);
		mdate = format.format(d);
		return mdate;
	}

	/**
	 * 判断是否润年
	 * 
	 * @param ddate
	 * @return
	 */
	public static boolean isLeapYear(String ddate)
	{

		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */
		Date d = strToDate(ddate);
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(d);
		int year = gc.get(Calendar.YEAR);
		if ((year % 400) == 0)
			return true;
		else if ((year % 4) == 0)
		{
			if ((year % 100) == 0)
				return false;
			else
				return true;
		} else
			return false;
	}

	/**
	 * 返回美国时间格式 26 Apr 2006
	 * 
	 * @param str
	 * @return
	 */
	public static String getEDate(String str)
	{
		setTimeZone();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(str, pos);
		String j = strtodate.toString();
		String[] k = j.split(" ");
		return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
	}

	/**
	 * 获取一个月的最后一天
	 * 
	 * @param dat
	 * @return
	 */
	public static String getEndDateOfMonth(String dat)
	{// yyyy-MM-dd
		String str = dat.substring(0, 8);
		String month = dat.substring(5, 7);
		int mon = Integer.parseInt(month);
		if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8
				|| mon == 10 || mon == 12)
		{
			str += "31";
		} else if (mon == 4 || mon == 6 || mon == 9 || mon == 11)
		{
			str += "30";
		} else
		{
			if (isLeapYear(dat))
			{
				str += "29";
			} else
			{
				str += "28";
			}
		}
		return str;
	}

	/**
	 * 取得指定月份的第一天
	 * 
	 * @param strdate
	 *            String
	 * @return String
	 */
	public static String getMonthBegin(String dat)
	{
		String str = dat.substring(0, 8);
		return str + "01";
	}

	/**
	 * 判断二个时间是否在同一个周
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameWeekDates(Date date1, Date date2)
	{
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		if (0 == subYear)
		{
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
					.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (1 == subYear && 11 == cal2.get(Calendar.MONTH))
		{
			// 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
					.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH))
		{
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
					.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		return false;
	}

	/**
	 * 产生周序列,即得到当前时间所在的年度是第几周
	 * 
	 * @return
	 */
	public static String getSeqWeek()
	{
		Calendar c = Calendar.getInstance(Locale.CHINA);
		String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
		if (week.length() == 1)
			week = "0" + week;
		// String year = Integer.toString(c.get(Calendar.YEAR));
		return week;
	}

	/**
	 * 根據傳入的一個日期，判斷這個日期所在的周是年度第幾周
	 * 
	 * @param day
	 * @return
	 */
	public static String getSeqWeek(String day)
	{
		Calendar c = Calendar.getInstance(Locale.CHINA);
		c.setTime(strToDate(day));
		String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
		if (week.length() == 1)
			week = "0" + week;
		// String year = Integer.toString(c.get(Calendar.YEAR));
		return week;
	}

	/**
	 * 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
	 * 
	 * @param sdate
	 * @param num
	 * @return
	 */
	public static String getWeek(String sdate, String num)
	{
		// 再转换为时间
		Date dd = DateUtil.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(dd);
		if (num.equals("1")) // 返回星期一所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		else if (num.equals("2")) // 返回星期二所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		else if (num.equals("3")) // 返回星期三所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		else if (num.equals("4")) // 返回星期四所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		else if (num.equals("5")) // 返回星期五所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		else if (num.equals("6")) // 返回星期六所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		else if (num.equals("0")) // 返回星期日所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}

	/**
	 * 根据一个日期，返回是星期几的字符串
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getWeek(String sdate)
	{
		// 再转换为时间
		Date date = DateUtil.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	/**
	 * 两个时间之间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2)
	{
		setTimeZone();
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try
		{
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e)
		{
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 形成如下的日历 ， 根据传入的一个时间返回一个结构 星期日 星期一 星期二 星期三 星期四 星期五 星期六 下面是当月的各个时间
	 * 此函数返回该日历第一行星期日所在的日期
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getNowMonth(String sdate)
	{
		// 取该时间所在月的一号
		sdate = sdate.substring(0, 8) + "01";
		// 得到这个月的1号是星期几
		Date date = DateUtil.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int u = c.get(Calendar.DAY_OF_WEEK);
		String newday = DateUtil.getNextDay(sdate, (1 - u) + "");
		return newday;
	}

	public static String[][] getCal(String sdate)
	{
		String smon = sdate.substring(5, 7);// 月份
		// 月份第一天
		String firstday = sdate.substring(0, 8) + "01";
		// 月份第一天对应所在周的第一天,周日日期
		String sone = getNowMonth(sdate);

		String endday = DateUtil.getEndDateOfMonth(sdate);
		// 找出本月跨几个周,
		String sweek = DateUtil.getSeqWeek(firstday);
		String eweek = DateUtil.getSeqWeek(endday);
		int kkkk = 0;
		if (Integer.parseInt(eweek) < Integer.parseInt(sweek))
		{
			// 表示是跨年度,这时需要用年度总周次来减前一周,再加1
			endday = DateUtil.getNextDay(endday, "-7"); // 往前推一个周
			eweek = DateUtil.getSeqWeek(endday);
			kkkk = 1;
		}
		if (kkkk == 1)
		{
			kkkk = Integer.parseInt(eweek) - Integer.parseInt(sweek) + 2;
		} else
			kkkk = Integer.parseInt(eweek) - Integer.parseInt(sweek) + 1;
		String[][] sp = new String[kkkk][7];
		// 第一层循环,跨周次
		for (int i = 1; i <= kkkk; i++)
		{
			// String tmp="";
			for (int k = 0; k < 7; k++)
			{
				// 循环当前日期
				String sc = getNextDay(sone, ((i - 1) * 7 + k) + "");
				if (smon.equals(sc.substring(5, 7)))
				{
					sp[i - 1][k] = sc.substring(8, 10);
				} else
				{
					sp[i - 1][k] = "&nbsp;";
				}
			}

		}
		return sp;
	}

	/**
	 * 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
	 * 
	 * 表示是取几位随机数，可以自己定
	 */

	public static String getNo(int k)
	{
		return getUserDate("yyyyMMddHHmmss") + getRandom(k);
	} 

	/**
	 * 求下个月的10号
	 */
	public static String getNextMonthDay(String sdate, int m)
	{
		int year = Integer.parseInt(sdate.substring(0, 4));
		int month = Integer.parseInt(sdate.substring(5, 7));
		month = month + m;
		if (month < 0)
		{
			month = month + 12;
			year = year - 1;
		} else if (month > 12)
		{
			month = month - 12;
			year = year + 1;
		} else if (month == 0)
		{
			year = year - 1;
			month = 12;
		}
		String smonth = "";
		if (month < 10)
			smonth = "0" + month;
		else
			smonth = "" + month;
		return year + "-" + smonth + "-10";
	}

	/**
	 * 將一個整數轉換為一個類型(主要是長度)的字符串,不足位在前面補0
	 * 
	 * @param k
	 *            要轉換的整數
	 * @param le
	 *            要轉換成的格式
	 * @return
	 */
	public static String getNumToStr(int k, String le)
	{
		String kk = k + "";
		String ks = k + "";
		if (kk.length() < le.length())
		{
			for (int i = 0; i < (le.length() - ks.length()); i++)
			{
				kk = "0" + kk;
			}
		}
		return kk;
	}

	public static void setTimeZone()
	{
//		if ("WINDOW".equals(Constants.OPTYPE))
//		{
			TimeZone tz = TimeZone.getTimeZone("ETC/GMT-8");
			TimeZone.setDefault(tz);
//		}
	}

	/**
	 * 取得几個月後的同一天,可自動換年
	 * 
	 * @param strDate
	 *            开始时间
	 * @param months
	 *            間隔的月數
	 * @return 處理後的日期，輸出格式yyyy-MM-dd
	 */
	public static String getDateForMonthsLater(String strDate, int months)
	{
		setTimeZone();
		Calendar c = Calendar.getInstance();
		c.setTime(DateUtil.strToDate(strDate));
		c.add(Calendar.MONTH, months);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(c.getTime());
		return dateString;
	}

	// 获取语言时区
	public static Locale getLocal(String local)
	{
		Locale slocal = null;
		if (local == null || "".equals(local))
			local = "ZH_TW";
		if (local.indexOf("_") > 0)
		{
			slocal = new Locale(local.substring(0, 2), local.substring(3, 5));
		} else
		{
			slocal = new Locale(local);
		}
		return slocal;
	}


	// 在JAVA中获取资源文件内容
	public static String getLocalString(String local, String sresource,
			String skey)
	{
		String sp = "";
		Locale slocal = getLocal(local);
		ResourceBundle rb = ResourceBundle.getBundle(sresource, slocal);
		sp = rb.getString(skey);
		return sp;
	}

	
	/**
	 * 当前时间的后几天
	 * getDate(这里用一句话描述这个方法的作用)    
	 * (这里描述这个方法适用条件 – 可选)     
	 * (这里描述这个方法的注意事项 – 可选)       
	 * @param @param dayNon
	 * @param @param format
	 * @param @return     
	 * @return String
	 * @Exception 异常对象
	 */
	public static String getDate(int dayType,int dayNon,String format)
	{
        Calendar cal = Calendar.getInstance();
        cal.add(dayType, dayNon);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(cal.getTime());
	}
	
	/**
	 * 在指点时间上加上几天后的日期字符串
	 * @param addBefore 指定日期
	 * @param dateFormat 默认格式
	 * @param addNumber 添加的天数
	 * @return
	 */
	public static String addDate(String addBefore,String dateFormat,int addNumber)
    {
        Date date = DateUtil.strToDate(addBefore);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, addNumber);
        if(StringUtils.isEmpty(dateFormat))
        {
            dateFormat = "yyyy-MM-dd";
        }
        return new SimpleDateFormat(dateFormat).format(cal.getTime());
    }
	
	public static String addDate2(String addBefore,String dateFormat,int addNumber,int dateType)
    {
        Date date = DateUtil.strToDate(addBefore);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        switch (dateType)
        {
        case Calendar.DATE:
            cal.add(Calendar.DATE, addNumber);
            break;
        case Calendar.MONTH:
            cal.add(Calendar.MONTH, addNumber);
            break;
        default:
            break;
        }
        if(StringUtils.isEmpty(dateFormat))
        {
            dateFormat = "yyyy-MM-dd";
        }
        return new SimpleDateFormat(dateFormat).format(cal.getTime());
    }
	
	/**
	 * 获取指定时间的第几个月后，第几号
	 * @param addBefore
	 * @param dateFormat
	 * @param monthAdd
	 * @param dayAdd
	 * @return
	 */
	public static String addDateOfMonth(String addBefore,String dateFormat,int monthAdd,int dayAdd)
    {

        Date date = DateUtil.strToDate(addBefore);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, monthAdd);
        cal.set(Calendar.DATE,dayAdd);
        if(StringUtils.isEmpty(dateFormat))
        {
            dateFormat = "yyyy-MM-dd";
        }
        return new SimpleDateFormat(dateFormat).format(cal.getTime());
    }
	/**
	 * 单据是否已经超期
	 * isChaoQi(这里用一句话描述这个方法的作用)    
	 * (这里描述这个方法适用条件 – 可选)     
	 * (这里描述这个方法的注意事项 – 可选)       
	 * @param @param startTime
	 * @param @param term
	 * @param @return     
	 * @return boolean
	 * @Exception 异常对象
	 */
	public static boolean isChaoQi(String startTime,int term)
	{
	    int days =(int) DateUtil.getDays(DateUtil.getStringDate(),startTime);
        if(days <= term)
        {
            return false;
        }
        else
        {
            return true;
        }
	}
	
	/**
	 * 单据是否已经到期
	 * @param @param termTime  到期时间
	 * @param @param term
	 * @param @return     
	 * @return boolean
	 * @Exception 异常对象
	 */
	public static boolean isDaoQi(String termTime)
    {
        int days =(int) DateUtil.getDays(DateUtil.getStringDate(),termTime);
        if(days > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
	
    public static String converDateStrToDefineDate(String dateString, String formate)
    {
        if(StringUtils.isEmpty(dateString))
        {
            return "";
        }

        Pattern pat = Pattern.compile("^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}$");
        Matcher m = pat.matcher(dateString.trim());
        boolean b = m.find();
        SimpleDateFormat formatter;
        if(b)
        {
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        else
        {
            formatter = new SimpleDateFormat("yyyy-MM-dd");
        }
        try
        {
            Date date = formatter.parse(dateString);
            SimpleDateFormat resultFormat = new SimpleDateFormat(formate);
            return resultFormat.format(date);
        }
        catch(ParseException e)
        {
            return dateString;
        }
    }

    /**
     * 获取当前日期yyyyMMdd
     */
    public static String getCurrentDate()
    {
    	Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
        calendar.add(Calendar.DATE, 0);    //得到当天
		String  yestedayDate
		= new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
       return yestedayDate;
    }
    
    /**
	 * 格式化日期
	 * 
	 * @param timeMillis
	 * @param formatType
	 *            0:yyyy-MM-dd 1:yyyy-MM-dd HH:mm 2:MM月dd日HH:mm 3:yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String DateFormat(long timeMillis, int formatType) {
		String val = "";
		try {
			String dateFormate = null;
			switch (formatType) {
			case 1:
				dateFormate = "yyyy-MM-dd  HH:mm";
				break;
			case 2:
				dateFormate = "MM月dd日HH:mm";
				break;
			case 3:
				dateFormate = "yyyy-MM-dd HH:mm:ss";
				break;
			default:
				dateFormate = "yyyy-MM-dd";
				break;
			}

			SimpleDateFormat df = new SimpleDateFormat(dateFormate);
			val = df.format(new Date(timeMillis)).toString();
		} catch (Exception e) {
			e.printStackTrace();
			val = Long.toString(timeMillis);
		}
		return val;
	}
	
	/**
	 * 获取时间戳
	 *
	 * EX-LIMIN002 2014-6-23 
	 * 
	 * @return
	 * @return long
	 * @throws
	 */
	public static long getTimeStamp(){
		return new Date().getTime();
	}
	
	public static void main(String[] args){
		/*Calendar rightNow = Calendar.getInstance();
		int hour = rightNow.get(Calendar.HOUR_OF_DAY);
		int minute = rightNow.get(Calendar.MINUTE);
		boolean timeFlag = false;
		//当前时间大于8点10分开始,16点25分结束
		if(hour>=8 && hour<=16){
			if((hour==8&&minute<10)
					||(hour==16&&minute>=25)){
				System.out.println("-------------当前时间为网页不可抓取时间："+hour+":"+minute);
				timeFlag = false;
			}else{
				System.out.println("-------------当前时间为网页可抓取时间："+hour+":"+minute);
				timeFlag = true;
			}
		}else{
			System.out.println("-------------当前时间为网页不可抓取时间："+hour+":"+minute);
			timeFlag = false;
		}
		if(!timeFlag){
			//休眠半小时
			System.out.println("-------------休眠半小时!");
			ThreadUtil.sleepSecord(30*60);
		}*/
		boolean timeFlag = true;
		while(timeFlag){
			Calendar rightNow = Calendar.getInstance();
			int hour = rightNow.get(Calendar.HOUR_OF_DAY);
			int minute = rightNow.get(Calendar.MINUTE);
			//当前时间大于8点10分开始,16点25分结束
			if(hour>=8 && hour<=16){
				if((hour==8&&minute<10)
						||(hour==16&&minute>=25)){
					System.out.println("-------------当前时间为网页不可抓取时间："+hour+":"+minute);
					timeFlag = true;
				}else{
					System.out.println("-------------当前时间为网页可抓取时间："+hour+":"+minute);
					timeFlag = false;
				}
			}else{
				System.out.println("-------------当前时间为网页不可抓取时间："+hour+":"+minute);
				timeFlag = true;
			}
			if(timeFlag){
				
				//休眠半小时
				System.out.println("-------------休眠半小时!");
			}else{
				break;
			}
		}
	}
	public static int getCurrentMinute()
	{
		 Calendar c = Calendar.getInstance();
		 return c.get(Calendar.MINUTE);
	}
	
	/**
	 * 格式化日期字符串
	 *
	 * EX-LIMIN002 2015-1-14 
	 * 
	 * @param dateStr
	 * @param currentFormat 当前日期格式
	 * @param targetFormat	需要格式化目标字符串
	 * @return
	 * @throws
	 */
	public static String formatDate(String dateStr, String currentFormat, String targetFormat){
		String result = dateStr;
		try {
			SimpleDateFormat csdf = new SimpleDateFormat(currentFormat);
			Date date = csdf.parse(dateStr);
			
			SimpleDateFormat tsdf = new SimpleDateFormat(targetFormat);
			result = tsdf.format(date);
		} catch (ParseException e) {
			result = dateStr;
		}
		return result;
	}
	
	/**
	 * 根据格式化后的日期对象获取时间戳
	 *
	 * EX-LIMIN002 2015-2-10 
	 * 
	 * @param dateStr	字符串日期类型
	 * @param format	格式化
	 * @return
	 * @throws
	 */
	public static long getFormatDateLong(String dateStr, String format){
		try{
			SimpleDateFormat csdf = new SimpleDateFormat(format);
			Date date = csdf.parse(dateStr);
			return date.getTime();
		}catch(Exception e){
			return 0;
		}
	}
}
