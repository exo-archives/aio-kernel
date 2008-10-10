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
package org.exoplatform.services.remote.group;

import org.exoplatform.container.component.ComponentPlugin;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Feb 21, 2005
 * @version $Id: MessageHandler.java 5799 2006-05-28 17:55:42Z geaz $
 */
abstract public class MessageHandler implements ComponentPlugin {
  private String                name_;

  private String                description_;

  private MessageHandlerMonitor monitor_;

  public MessageHandler(String id) {
    monitor_ = new MessageHandlerMonitor(id);
  }

  public void init(CommunicationService service) {

  }

  public String getName() {
    return name_;
  }

  public void setName(String s) {
    name_ = s;
  }

  public String getDescription() {
    return description_;
  }

  public void setDescription(String s) {
    description_ = s;
  }

  public String getIdentifier() {
    return monitor_.getMessageHandlerId();
  }

  public MessageHandlerMonitor getMonitor() {
    return monitor_;
  }

  abstract public Object handle(Message message) throws Exception;

}
