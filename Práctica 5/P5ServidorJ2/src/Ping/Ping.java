package Ping;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author jSeba
 */
public class Ping {

    /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args) {
       
        hacerPing("www.google.com");
    }*/
    
    public static int hacerPing(String ip)
    {
        //ip = "www.google.com";
        String pingResult = "";
        String[] cadenas = new String[20];
        // Contiene la instruccion a ejecutar...
        // Esta instruccion podria ser cambiada por cualquier otra
        String pingCmd = "ping  " + ip;
        int c=0;
        try
        {
            Runtime r = Runtime.getRuntime();
            Process p = r.exec(pingCmd);
 
            // Inicializa el lector del buffer
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
 
            String inputLine;
            // Bucle mientas reciba parametros del buffer
            while ((inputLine = in.readLine()) != null)
            {
                // Muestra por pantalla cada una de las lineas que recibe
                //System.out.println(c+" "+inputLine);
                
                // Si deseamos capturar el resultado para posteriormente
                // utilizarlo en nuestra aplicacion
                pingResult = inputLine;
                cadenas[c] = pingResult;
                c+=1;
            }
            in.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        
        //System.out.println("La ultima cadena es: "+cadenas[c-1]);
        int latencia = obtenerLatencia(cadenas[c-1]);//envía la última cadena del informe del ping
        System.out.println("La latencia es: "+latencia);
        return latencia;
    }
    
    public static int obtenerLatencia(String ultima)
    {
        String miliseg = obtenerMilisegundos(ultima);
        String[] parts=miliseg.split(" ");
        int latencia = Integer.parseInt(parts[2]);//obtiene el tiempo promedio que tardo en ser recibido la respuesta del ping
        return latencia;
    }
    
    public static String obtenerMilisegundos(String cadena)
    {
        String result = "";
        int bandera=0;
        for(int i=0; i<cadena.length(); i++)
        {
            if(Character.isDigit(cadena.charAt(i)))//si lo que lee es un dígito
            {
                result+=cadena.charAt(i);//se guarda
                
                if(Character.isDigit(cadena.charAt(i+1))==false)//si ya obtuvo todo un digito
                    bandera = 1;
            }
            if(bandera==1)//puede agregar un separador
                result+=" ";
                bandera=0;
        }
        //System.out.println("Resultado de obtenerMilisegundos: "+result.trim());
        return result;
    }
    
}
