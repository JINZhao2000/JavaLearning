package com.ayy.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @ ClassName GroupOfException
 * @ Description an example of a group of Exception
 * @ Author Zhao JIN
 * @ Date 30/10/2020 12:21
 * @ Version 1.0
 */
public class GroupOfException extends Exception{
    protected Throwable cause = null;
    private List<Throwable> exs = new ArrayList<Throwable>();

    public GroupOfException(){}

    public GroupOfException(String msg){
        super(msg);
    }

    public GroupOfException(Throwable cause){
        this.cause = cause;
    }

    public GroupOfException(String msg,Throwable cause){
        super(msg);
        this.cause = cause;
    }

    public List getException(){
        return exs;
    }

    public void addException(GroupOfException ex){
        exs.add(ex);
    }

    public Throwable initCause(Throwable cause){
        this.cause = cause;
        return this;
    }

    public Throwable getCause(){
        return cause;
    }

    public void printStackTrace(){
        printStackTrace(System.err);
    }

    public void printStackTrace(PrintStream outStream){
        printStackTrace(new PrintWriter(outStream));
    }

    public void printStackTrace(PrintWriter writer){
        super.printStackTrace(writer);
        if(getCause()!=null){
            getCause().printStackTrace(writer);
        }
        writer.flush();
    }
}
