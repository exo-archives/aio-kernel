/**
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 */

package org.exoplatform.services.naming;

import java.util.Iterator;
import java.util.Map.Entry;

import javax.naming.Reference;
import javax.naming.StringRefAddr;

import org.exoplatform.container.component.BaseComponentPlugin;
import org.exoplatform.container.configuration.ConfigurationException;
import org.exoplatform.container.xml.InitParams;
import org.exoplatform.container.xml.PropertiesParam;
import org.exoplatform.container.xml.ValueParam;
/**
 * Created by The eXo Platform SARL        .<br/>
 * InitialContextInitializer's Component Plugin for binding reference to JNDI naming context
 * @author <a href="mailto:gennady.azarenkov@exoplatform.com">Gennady Azarenkov</a>
 * @version $Id: BindReferencePlugin.java 6853 2006-07-07 11:41:24Z geaz $
 */

public class BindReferencePlugin extends BaseComponentPlugin {
  
  private String bindName; 
  private Reference reference;

  /**
   * @param params
   * Mandatory:
   *   bind-name (value param) - name of binding reference
   *   class-name (value param) - type of binding reference
   *   factory (value param) - object factory type
   * Optional:
   *   ref-addresses (properties param)
   * @throws ConfigurationException
   */
  public BindReferencePlugin(InitParams params) throws ConfigurationException {

    ValueParam bnParam = params.getValueParam("bind-name");
    if(bnParam == null) {
      throw new ConfigurationException("No 'bind-name' parameter found");
    } 
    ValueParam cnParam = params.getValueParam("class-name");
    if(cnParam == null) {
      throw new ConfigurationException("No 'class-name' parameter found");
    } 
    ValueParam factoryParam = params.getValueParam("factory");
    if(factoryParam == null) {
      throw new ConfigurationException("No 'factory' parameter found");
    }
    ValueParam flParam = params.getValueParam("factory-location");
    String factoryLocation;
    if(flParam != null) factoryLocation = flParam.getValue();
    else factoryLocation = null;
    
    bindName = bnParam.getValue();
    reference = new Reference(cnParam.getValue(), factoryParam.getValue(), factoryLocation);
    
    PropertiesParam addrsParam = params.getPropertiesParam("ref-addresses");
    if (addrsParam != null) {
      for(Iterator it = addrsParam.getProperties().entrySet().iterator(); it.hasNext();) {
        Entry entry = (Entry)it.next();
        reference.add(new StringRefAddr((String)entry.getKey(), (String)entry.getValue()));
      }
    }
  }

  /**
   * @return reference bound
   */
  public Reference getReference() { return reference; }

  /**
   * @return name
   */
  public String getBindName() { return bindName; }
}
