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
package org.exoplatform.services.log.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.exoplatform.container.xml.InitParams;
import org.exoplatform.container.xml.PropertiesParam;
import org.exoplatform.services.log.LogService;

/**
 * Created by the Exo Development team. Author : Mestrallet Benjamin
 * benjmestrallet@users.sourceforge.net Date: 14 nov. 2003 Time: 20:40:57
 * @deprecated to instanciate a logger use ExoLogger.getLogger()
 */
public class LogServiceImpl implements LogService {

	private HashMap logs_;

	private HashMap configure_;
	  
  public LogServiceImpl(InitParams params) throws Exception {
    logs_ = new HashMap() ;
    configure_ = new HashMap();
    configure_.put("org.exoplatform", new Integer(ExoLog.INFO));
    PropertiesParam param = params.getPropertiesParam("log.level.config") ;
    Iterator i =  param.getProperties().entrySet().iterator() ;
    while(i.hasNext()) {
      Map.Entry entry = (Map.Entry) i.next() ;
      String name = (String)entry.getKey() ;
      int  level =  toLevel((String) entry.getValue());
      configure_.put(name, new Integer(level));
    }
  
  }
	public void start() {
	}

	public void stop() {

	}

	public Log getLog(String name) {
		Log log = (Log) logs_.get(name);
		if (log == null) {
			synchronized (logs_) {
				int level = ExoLog.INFO;
				try {
					level = getDefaultLogLevel(name);
				} catch (Exception ex) {
				}
				log = new ExoLog(name, level);
				logs_.put(name, log);
			}
		}
		return log;
	}

	public Log getLog(Class clazz) {
		String name = clazz.getName();
		int idx = name.lastIndexOf(".");
		name = name.substring(0, idx);
		return getLog(name);
	}

	public Collection getLogs() {
		return logs_.values();
	}

	public int getLogLevel(String name) throws Exception {
		ExoLog log = (ExoLog) logs_.get(name);
		if (log != null)
			return log.getLevel();
		return INFO;
	}

	public void setLogLevel(String name, int level, boolean recursive)
			throws Exception {
		if (recursive) {
			Iterator i = logs_.values().iterator();
			while (i.hasNext()) {
				ExoLog log = (ExoLog) i.next();
				if (log.getLogCategory().startsWith(name)) {
					log.setLevel(level);
				}
			}
		} else {
			ExoLog log = (ExoLog) logs_.get(name);
			if (log != null) {
				log.setLevel(level);
			}
		}
	}

	public List getLogBuffer() {
		return ExoLog.getLogBuffer();
	}

	public List getErrorBuffer() {
		return ExoLog.getErrorBuffer();
	}

	private int getDefaultLogLevel(String name) throws Exception {
		while (name != null) {
			Integer level = (Integer) configure_.get(name);
			if (level != null)
				return level.intValue();
			int index = name.lastIndexOf(".");
			if (index > 0)
				name = name.substring(0, index);
			else
				name = null;
		}
		return ExoLog.INFO;
	}
  
  private int toLevel(String s) {
    if("FATAL".equals(s)) return ExoLog.FATAL ;
    else if("ERROR".equals(s)) return ExoLog.ERROR ;
    else if("WARN".equals(s)) return ExoLog.WARN ;
    else if("INFO".equals(s)) return ExoLog.INFO ;
    else if("DEBUG".equals(s)) return ExoLog.DEBUG ;
    else if("TRACE".equals(s)) return ExoLog.TRACE ;
    else return ExoLog.INFO ;
  }
}