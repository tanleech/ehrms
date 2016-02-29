/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.servlet.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    
}
