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

import java.util.List;

/**
 * This object breaks the concepts of paging at its usage leads to load all the objects. It should not
 * be used and will be probably removed.
 *
 * @deprecated
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Oct 21, 2004
 * @version $Id: ObjectPageList.java,v 1.1 2004/10/22 14:18:46 tuan08 Exp $
 */
public class ObjectPageList<E> extends PageList<E> {

  private List<E> objects_;

  public ObjectPageList(List<E> list, int pageSize) {
    super(pageSize);
    objects_ = list;
    setAvailablePage(list.size());
  }

  protected void populateCurrentPage(int page) throws Exception {
    currentListPage_ = objects_.subList(getFrom(), getTo());
  }

  public List<E> getAll() throws Exception {
    return objects_;
  }
}
