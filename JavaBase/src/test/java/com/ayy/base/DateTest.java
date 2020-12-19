package com.ayy.base;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @ ClassName DateTest
 * @ Description use of the APIs of Date
 * @ Author Zhao JIN
 * @ Date 30/10/2020 11:27
 * @ Version 1.0
 */
public class DateTest {
	/**
	 * basic API of the class Date
	 */
	@Test
    public void test01(){
        Date d = new Date();
        DateFormat dateFormat = new SimpleDateFormat();
		System.out.println(d);
		System.out.println(d.getTime());
		System.out.println(d.after(d));
		System.out.println(d.equals(d));
		// @since jdk1.8 Date.toGMTString() is deprecated, it's replaced by DateFormat.format(new Date);
		System.out.println(dateFormat.format(d));
    }

	/**
	 * transformation between the String and the Date
	 * @throws ParseException when the String can't be transferred to Date
	 */
	@Test
    public void test02() throws ParseException {
	    // transfer the Date to String
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    String str = df.format(new Date());
	    System.out.println(str);

	    // transfer the String to Date
	    DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    Date date = df.parse("1970-01-01 00:00:00");
	    System.out.println(date);
    }

	/**
	 * new Date API @since jdk1.8
	 * use of the classes LocalDate, LocalTime, LocalDateTime
	 */
	@Test
	public void test03(){
	    LocalDate localDate = LocalDate.of(2000,1,11);
	    System.out.println(localDate);
	    LocalTime localTime = LocalTime.of(12,00);
	    System.out.println(localTime);
	    LocalDateTime localDateTime = LocalDateTime.of(localDate,localTime);
	    System.out.println(localDateTime);
    }
}
