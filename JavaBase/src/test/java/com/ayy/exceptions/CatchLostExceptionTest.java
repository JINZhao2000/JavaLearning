package com.ayy.exceptions;

import org.junit.Test;

/**
 * @ ClassName CatchLostException
 * @ Description catch the lost exception in finally
 * @ Author Zhao JIN
 * @ Date 30/10/2020 12:32
 * @ Version 1.0
 */
public class CatchLostExceptionTest {
    @Test
    public void test01(){
        CatchLostExceptionTest t = new CatchLostExceptionTest();
        try {
            t.show();
        }catch (Exception e){
            System.out.println("Caught Exception: "+e.getMessage());
            Throwable[] es = e.getSuppressed();
            for (Throwable ex : es ) {
                System.out.println("Lost Exception: "+ex.getMessage());
            }
        }
    }

    /**
     * a way to catch the lost exception
     * @throws Exception the lost exception
     */
    public void show() throws Exception{
        // reference of the Exception and save this exception
        Exception exception = null;
        try {
            Integer.parseInt("A");
        }catch (NumberFormatException e1){
            exception = e1;
        }finally {
            try {
                int result = 2/0;
            }catch (ArithmeticException e2){
                if (exception==null){
                    exception = e2;
                }else {
                    exception.addSuppressed(e2);
                }
            }
            throw exception;
        }
    }
}
