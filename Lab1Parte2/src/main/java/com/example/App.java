package com.example;

/*
 * #%L
 * NanoHttpd-Webserver
 * %%
 * Copyright (C) 2012 - 2016 nanohttpd
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the nanohttpd nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

import edu.eci.arsw.viewgen.annotations.AbsolutePosition;
import edu.eci.arsw.viewgen.annotations.RelativePosition;
import java.io.IOException;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App extends NanoHTTPD {
    
    Class<?> clase;
Object invoke;
Object invokeToList;
    public App(String name) throws IOException, ClassNotFoundException {
        super(8080);
        Class<?> forName = Class.forName(name);
        clase = forName;
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning! Point your browsers to http://localhost:8080/ \n");
    }

    public static void main(String[] args) throws ClassNotFoundException {
        try {
          String  ruta= "edu.eci.arsw.viewgen.models.SampleViewMetadata";
            App app = new App(ruta);
          
           
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }
    

    @Override
    public Response serve(IHTTPSession session) {
      
        java.lang.reflect.Method[] methods = clase.getMethods();
        String border = null;
        String borderList = null,heightList = null,widthList = null,topList = null,position = null;
        int height = 0,width = 0;
          for (int i = 0; i < methods.length; i++) {
           Annotation[] annotations = methods[i].getAnnotations();
            for (Annotation annotation : annotations) {
                if(annotation.annotationType().equals(RelativePosition.class)){
                    
                    try {
                        //System.out.println("Metodos que contienen la anotacion RP : " + methods[i].getName());
                         invoke = methods[i].invoke(clase.newInstance());
                        if(annotation instanceof RelativePosition){
                            RelativePosition myAnnotation = (RelativePosition) annotation;
                            border = myAnnotation.border();
                            height = myAnnotation.heightInPx();
                            width = myAnnotation.widthInPx();
                            
                            System.out.println("Bord"+ border + "hg"+ height +"wd"+ width);
                        }
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InstantiationException ex) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                if(annotation.annotationType().equals(AbsolutePosition.class)){
                    try {
                        invokeToList = methods[i].invoke(clase.newInstance());
                         if(annotation instanceof AbsolutePosition){
                            AbsolutePosition myAnnotation = (AbsolutePosition) annotation;
                            borderList = myAnnotation.border();
                            heightList = myAnnotation.height();
                            widthList = myAnnotation.width();
                            topList= myAnnotation.top();
                            position = myAnnotation.position();
                            System.out.println("Bord"+ borderList + "hg"+ heightList +"wd"+ widthList);
                        }
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InstantiationException ex) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        
        String msg = "<html><body><h1>Usando Metadatos:</h1>\n";       
        
        Map<String, String> parms = session.getParms();

        msg += 
                
                 "<div style="
                + "'height:"
                + height+""
                + ";width:"
                + width+""
                + ";border:"
                + border+"'><p>"
                + invoke+"</p>";
        String msgList="<div style="
                + "'position:"
                + position+""
                + ";width:"
                + widthList+""
                + ";border:"
                + borderList+""
                + ";height:"
                + heightList+";top:"
                + topList+"'><ul>"
                + invokeToList+"</ul><div></div>";

        return newFixedLengthResponse(msg + ""+msgList+"</body></html>\n");
    }
}
