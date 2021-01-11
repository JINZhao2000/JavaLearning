package com.ayy.webserver.server;

/**
 * @ ClassName Servlet
 * @ Description Interface of
 * @ Author Zhao JIN
 * @ Date 11/01/2021 22H
 * @ Version 1.0
 */

public interface Servlet {
    void service(Request request, Response response);
}
