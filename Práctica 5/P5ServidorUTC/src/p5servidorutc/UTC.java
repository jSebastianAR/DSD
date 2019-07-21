/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p5servidorutc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * @author jSeba
 */
public class UTC {
    
    /*public static void main(String args[])
    {
        System.out.println("utc:"+obtenerUTC());
    }*/
    
    public static String obtenerUTC()
    {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        Calendar calendar2 = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = 
               new SimpleDateFormat("EE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        simpleDateFormat.setTimeZone(timeZone);
        
    /*  System.out.println("Time zone: " + timeZone.getID());
        System.out.println("default time zone: " + TimeZone.getDefault().getID());
        System.out.println();
    */      
        
       // System.out.println("UTC:     " + simpleDateFormat.format(calendar.getTime()));
        String UTC=simpleDateFormat.format(calendar.getTime());//guarda toda la fecha y hora
        String[] parts = UTC.split(" ");
        //System.out.println("Hora en UTC:"+parts[3]);
        String horaUTC = parts[3];
        return horaUTC;
    }
}
