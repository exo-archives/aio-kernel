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
package org.exoplatform.services.log;

/**
 * Created by the Exo Development team. Author : Tuan Nguyen
 * @version $Id$
 */
public class LogMessage {

  static public int FATAL = 0;

  static public int ERROR = 1;

  static public int WARN  = 2;

  static public int INFO  = 3;

  static public int DEBUG = 4;

  static public int TRACE = 5;

  private String    name_;

  private int       type_;

  private String    message_;

  private String    detail_;

  public LogMessage(String name, int type, String message, String detail) {
    name_ = name;
    type_ = type;
    message_ = message;
    detail_ = detail;
  }

  public String getName() {
    return name_;
  }

  public int getType() {
    return type_;
  }

  public String getMessage() {
    return message_;
  }

  public String getDetail() {
    return detail_;
  }

}
