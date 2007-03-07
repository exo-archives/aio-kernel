/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.component;

import java.util.Collection;

import org.drools.RuleBase;
import org.drools.WorkingMemory;
/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Apr 18, 2005
 * @version $Id: DroolRulePlugin.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class DroolRulePlugin implements  RulePlugin {
  private String name ;
  private String description ;
  private RuleBase ruleBase ;
  
  public String getName() {  return name ; }
  public void   setName(String s) { name =  s ; }
  
  public String getDescription() { return description ; }
  public void   setDescription(String s) { description = s ; }
  
  public  RuleBase getRuleBase()  { return ruleBase ; }
  public  void     setRuleBase(RuleBase  r) { ruleBase =  r ; }
  
  public void execute(Object fact) throws Exception {
    RuleBase rbase = getRuleBase() ;
    WorkingMemory workingMemory = rbase.newWorkingMemory( );
    workingMemory.assertObject(fact);
    workingMemory.fireAllRules( );
  }

  public void execute(Collection facts) throws Exception {
    execute(facts, false) ;
  }
  
  public void execute(Collection facts, boolean dynamic) throws Exception {
    RuleBase rbase = getRuleBase() ;
    WorkingMemory workingMemory = rbase.newWorkingMemory( );
    for(Object fact :  facts) {
      workingMemory.assertObject(fact, dynamic);
    }
    workingMemory.fireAllRules( );
  }
}
