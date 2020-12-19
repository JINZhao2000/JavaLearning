package com.ayy.base;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * @ ClassName CalendarTest
 * @ Description use the APIs of Calender
 * @ Author Zhao JIN
 * @ Date 30/10/2020 11:51
 * @ Version 1.0
 */
public class CalendarTest {
    @Test
    public void test01(){
        // set the elements of Calendar
        Calendar cal = new GregorianCalendar(2999,10,9,22,10,50);
        int year = cal.get(cal.YEAR);
        int month = cal.get(cal.MONTH);
        int day = cal.get(cal.DATE);
        int weekday = cal.get(cal.DAY_OF_WEEK);
        System.out.println(year);
        System.out.println(month);
        System.out.println(weekday);
        System.out.println(day);

        Calendar cal2 = new GregorianCalendar();
        cal2.set(Calendar.YEAR,8012);
        System.out.println(cal2);
        // calculation of Date
        Calendar cal3 = new GregorianCalendar();
        cal3.add(Calendar.DATE,-100);
        System.out.println(cal3);

        // transformation between the Date and the Calendar
        Date d1 = cal3.getTime();
        Calendar cal4 = new GregorianCalendar();
        cal4.setTime(new Date());
        printCalendar(cal4);
    }

    /**
     * print a calendar of the date entered
     * @throws ParseException the date entered is not valid
     */
    @Test
    public void test02() throws ParseException {
        System.out.println("enter the date (format：2020-1-1): ");
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date d = df.parse(str);
        Calendar c = new GregorianCalendar();
        c.setTime(d);


        int days = c.getActualMaximum(Calendar.DATE);
        int day = c.get(Calendar.DAY_OF_MONTH);
        System.out.println("Sun\tMon\tTue\tWen\tThu\tFri\tSat");
        c.set(Calendar.DAY_OF_MONTH,1);
        for (int i=0;i<c.get(Calendar.DAY_OF_WEEK)-1;i++){
            System.out.print("\t");
        }

        for (int i=1;i<=days;i++){
            if(day == c.get(Calendar.DAY_OF_MONTH)){
                System.out.print(c.get(Calendar.DAY_OF_MONTH) + "*\t");
            }else {
                System.out.print(c.get(Calendar.DAY_OF_MONTH) + "\t");
            }
            if(c.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
                System.out.println();
            }
            c.add(Calendar.DAY_OF_MONTH,1);
        }
    }

    /**
     * print a Calendar with the format we define
     * @param c the Calendar to print
     */
    public static void printCalendar(Calendar c){
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int date = c.get(Calendar.DATE);
        int dayweek = c.get(Calendar.DAY_OF_WEEK)-1;
        String dayweek2 = (dayweek+"");
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        int seconde = c.get(Calendar.SECOND);
        System.out.println(year+"-"+month+"-"+date+" "+hour+":"+minute+":"+seconde+" "+dayweek2+" day");
    }
}
