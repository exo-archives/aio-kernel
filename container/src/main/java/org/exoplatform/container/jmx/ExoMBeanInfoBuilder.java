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
package org.exoplatform.container.jmx;

import org.exoplatform.container.management.ManagedTypeMetaData;
import org.exoplatform.container.management.MetaDataBuilder;
import org.exoplatform.container.management.ManagedMethodMetaData;
import org.exoplatform.container.management.ManagedMethodParameterMetaData;
import org.exoplatform.container.management.ManagedPropertyMetaData;

import javax.management.modelmbean.ModelMBeanOperationInfo;
import javax.management.modelmbean.ModelMBeanInfoSupport;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanConstructorInfo;
import javax.management.modelmbean.ModelMBeanNotificationInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.MBeanParameterInfo;
import javax.management.Descriptor;
import javax.management.IntrospectionException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.Collections;

/**
 * <p>A class that build mbean meta data</p>
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class ExoMBeanInfoBuilder {

  private static enum Role {
    SET("setter"),
    IS("getter"),
    GET("getter"),
    OP("operation");

    private final String name;

    private Role(String role) {
      this.name = role;
    }
  }

  private MetaDataBuilder metaDataBuilder;

  /**
   * Create a new builder.
   *
   * @param clazz the clazz
   * @throws IllegalArgumentException if the class is null or does not contain meta data
   */
  public ExoMBeanInfoBuilder(Class clazz) throws IllegalArgumentException {
    this.metaDataBuilder = new MetaDataBuilder(clazz);
  }

  public boolean isBuildable() {
    return metaDataBuilder.isBuildable();
  }

  private ModelMBeanOperationInfo buildOperationInfo(
    Method method,
    String description,
    Role role,
    Collection<ManagedMethodParameterMetaData> parametersMD) {
    ModelMBeanOperationInfo operationInfo = new ModelMBeanOperationInfo(description, method);

    //
    if (description == null) {
      description = "Management operation";
    }

    //
    MBeanParameterInfo[] parameterInfos = operationInfo.getSignature();
    for (ManagedMethodParameterMetaData parameterMD : parametersMD) {
      int i = parameterMD.getIndex();
      MBeanParameterInfo parameterInfo = parameterInfos[i];
      String parameterName = parameterInfo.getName();
      String parameterDescription = operationInfo.getSignature()[i].getDescription();
      if (parameterMD.getName() != null) {
        parameterName = parameterMD.getName();
      } else if (parameterMD.getDescription() != null) {
        parameterDescription = parameterMD.getDescription();
      }
      parameterInfos[i] = new MBeanParameterInfo(
        parameterName,
        parameterInfo.getType(),
        parameterDescription);
    }

    //
    Descriptor operationDescriptor = operationInfo.getDescriptor();
    operationDescriptor.setField("role", role.name);

    //
    return new ModelMBeanOperationInfo(
      operationInfo.getName(),
      description,
      parameterInfos,
      operationInfo.getReturnType(),
      operationInfo.getImpact(),
      operationDescriptor);
  }

  /**
   * Build the info.
   *
   * @return returns the info
   * @throws IllegalStateException raised by any build time issue
   */
  public ModelMBeanInfo build() throws IllegalStateException {
    ManagedTypeMetaData typeMD = metaDataBuilder.build();

    //
    String mbeanDescription = "Exo model mbean";
    if (typeMD.getDescription() != null) {
      mbeanDescription = typeMD.getDescription();
    }

    //
    ArrayList<ModelMBeanOperationInfo> operations = new ArrayList<ModelMBeanOperationInfo>();
    for (ManagedMethodMetaData methodMD : typeMD.getMethods()) {
      ModelMBeanOperationInfo operationInfo = buildOperationInfo(
        methodMD.getMethod(),
        methodMD.getDescription(),
        Role.OP,
        methodMD.getParameters()
      );
      operations.add(operationInfo);
    }

    //
    Map<String, ModelMBeanAttributeInfo> attributeInfos = new HashMap<String, ModelMBeanAttributeInfo>();
    for (ManagedPropertyMetaData propertyMD : typeMD.getProperties()) {

      Method getter = propertyMD.getGetter();
      if (getter != null) {
        Role role;
        String getterName = getter.getName();
        if (getterName.startsWith("get") && getterName.length() > 3) {
          role = Role.GET;
        } else if (getterName.startsWith("is") && getterName.length() > 2) {
           role = Role.IS;
        } else {
          throw new AssertionError();
        }
        Collection<ManagedMethodParameterMetaData> blah = Collections.emptyList();
        ModelMBeanOperationInfo operationInfo = buildOperationInfo(
          getter,
          propertyMD.getGetterDescription(),
          role,
          blah
        );
        operations.add(operationInfo);
      }

      //
      Method setter = propertyMD.getSetter();
      if (setter != null) {
        ManagedMethodParameterMetaData s = new ManagedMethodParameterMetaData(0);
        s.setDescription(propertyMD.getSetterParameter().getDescription());
        s.setName(propertyMD.getSetterParameter().getName());
        Collection<ManagedMethodParameterMetaData> blah = Collections.singletonList(
          s
        );
        ModelMBeanOperationInfo operationInfo = buildOperationInfo(
          setter,
          propertyMD.getSetterDescription(),
          Role.SET,
          blah
        );
        operations.add(operationInfo);
      }

      //
      try {
        String attributeDescription = propertyMD.getDescription() != null ?
          propertyMD.getDescription() :
          ("Managed attribute " + propertyMD.getName());

        //
        ModelMBeanAttributeInfo attributeInfo = new ModelMBeanAttributeInfo(
            propertyMD.getName(),
            attributeDescription,
            getter,
            setter);

        //
        Descriptor attributeDescriptor = attributeInfo.getDescriptor();
        if (getter != null) {
          attributeDescriptor.setField("getMethod", getter.getName());
        }
        if (setter != null) {
          attributeDescriptor.setField("setMethod", setter.getName());
        }
        attributeDescriptor.setField("currencyTimeLimit", "-1");
        attributeDescriptor.setField("persistPolicy", "Never");
        attributeInfo.setDescriptor(attributeDescriptor);

        //
        ModelMBeanAttributeInfo previous = attributeInfos.put(propertyMD.getName(), attributeInfo);
        if (previous != null) {
          throw new IllegalArgumentException();
        }
      }
      catch (IntrospectionException e) {
        throw new AssertionError(e);
      }
    }

    //
    return new ModelMBeanInfoSupport(
      typeMD.getType().getName(),
      mbeanDescription,
      attributeInfos.values().toArray(new ModelMBeanAttributeInfo[attributeInfos.size()]),
      new ModelMBeanConstructorInfo[0],
      operations.toArray(new ModelMBeanOperationInfo[operations.size()]),
      new ModelMBeanNotificationInfo[0]);
  }
}
