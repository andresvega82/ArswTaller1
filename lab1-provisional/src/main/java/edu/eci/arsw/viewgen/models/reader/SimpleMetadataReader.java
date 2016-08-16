/*
 * Copyright (C) 2016 nanohttpd
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.arsw.viewgen.models.reader;

import edu.eci.arsw.viewgen.annotations.RelativePosition;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

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

/**
 * @author hcadavid
 */
public class SimpleMetadataReader {

    public void llamarMetodos(String nameofMethod) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
        Class<?> c = Class.forName(nameofMethod);
        System.out.println("Introspecting " + c.getName() + "...");
        
        
        Method[] methods = c.getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getReturnType().equals(String.class)) {
                System.out.println("Metodos : " + methods[i].getName());
                
                System.out.println("Invocado:"+methods[i].invoke(c.newInstance()));
                        
            }
        }
    }

    public void llamaranotaciones(String nameofMethod) throws ClassNotFoundException {
        Class<?> c = Class.forName(nameofMethod);
        System.out.println("Introspecting " + c.getName() + "...");
        Method[] methods = c.getMethods();
        for (int i = 0; i < methods.length; i++) {
           
            if(Arrays.toString(methods[i].getAnnotationsByType(RelativePosition.class)).contains("RelativePosition") ){
                System.out.println("Metodos que contienen la anotacion RP : " + methods[i].getName());
            }
        }
    }

    public static void main(String args[]) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
        //        String className = args[0];

        SimpleMetadataReader smp = new SimpleMetadataReader();
        smp.llamarMetodos("edu.eci.arsw.viewgen.models.SampleViewMetadata");
       //smp.llamaranotaciones("edu.eci.arsw.viewgen.models.SampleViewMetadata");
    }
}
