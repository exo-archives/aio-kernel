package org.exoplatform.services.log;

/**
 * Copyright 2001-2003 The eXo platform SARL All rights reserved.
 * Please look at license.txt in info directory for more license detail.
 **/

/**
 * Created by the Exo Development team.
 * Author : Tuan Nguyen
 *          tuan08@users.sourceforge.net
 * Date: 14 nov. 2003
 * Time: 20:19:45
 */
public class LogMessage {

  static public int FATAL = 0 ;
  static public int ERROR = 1 ;
  static public int WARN = 2 ;
  static public int INFO = 3 ;
  static public int DEBUG = 4 ;
  static public int TRACE = 5 ;
  
  private String name_ ;
  private int type_ ;
  private String message_ ;
  private String detail_ ;
  

  public LogMessage(String name, int type, String message, String detail) {
    name_ = name ;
    type_ = type ;
    message_ =  message ;
    detail_ = detail ;
  }
  
  public String getName() { return name_ ; }
  
  public int getType() { return type_ ; }
  
  public String getMessage() { return message_ ; } 

  public String getDetail()  { return detail_ ; }

}
