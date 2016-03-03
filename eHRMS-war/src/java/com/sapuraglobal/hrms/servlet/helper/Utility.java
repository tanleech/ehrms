/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.servlet.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
public class Utility {
    
    public static Date format(String dateString, String format)throws ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date dateObj = formatter.parse(dateString);

        return dateObj;
    }
    public static double computeDaysBetween(Date d1, Date d2)
    {
        long diff = d2.getTime()-d1.getTime();
        
        double days = Math.abs(d2.getTime() - d1.getTime()) / 86400000;
        return days;
    }
    public static Date getYearBeginTime()
    {
        //SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
       try
       {
        Date current = new Date();
        Calendar cal = Calendar.getInstance(); 
        int year = cal.get(Calendar.YEAR);
        String source = "01/01/"+String.valueOf(year);
        System.out.println("source :"+source);
        Date yearBeginTime = format(source,"MM/dd/yyyy"); 
        return yearBeginTime;
       }catch(ParseException pe)
       {
           return null;
       }
       
    }
    
}
