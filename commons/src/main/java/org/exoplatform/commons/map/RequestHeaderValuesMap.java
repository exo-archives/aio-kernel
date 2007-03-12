/**
 * Copyright (c) 2001-2006 eXo Platform SARL, http://www.exoplatform.com/
 *
 * All rights reserved. Please look at license.txt in info directory for
 * more license detail.
 *
 * -------------------------------------------------------------------------
 * $Id: RequestHeaderValuesMap.java 10579 2006-11-24 03:18:07Z rmarins $
 * -------------------------------------------------------------------------
 */
package org.exoplatform.commons.map;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @version $Rev: 10579 $
 * @author <a href="mailto:rafael.marins@exoplatform.com">Rafael Marins</a>
 */
public class RequestHeaderValuesMap implements Map<String, Enumeration<String>>{

    private Map<String, Enumeration<String>> delegate;

    public RequestHeaderValuesMap(HttpServletRequest request) {
        super();
        this.delegate = new HashMap<String, Enumeration<String>>();
        
        load(request);
    }

    /**
     * @param request
     */
    private void load(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String name = headers.nextElement();
            Enumeration<String> values = request.getHeaders(name);
            put(name, values);
        }
    }

    /**
     * 
     * @see java.util.Map#clear()
     */
    public void clear() {
        delegate.clear();
    }

    /**
     * @param key
     * @return
     * @see java.util.Map#containsKey(java.lang.Object)
     */
    public boolean containsKey(Object key) {
        return delegate.containsKey(key.toString().toLowerCase());
    }

    /**
     * @param value
     * @return
     * @see java.util.Map#containsValue(java.lang.Object)
     */
    public boolean containsValue(Object value) {
        return delegate.containsValue(value);
    }

    /**
     * @return
     * @see java.util.Map#entrySet()
     */
    public Set<Entry<String, Enumeration<String>>> entrySet() {
        return delegate.entrySet();
    }

    /**
     * @param o
     * @return
     * @see java.util.Map#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
        return delegate.equals(o);
    }

    /**
     * @param key
     * @return
     * @see java.util.Map#get(java.lang.Object)
     */
    public Enumeration<String> get(Object key) {
        return delegate.get(key.toString().toLowerCase());
    }

    /**
     * @return
     * @see java.util.Map#hashCode()
     */
    public int hashCode() {
        return delegate.hashCode();
    }

    /**
     * @return
     * @see java.util.Map#isEmpty()
     */
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    /**
     * @return
     * @see java.util.Map#keySet()
     */
    public Set<String> keySet() {
        return delegate.keySet();
    }

    /**
     * @param key
     * @param value
     * @return
     * @see java.util.Map#put(java.lang.Object, java.lang.Object)
     */
    public Enumeration<String> put(String key, Enumeration<String> values) {
        return delegate.put(key.toLowerCase(), values);
    }

    /**
     * @param t
     * @see java.util.Map#putAll(java.util.Map)
     */
    public void putAll(Map<? extends String, ? extends Enumeration<String>> t) {
        delegate.putAll(t);
    }

    /**
     * @param key
     * @return
     * @see java.util.Map#remove(java.lang.Object)
     */
    public Enumeration<String> remove(Object key) {
        return delegate.remove(key.toString().toLowerCase());
    }

    /**
     * @return
     * @see java.util.Map#size()
     */
    public int size() {
        return delegate.size();
    }

    /**
     * @return
     * @see java.util.Map#values()
     */
    public Collection<Enumeration<String>> values() {
        return delegate.values();
    }

}
