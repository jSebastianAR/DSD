/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 *
 * @author jSeba
 */
public class Relojes{
   public String hora;
   public String minutos;
   public String segundos;
   public String ampm; 
   public int aux=0;
   public int auxhora,auxmin,auxseg;
   
   
   public Relojes()
   {}
  
   public void calcula() {
        
        Calendar calendario = new GregorianCalendar();
        Date fechaHoraActual = new Date();
        System.out.println("Valor antiguo de aux: "+this.aux);
        
        System.out.println("Valor actual de aux: "+this.aux);
        
        calendario.setTime(fechaHoraActual);
        ampm = calendario.get(Calendar.AM_PM)==Calendar.AM ? "AM":"PM";
        
        if(ampm.equals("PM"))
        {
            int h = calendario.get(Calendar.HOUR_OF_DAY)-12;
            this.hora = h>9? ""+h:"0"+h; 
        }else
        {
            this.hora = calendario.get(Calendar.HOUR_OF_DAY)>9?""+calendario.get(Calendar.HOUR_OF_DAY):"0"+calendario.get(Calendar.HOUR_OF_DAY);   
        }
        this.minutos = calendario.get(Calendar.MINUTE)>9?""+calendario.get(Calendar.MINUTE):"0"+calendario.get(Calendar.MINUTE);
        this.segundos = calendario.get(Calendar.SECOND)>9?""+calendario.get(Calendar.SECOND):"0"+calendario.get(Calendar.SECOND);
        this.aux=1;
    }
   
    public void actualiza()
    {
        auxhora = Integer.parseInt(this.hora);
        auxmin = Integer.parseInt(this.minutos);
        auxseg = Integer.parseInt(this.segundos);
        
        if(auxseg<=58)//si ya sobrepaso el límite de segundos
        {
           auxseg+=1;//aumenta el segundero
        }else
        {
            if(auxmin<=58)
           {
              auxmin+=1; //aumenta el minutero 
              mandaActualizacion(auxmin,6679);
           }else //se reinicia la cuenta
           {
              auxmin=0;
              mandaActualizacion(auxmin,6678);
              if(auxhora<=22)
              {
                  auxhora+=1;//aumenta el valor de la hora
                  mandaActualizacion(auxhora,6677);
              }else
              {
                  auxhora=0;//se reinicia la hora
                  mandaActualizacion(auxhora,6677);
              }
           }
           auxseg=0; //se reinicia el segundero
        }
        
        if(auxhora>=0 && auxhora <=11) //actualizando la variable de horas
        {
            this.ampm = "am"; //hora de la mañana
            if(auxhora<=9)
            {
                this.hora = "0"+auxhora;//se concatena un cero.
            }else
            {
                this.hora = Integer.toString(auxhora);//se agrega tal y como está.
            }
        }else
        {
            this.ampm = "pm"; //hora de la mañana
            this.hora = Integer.toString(auxhora);//se agrega tal y como está.
        }
        
        if(auxmin>=0 && auxmin<=9) //actualizando minutos
        {
            this.minutos = "0"+auxmin;//seconcatena un cero
        }else
        {
            this.minutos = Integer.toString(auxmin);//
        }
        
        if(auxseg>=0 && auxseg<=9)//actualizando segundos
        {
            this.segundos = "0"+auxseg;//seconcatena un cero
        }else
        {
            this.segundos = Integer.toString(auxseg);//
        }
        
    }
    
    public void generaHora()
    {
        Random random = new Random(); //intancia para valor random
      
        auxhora = random.nextInt(24);//el valor de las horas será hasta 23
        auxmin = random.nextInt(60);//el valor de los minutos será hasta 59
        auxseg = random.nextInt(60);//el valor de los minutos será hasta 59
        
        if(auxhora>=0 && auxhora <=11) //actualizando la variable de horas
        {
            this.ampm = "am"; //hora de la mañana
            if(auxhora<=9)
            {
                this.hora = "0"+auxhora;//se concatena un cero.
            }else
            {
                this.hora = Integer.toString(auxhora);//se agrega tal y como está.
            }
        }else
        {
            this.ampm = "pm"; //hora de la mañana
            this.hora = Integer.toString(auxhora);//se agrega tal y como está.
        }
        
        if(auxmin>=0 && auxmin<=9) //actualizando minutos
        {
            this.minutos = "0"+auxmin;//seconcatena un cero
        }else
        {
            this.minutos = Integer.toString(auxmin);//
        }
        
        if(auxseg>=0 && auxseg<=9)//actualizando segundos
        {
            this.segundos = "0"+auxseg;//seconcatena un cero
        }else
        {
            this.segundos = Integer.toString(auxseg);//
        }
        
        this.aux=1;
        System.out.println("Randoms hora:"+this.hora+" minutos:"+this.segundos+" segundos:"+this.segundos);
    }
    
    
    public void mandaActualizacion(int valor,int puerto)
    {
                try {
            Socket s = new Socket("192.168.1.72", puerto);
            //Socket s = new Socket("192.168.1.82", 6667);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            String minutoCompleta="";
            if(puerto==6678)//minuto
            {
                minutoCompleta="00:"+Integer.toString(valor);
            }
            else//hora
            {
            minutoCompleta=Integer.toString(valor)+":00";    
            }
            //minutoCompleta=Integer.toString(valor);
            System.out.println(""+minutoCompleta);
            //horaCompleta = String.format("%02d", LabelRelojMaestro.);
            dout.writeUTF("" + minutoCompleta);
            dout.flush();
            dout.close();
            s.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
