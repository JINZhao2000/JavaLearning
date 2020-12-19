package com.ayy.base;

import org.junit.Test;

/**
 * @ ClassName StringTest
 * @ Description use of the class String
 * @ Author Zhao JIN
 * @ Date 30/10/2020 11:04
 * @ Version 1.0
 */
public class StringTest {

	/**
	 * the use of StringBuilder
	 */
	@Test
    public void test01(){
	    String s = new String("");
	    // StringBuilder
	    // StringBuilder thread unsafe, high efficiency; StringBuffer thread safe low efficiency
	    StringBuilder sb = new StringBuilder("abcdefg");

	    System.out.println(Integer.toHexString(sb.hashCode()));
	    System.out.println(sb);
	    // set a character
	    sb.setCharAt(2,'M');
	    System.out.println(Integer.toHexString(sb.hashCode()));
	    System.out.println(sb);
    }

	/**
	 * the use of StringBuilder2
	 */
	@Test
    public void test02(){
	    StringBuilder sb = new StringBuilder();
	    // create chain of letters
	    for(int i=0;i<26;i++){
		    char temp = (char)('a'+i);
		    sb.append((temp));
	    }
	    System.out.println(sb);
	    // reverse of String
	    sb.reverse();
	    System.out.println(sb);
	    // set a character
	    sb.setCharAt(3,'1');
	    System.out.println(sb);
	    // insert a character
	    sb.insert(0,'2').insert(3,'3');
	    // chain of calls, use the method : return this;
	    System.out.println(sb);
	    // delete a character
	    sb.delete(20,23);
	    System.out.println(sb);
    }

	/**
	 * the comparision between String and StringBuilder
	 */
	@Test
	public void test03(){
	    // use String to splice the characters
	    String str = "";
	    // the splicing use actually the class StringBuilder, it creates a new StringBuild every time
	    long num1 = Runtime.getRuntime().freeMemory();
	    long time1 = System.currentTimeMillis();
	    for(int i=0; i<5000; i++){
		    str = str +i;
	    }
	    long num2 = Runtime.getRuntime().freeMemory();
	    long time2 = System.currentTimeMillis();
	    System.out.println("String used memory ： "+ (num1 - num2));
	    System.out.println("String used time ： "+ (time2 - time1));
	    // use StringBuilder to splice the characters
	    StringBuilder sb1 = new StringBuilder("");
	    long num3 = Runtime.getRuntime().freeMemory();
	    long time3 = System.currentTimeMillis();
	    for(int i = 0; i<5000; i++){
		    sb1.append(i);
	    }
	    long num4 = Runtime.getRuntime().freeMemory();
	    long time4 = System.currentTimeMillis();
	    System.out.println("StringBuilder used memory ： "+(num3 - num4));
	    System.out.println("StringBuilder used time ： "+(time4 - time3));
    }
}
