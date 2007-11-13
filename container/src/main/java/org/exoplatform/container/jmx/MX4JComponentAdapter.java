/*****************************************************************************
 * Copyright (C) NanoContainer Organization. All rights reserved.            *
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD      *
 * style license a copy of which has been included with this distribution in *
 * the LICENSE.txt file.                                                     *
 *                                                                           *
 * Original code by James Strachan and Mauro Talevi                          *
 *****************************************************************************/
package org.exoplatform.container.jmx;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;

import org.exoplatform.container.ExoContainer;
import org.exoplatform.container.component.ComponentLifecycle;
import org.exoplatform.container.component.ComponentPlugin;
import org.exoplatform.container.configuration.ConfigurationManager;
import org.exoplatform.container.xml.Component;
import org.exoplatform.container.xml.ExternalComponentPlugins;
import org.exoplatform.container.xml.InitParams;
import org.picocontainer.PicoContainer;
import org.picocontainer.defaults.AbstractComponentAdapter;
/**
 * @author James Strachan
 * @author Mauro Talevi
 * @author Jeppe Cramon
 * @author Benjamin Mestrallet 
 * @version $Revision: 1.5 $
 */
public class MX4JComponentAdapter extends AbstractComponentAdapter {
  private Object instance_ ;

  public MX4JComponentAdapter(Object key, Class implementation) {
    super(key, implementation) ;
  }

  public Object getComponentInstance(PicoContainer container) {
    if(instance_ != null ) return instance_ ;
    ExoContainer exocontainer = (ExoContainer) container ;
    try {
      Object key = getComponentKey() ;
      String componentKey = null ;
      if(key instanceof String)  componentKey = (String) key ;
      else  componentKey = ((Class)key).getName() ;
      ConfigurationManager manager = 
        (ConfigurationManager) exocontainer.getComponentInstanceOfType(ConfigurationManager.class);
      Component component =  manager.getComponent(componentKey) ;
      InitParams params = null ;
      boolean debug =  false ;
      if(component != null) {
        params = component.getInitParams() ;
        debug = component.getShowDeployInfo() ;
      }
      
      instance_ = exocontainer.createComponent(getComponentImplementation(), params) ;
      
      if(debug) print("==> create  component : " + instance_) ;
      if(component != null && component.getComponentPlugins() != null) {
        addComponentPlugin(debug,instance_, component.getComponentPlugins(),exocontainer);
      }
      ExternalComponentPlugins ecplugins = 
        manager.getConfiguration().getExternalComponentPlugins(componentKey) ;
      if(ecplugins != null) {
        addComponentPlugin(debug, instance_, ecplugins.getComponentPlugins(), exocontainer) ;
      }
      //check  if component implement  the ComponentLifecycle 
      exocontainer.manageMBean(component,componentKey, instance_) ;
      if(debug) print("==> add " + component + " to a mbean server") ;
      if(instance_ instanceof ComponentLifecycle) {
        ComponentLifecycle lc  = (ComponentLifecycle) instance_ ;
        lc.initComponent(exocontainer) ;
      }
    } catch (Exception ex) {
      throw new RuntimeException("Cannot instantiate component " + getComponentImplementation(), ex) ;
    }
    return instance_ ;
  }

  private void addComponentPlugin(boolean debug, Object component,  
      List<org.exoplatform.container.xml.ComponentPlugin> plugins, 
      ExoContainer container) throws Exception {
    if(plugins == null) return ;
    for(org.exoplatform.container.xml.ComponentPlugin plugin : plugins) {
      Class clazz = Class.forName(plugin.getType()) ;
      ComponentPlugin cplugin = 
        (ComponentPlugin)container.createComponent(clazz, plugin.getInitParams()) ;
      cplugin.setName(plugin.getName()) ;
      cplugin.setDescription(plugin.getDescription()) ;
      clazz =  component.getClass() ;
      try {
        Method m =  getSetMethod(clazz, plugin.getSetMethod()) ;
        Object[]  params = {cplugin} ;
        m.invoke(component,params) ;
        if(debug) print("==> add component plugin: " + cplugin) ;
      } catch (Exception ex) {
//      System. err.println("Error while invoking set method : " + plugin.getSetMethod()+ 
//      " of component : " + component.getClass().getName());
        throw ex ;
      }

      cplugin.setName(plugin.getName()) ;
      cplugin.setDescription(plugin.getDescription()) ;
    }
  }


  private Method  getSetMethod(Class  clazz ,  String name) {
    Method[] methods  = clazz.getMethods() ;
    for(Method m : methods) {
      if(name.equals(m.getName())) {
        Class[] types = m.getParameterTypes() ;
        if(types != null && types.length == 1 &&  
            ComponentPlugin.class.isAssignableFrom(types[0])) {
          return m ;
        }
      }
    }
    return null ;
  }

  public void verify(PicoContainer container)  {  }

  private void print(String message) {
//  System .out.println(message) ;
  }
}