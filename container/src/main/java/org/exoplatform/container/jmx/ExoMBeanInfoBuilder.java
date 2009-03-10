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

import org.exoplatform.management.annotations.Managed;
import org.exoplatform.management.annotations.ManagedName;
import org.exoplatform.management.annotations.ManagedDescription;
import org.exoplatform.commons.reflect.AnnotationIntrospector;

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
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/**
 * <p>A class that build mbean meta data suitable for:
 * <ul>
 * <li>Represent a management view of the managed class</li>
 * <li>Provide additional metadata for a model mbean</li>
 * </ul>
 * </p>
 * <p>The following rules do apply to the class from which meta data are constructed:
 * <ul>
 * <li>The class must be annotated by {@link Managed}</li>
 * <li>The class may be annoated by {@link ManagedDescription}</li>
 * <li>Any property described by its getter and/or setter getter annotated by {@link Managed} is exposed as an attribute/li>
 * <li>Any property providing an annotated getter is readable</li>
 * <li>Any property providing an annotated setter is writable</li>
 * <li>Any getter/setter annotated by {@link ManagedName} redefines the attribute name</li>
 * <li>Any getter/setter annotated by {@link ManagedDescription} defines the attribute description</li>
 * <li>When corresponding getter/setter redefines the attribute name, the value must be the same otherwhise
 * an exception is thrown at built time</li>
 * <li>Any method annotated by {@link Managed} is exposed as a management operation</li>
 * <li>Any method annotated by {@link ManagedDescription} defines the operation description</li>
 * <li>Any non setter/getter method annotated by {@link ManagedName} causes a built time exception</li>
 * <li>Any method argument annotated by {@link ManagedName} defines the management name of the corresponding operation parameter</li>
 * <li>Any method argument annotated by {@link ManagedDescription} defines the management description of the corresponding operation parameter</li>
 * </ul>
 * </p>
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class ExoMBeanInfoBuilder {

  private static enum Type {
    SET("setter"),
    IS("getter"),
    GET("getter"),
    OP("operation");

    private final String role;

    private Type(String role) {
      this.role = role;
    }
  }

  private Class clazz;
  private boolean buildable;

  /**
   * Create a new builder.
   *
   * @param clazz the clazz
   * @throws IllegalArgumentException if the class is null or is not annotated by {@link Managed}
   */
  public ExoMBeanInfoBuilder(Class clazz) throws IllegalArgumentException {
    if (clazz == null) {
      throw new NullPointerException();
    }

    //
    Managed mb = AnnotationIntrospector.resolveClassAnnotations(clazz, Managed.class);

    //
    this.clazz = clazz;
    this.buildable = mb != null;
  }

  public boolean isBuildable() {
    return buildable;
  }

  /**
   * Build the info.
   *
   * @return returns the info
   * @throws IllegalStateException raised by any build time issue
   */
  public ModelMBeanInfo build() throws IllegalStateException {
    if (!buildable) {
      throw new IllegalStateException("Class " + clazz.getName() + " does not contain management annotation");
    }

    //
    ManagedDescription mbeanManagedDescription = AnnotationIntrospector.resolveClassAnnotations(clazz, ManagedDescription.class);
    String mbeanDescription = "Exo model mbean";
    if (mbeanManagedDescription != null) {
      mbeanDescription = mbeanManagedDescription.value();
    }

    //
    Map<Method, Managed> managedMethods = AnnotationIntrospector.resolveMethodAnnotations(clazz, Managed.class);
    Map<Method, ManagedName> methodNames = AnnotationIntrospector.resolveMethodAnnotations(clazz, ManagedName.class);
    Map<Method, ManagedDescription> methodDescriptions = AnnotationIntrospector.resolveMethodAnnotations(clazz, ManagedDescription.class);

    //
    Map<String, Method> setters = new HashMap<String, Method>();
    Map<String, Method> getters = new HashMap<String, Method>();
    ArrayList<ModelMBeanOperationInfo> operations = new ArrayList<ModelMBeanOperationInfo>();
    for (Map.Entry<Method, Managed> entry : managedMethods.entrySet()) {

      Method method = entry.getKey();

      //
      String operationDescription = "Management operation";
      ManagedDescription operationManagedDescription = methodDescriptions.get(method);
      if (operationManagedDescription != null) {
        operationDescription = operationManagedDescription.value();
      }

      //
      String methodName = method.getName();
      Class[] parameterTypes = method.getParameterTypes();

      //
      Type type = Type.OP;
      Integer index = null;
      if (method.getReturnType() == void.class) {
        if (parameterTypes.length == 1 &&
            methodName.startsWith("set") &&
            methodName.length() > 4) {
          type = Type.SET;
          index = 3;
        }
      } else {
        if (parameterTypes.length == 0) {
          if (methodName.startsWith("get") && methodName.length() > 3) {
            type = Type.GET;
            index = 3;
          } else if (methodName.startsWith("is") && methodName.length() > 2) {
            type = Type.IS;
            index = 2;
          }
        }
      }

      // Put in the correct map if it is an attribute
      if (index != null) {
        String attributeName = methodName.substring(index);

        //
        Map<String, Method> map = type == Type.SET ? setters : getters;
        Method previous = map.put(attributeName, method);
        if (previous != null) {
          throw new IllegalArgumentException("Duplicate attribute " + type.role + " " + previous + " and " + method);
        }
      } else {
        ManagedName managedName = methodNames.get(method);
        if (managedName != null) {
          throw new IllegalArgumentException("Managed operation " + method.getName() +
            " cannot be annoated with @" + ManagedName.class.getName() + " with value " + managedName.value());
        }
      }

      // Build the default mbean info
      ModelMBeanOperationInfo operationInfo = new ModelMBeanOperationInfo(operationDescription, method);

      // Overload with annotations meta data
      Annotation[][] parameterAnnotations = method.getParameterAnnotations();
      MBeanParameterInfo[] parameterInfos = operationInfo.getSignature();
      for (int i = 0;i < parameterAnnotations.length;i++) {
        MBeanParameterInfo parameterInfo = parameterInfos[i];
        String parameterName = parameterInfo.getName();
        String parameterDescription = operationInfo.getSignature()[i].getDescription();
        for (Annotation parameterAnnotation : parameterAnnotations[i]) {
          if (parameterAnnotation instanceof ManagedName) {
            parameterName = ((ManagedName)parameterAnnotation).value();
          } else if (parameterAnnotation instanceof ManagedDescription) {
            parameterDescription = ((ManagedDescription)parameterAnnotation).value();
          }
        }
        parameterInfos[i] = new MBeanParameterInfo(
          parameterName,
          parameterInfo.getType(),
          parameterDescription);
      }

      //
      Descriptor operationDescriptor = operationInfo.getDescriptor();
      operationDescriptor.setField("role", type.role);

      //
      operationInfo = new ModelMBeanOperationInfo(
        operationInfo.getName(),
        operationDescription,
        parameterInfos,
        operationInfo.getReturnType(),
        operationInfo.getImpact(),
        operationDescriptor);

      //
      operations.add(operationInfo);
    }

    // Process attributes
    Map<String, ModelMBeanAttributeInfo> attributeInfos = new HashMap<String, ModelMBeanAttributeInfo>();
    Set<String> attributeNames = new HashSet<String>();
    attributeNames.addAll(getters.keySet());
    attributeNames.addAll(setters.keySet());
    for (String attributeName : attributeNames) {
      Method getter = getters.get(attributeName);
      Method setter = setters.get(attributeName);

      ManagedDescription managedAttributeDescription = null;
      ManagedName getterName = null;
      ManagedName setterName = null;
      if (getter != null) {
        getterName = methodNames.get(getter);
        managedAttributeDescription = methodDescriptions.get(getter);
      }
      if (setter != null) {
        setterName = methodNames.get(setter);
        if (managedAttributeDescription == null) {
          managedAttributeDescription = methodDescriptions.get(setter);
        }
      }


      //
      if (getterName != null) {
        if (setterName != null) {
          if (!getterName.value().equals(setterName.value())) {
            throw new IllegalArgumentException();
          }
        }
        attributeName = getterName.value();
      } else if (setterName != null) {
        attributeName = setterName.value();
      }

      //
      try {
        String attributeDescription = managedAttributeDescription != null ?
          managedAttributeDescription.value() :
          ("Managed attribute " + attributeName);

        //
        ModelMBeanAttributeInfo attributeInfo = new ModelMBeanAttributeInfo(
            attributeName,
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
        ModelMBeanAttributeInfo previous = attributeInfos.put(attributeName, attributeInfo);
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
      clazz.getName(),
      mbeanDescription,
      attributeInfos.values().toArray(new ModelMBeanAttributeInfo[attributeInfos.size()]),
      new ModelMBeanConstructorInfo[0],
      operations.toArray(new ModelMBeanOperationInfo[operations.size()]),
      new ModelMBeanNotificationInfo[0]);
  }
}
