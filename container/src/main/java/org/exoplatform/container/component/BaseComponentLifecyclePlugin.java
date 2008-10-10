package org.exoplatform.container.component;

import java.util.List;

import org.exoplatform.container.ExoContainer;

abstract public class BaseComponentLifecyclePlugin implements ComponentLifecyclePlugin {
  private String       name;

  private String       description;

  private List<String> manageableComponents;

  public String getName() {
    return name;
  }

  public void setName(String s) {
    name = s;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String s) {
    description = s;
  }

  public List<String> getManageableComponents() {
    return manageableComponents;
  }

  public void setManageableComponents(List<String> list) {
    manageableComponents = list;
  }

  public void initComponent(ExoContainer container, Object component) throws Exception {

  }

  public void startComponent(ExoContainer container, Object component) throws Exception {

  }

  public void stopComponent(ExoContainer container, Object component) throws Exception {

  }

  public void destroyComponent(ExoContainer container, Object component) throws Exception {

  }
}
