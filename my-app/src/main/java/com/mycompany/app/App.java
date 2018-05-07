package com.mycompany.app;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Cookie cookie = new Cookie("test", "test");
        cookie.setHttpOnly(true);
    }
}
