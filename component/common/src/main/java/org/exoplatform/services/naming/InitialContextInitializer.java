/**
 * Copyright 2001-2006 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 */

package org.exoplatform.services.naming;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;
import javax.naming.Reference;

import org.apache.commons.logging.Log;
import org.exoplatform.container.component.ComponentPlugin;
import org.exoplatform.container.configuration.ConfigurationException;
import org.exoplatform.container.xml.InitParams;
import org.exoplatform.container.xml.PropertiesParam;
import org.exoplatform.container.xml.Property;
import org.exoplatform.services.log.ExoLogger;

/**
 * Created by The eXo Platform SARL        .<br/>
 * Initializer for Context.INITIAL_CONTEXT_FACTORY
 * @author Gennady Azarenkov
 * @version $Id: InitialContextInitializer.java 9867 2006-10-30 08:01:12Z geaz $
 */

public class InitialContextInitializer {
  
  final public static String PROPERTIES_DEFAULT = "default-properties";
  final public static String PROPERTIES_MANDATORY = "mandatory-properties";
  
  private static Log log = ExoLogger.getLogger("naming.InitialContextInitializer");

  private List <BindReferencePlugin> bindReferencesPlugins;
  
  private String defaultContextFactory;
  
  private final InitialContext initialContext;
  
  
  /**
   * @param params
   * @throws NamingException
   * @throws ConfigurationException if no context factory initialized and no context-factory nor default-context-factory configured 
   */
  public InitialContextInitializer(InitParams params) throws NamingException, ConfigurationException {
    for (Iterator propsParams = params.getPropertiesParamIterator(); propsParams.hasNext();) {
      PropertiesParam propParam = (PropertiesParam) propsParams.next();
      boolean isDefault = propParam.getName().equals(PROPERTIES_DEFAULT);
      boolean isMandatory = propParam.getName().equals(PROPERTIES_MANDATORY);
      for (Iterator props = propParam.getPropertyIterator(); props.hasNext(); ) {
        Property prop = (Property) props.next();
        String propName = prop.getName();
        String propValue = prop.getValue();
        String existedProp = System.getProperty(propName);
        if (isMandatory) {
          setSystemProperty(propName, propValue, propParam.getName());
        } else if (isDefault) {
          if (existedProp == null) {
            setSystemProperty(propName, propValue, propParam.getName());
          } else {
            log.info("Using default system property: " + propName + " = " + existedProp);
          }
        }
      }
    }
    initialContext = new InitialContext();
    bindReferencesPlugins = new ArrayList<BindReferencePlugin>();
  }  
  
  private void setSystemProperty(String propName, String propValue, String propParamName) {
    System.setProperty(propName, propValue);
    if (propName.equals(Context.INITIAL_CONTEXT_FACTORY)) {
      defaultContextFactory = propValue;
    }
    log.info("Using mandatory system property: " + propName + " = " + System.getProperty(propName));
  }
  
  // for out-of-container testing
  private InitialContextInitializer(String name, Reference reference) throws NamingException {
    if(System.getProperty(Context.INITIAL_CONTEXT_FACTORY) == null)
      System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
          defaultContextFactory);
    initialContext = new InitialContext();
    initialContext.rebind(name, reference);
    
  }
  
  /**
   * Patch for case when bound objects are not shared i.e. there are some parts of app using 
   * different copy of Context, for example per web app InitialContext in Tomcat  
   */
  public void recall() {
    for(BindReferencePlugin plugin : bindReferencesPlugins) {
      try {
        InitialContext ic = new InitialContext();
        ic.bind(plugin.getBindName(), plugin.getReference());
        log.info("Reference bound (by recall()): "+plugin.getBindName());
      } catch (NameAlreadyBoundException e) {
        log.debug("Name already bound: "+plugin.getBindName());
      } catch (NamingException e) {
        log.error("Could not bind: "+plugin.getBindName(), e);
      }
    }
  }
  
  public void addPlugin(ComponentPlugin plugin) {
    if (plugin instanceof BindReferencePlugin) {
      BindReferencePlugin brplugin = (BindReferencePlugin) plugin;
      try {
        //initialContext = new InitialContext();
        initialContext.rebind(brplugin.getBindName(), brplugin.getReference());
        log.info("Reference bound: "+brplugin.getBindName());
        bindReferencesPlugins.add((BindReferencePlugin)plugin);
      } catch (NamingException e) {
        log.error("Could not bind: "+brplugin.getBindName(), e);
      }
    }
  }

  public ComponentPlugin removePlugin(String name) {
    return null;
  }

  public Collection getPlugins() {
    return bindReferencesPlugins;
  }
  
  /**
   * @return defaultContextFactory name
   */
  public String getDefaultContextFactory() {
    return defaultContextFactory;
  }


  /**
   * @return stored InitialContext
   */
  public synchronized InitialContext getInitialContext() {
    return initialContext;
  }

  // for out-of-container testing
  public static void initialize(String name, Reference reference) throws NamingException {
    new InitialContextInitializer(name, reference);
  }

  
}
