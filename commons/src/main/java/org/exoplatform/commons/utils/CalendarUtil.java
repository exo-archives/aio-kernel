/***************************************************************************
 * Copyright 2001-2005 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarUtil {
	
	public CalendarUtil() {
	}
	
	public static int getDaysInMonth(Date date) {
		int days = 0;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		int month = gc.get(GregorianCalendar.MONTH) + 1;
		int year = gc.get(GregorianCalendar.YEAR);

		// RETURN 31 DAYS
		if (month == 1
			|| month == 3
			|| month == 5
			|| month == 7
			|| month == 8
			|| month == 10
			|| month == 12) {
			days = 31;
		}
		// RETURN 30 DAYS
		else if (month == 4 || month == 6 || month == 9 || month == 11) {
			days = 30;
		}
		// RETURN 29 DAYS
		else if (month == 2) {
			if (isLeapYear(year)) {
				days = 29;
			}
			// RETURN 28 DAYS
			else {
				days = 28;
			}
		}
		return days;
	}

	//	   CHECK TO SEE IF YEAR IS A LEAP YEAR
	public static boolean isLeapYear(int year) {

		if (((year % 4) == 0) && ((year % 100) != 0) || ((year % 400) == 0)) {
			return (true);
		} else {
			return (false);
		}
	}

	public static int getWeekOfDay(Date date) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		return c.get(GregorianCalendar.DAY_OF_WEEK);
	}

	// TODO : refactor compare methods to take time zone in account
	public static int compareDate(GregorianCalendar gCal1, GregorianCalendar gCal2) {
		if (gCal1.get(GregorianCalendar.YEAR) < gCal2.get(GregorianCalendar.YEAR)) return -1;
		else if (gCal1.get(GregorianCalendar.YEAR) > gCal2.get(GregorianCalendar.YEAR)) return 1;
		if (gCal1.get(GregorianCalendar.MONTH) < gCal2.get(GregorianCalendar.MONTH)) return -1;
		else if (gCal1.get(GregorianCalendar.MONTH) > gCal2.get(GregorianCalendar.MONTH)) return 1;
		if (gCal1.get(GregorianCalendar.DATE) < gCal2.get(GregorianCalendar.DATE)) return -1;
		else if (gCal1.get(GregorianCalendar.DATE) > gCal2.get(GregorianCalendar.DATE)) return 1;

		return 0;
	}

	public static int compareTimeUpToMinute(GregorianCalendar gCal1, GregorianCalendar gCal2) {
		if (gCal1.get(GregorianCalendar.HOUR_OF_DAY) < gCal2.get(GregorianCalendar.HOUR_OF_DAY)) return -1;
		if (gCal1.get(GregorianCalendar.HOUR_OF_DAY) > gCal2.get(GregorianCalendar.HOUR_OF_DAY)) return 1;
		if (gCal1.get(GregorianCalendar.MINUTE) < gCal2.get(GregorianCalendar.MINUTE)) return -1;
		if (gCal1.get(GregorianCalendar.MINUTE) > gCal2.get(GregorianCalendar.MINUTE)) return 1;

		return 0;
	}

	public static int compareDateTimeUpToMinute(GregorianCalendar gCal1, GregorianCalendar gCal2) {
		if (compareDate(gCal1, gCal2) < 0) return -1;
		else if (compareDate(gCal1, gCal2) > 0) return 1;
		if (compareTimeUpToMinute(gCal1, gCal2) < 0) return -1;
		else if (compareTimeUpToMinute(gCal1, gCal2) > 0) return 1;
		
		return 0;
	}
	
	public static String format(GregorianCalendar gCal) {
		return new SimpleDateFormat().format(gCal) ;
		
	}
}
