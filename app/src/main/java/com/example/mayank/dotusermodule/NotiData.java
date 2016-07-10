package com.example.mayank.dotusermodule;

/**
 * Created by mayank on 10/07/16.
 */
public class NotiData {

    String title,body,places;
    int date;

    public NotiData(String t,String b, String p,String d)
    {
        title = t;
        body = b;
        places = p;
        date = Integer.parseInt(d);
    }


}
