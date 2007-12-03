 /*
 * Copyright (C) 2003-2007 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package org.exoplatform.commons.utils;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Benjamin Mestrallet
 * benjamin.mestrallet@exoplatform.com
 */
public class MapResourceBundle extends ResourceBundle implements Serializable {
  
  private final static String REGEXP = "#\\{.*\\}";
  
  private Map props = new HashMap();
  private Locale locale;
  
  public MapResourceBundle(Locale l) {    
    this.locale = l;
  }  

  public MapResourceBundle(ResourceBundle rB,  Locale l) {
    this.locale = l;
    initMap(rB);
  }

  private void initMap(ResourceBundle rB) {
    Enumeration e = rB.getKeys();
    while (e.hasMoreElements()) {
      String s = (String) e.nextElement();
      try {        
        if (props.get(s) == null) {
          String[] newArray = rB.getStringArray(s);        
          props.put(s, newArray);
        }
      } catch (ClassCastException ex) {
        props.put(s, rB.getObject(s));
      }
    }
  }

  protected Object handleGetObject(String key) {
    return props.get(key);
  }

  public Enumeration getKeys() {
    return new Vector(props.keySet()).elements();
  }

  public Locale getLocale() {
    return this.locale;
  }
  
  public void add(String key, Object value){
    props.put(key, value);
  }
  
  public void remove(String key){
    props.remove(key);
  }
    
  public void merge(ResourceBundle bundle){
    Enumeration e = bundle.getKeys();
    while (e.hasMoreElements()) {
      String s = (String) e.nextElement();
      Object value = bundle.getObject(s);      
      try {        
        String[] newArray = bundle.getStringArray(s);        
        if (props.get(s) == null) {
          props.put(s, newArray);
        }
      } catch (ClassCastException ex) {
        props.put(s, value);
      }
    }    
  }   
  
  public void resolveDependencies(){
    Map tempMap = new HashMap();
    Set keys = props.keySet();
    Pattern pattern = Pattern.compile(REGEXP);
    for (Iterator iter = keys.iterator(); iter.hasNext();) {
      String element = (String) iter.next();
      String value = lookupKey(element, pattern);        
      tempMap.put(element, value);
    }
    props = tempMap;
  }
  
  private String lookupKey(String key, Pattern pattern){
    String s = (String) props.get(key);     
    if(s == null) return key;
    Matcher matcher = pattern.matcher(s);         
    if(matcher.find()){      
      return recursivedResolving(s, pattern);        
    }  
    return s;                   
  }  
  
  private String recursivedResolving(String key, Pattern pattern){                  
    String resolved = key;
    StringBuffer sB = new StringBuffer();
    while(resolved.indexOf("#{") != -1){  
      sB.setLength(0) ;
      int firstIndex = resolved.indexOf('#');      
      int lastIndex = resolved.indexOf('}', firstIndex);
      String realKey = resolved.substring(firstIndex + 2, lastIndex);      
      sB.append(resolved.substring(0, firstIndex));
      sB.append(lookupKey(realKey, pattern));
      sB.append(resolved.substring(lastIndex + 1));
      resolved = sB.toString();         
    }    
    return resolved;        
  }  
}