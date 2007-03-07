/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.download.test;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.download.ClasspathDownloadResource;
import org.exoplatform.services.download.DownloadResource;
import org.exoplatform.services.download.DownloadService;
import org.exoplatform.services.download.FileDownloadResource;
import org.exoplatform.services.download.InputStreamDownloadResource;
import org.exoplatform.test.BasicTestCase;

/**
 * Created by The eXo Platform SARL
 * Author : Tuan Nguyen
 *          tuan08@users.sourceforge.net
 * Dec 26, 2005
 */
public class TestDownloadService extends BasicTestCase {  
  private DownloadService service_ ;
  
  public TestDownloadService(String name) {
    super(name) ; 
  }
  
  public void setUp() throws Exception {
    if(service_ ==null) {
      PortalContainer manager = PortalContainer.getInstance() ;
      service_ = (DownloadService)manager.getComponentInstanceOfType(DownloadService.class) ;      
    }
  }
  
  public void testDownloadService() throws Exception {
    assertTrue("expect service is inited",service_!=null)  ;
    
    String basepath = "conf/portal" ;
    ClasspathDownloadResource cpathResource = 
      new ClasspathDownloadResource("classpath", basepath+"/configuration.xml","text/xml") ;    
    assertTrue("expect classpath download resource is created",cpathResource!=null) ;
    String classpathId = service_.addDownloadResource(cpathResource) ;
    System.out.println("Download link of resource======>"+service_.getDownloadLink(classpathId)) ;
    ClasspathDownloadResource cpresourceFirst = 
      (ClasspathDownloadResource)service_.getDownloadResource(classpathId) ;   
    assertTrue("expect class path resource  is found for the first time get",
        cpresourceFirst!=null && "text/xml".equals(cpresourceFirst.getResourceMimeType())) ;
    ClasspathDownloadResource cpresouceSecond = 
      (ClasspathDownloadResource)service_.getDownloadResource(classpathId) ;
    assertTrue("expect download resource is not found for the second time get",cpresouceSecond==null) ;
    
    ClasspathDownloadResource cresource = 
      new ClasspathDownloadResource(basepath+"/test-configuration.xml","text/xml") ;
    InputStreamDownloadResource isresource= 
      new InputStreamDownloadResource(cresource.getInputStream(),"application/stream") ;
    String isresourceId = service_.addDownloadResource(isresource) ;
    System.out.println("Download link of resource======>"+service_.getDownloadLink(isresourceId)) ;
    InputStreamDownloadResource istreamResource = 
      (InputStreamDownloadResource)service_.getDownloadResource(isresourceId) ;
    assertTrue("expect input stream resource is found",
        istreamResource!=null && "application/stream".equals(istreamResource.getResourceMimeType())) ;
    
    String basedir = System.getProperty("basedir");
    FileDownloadResource defaultFileResource =
      new FileDownloadResource("file", basedir + "/pom.xml","text/plain") ;    
    service_.addDefaultDownloadResource(defaultFileResource) ;
    
    FileDownloadResource file1 =
      new FileDownloadResource("file", basedir+"/src/java/conf/portal/configuration.xml","text/xml") ;
    assertTrue("expect file resource is not null",file1!=null) ;
    String file1Id = service_.addDownloadResource(file1) ;
    System.out.println("Download link of resource======>"+service_.getDownloadLink(file1Id)) ;
    DownloadResource file1GetFirst = service_.getDownloadResource(file1Id) ;
    DownloadResource file1GetSecond = service_.getDownloadResource(file1Id) ;
    assertTrue("expect file1 is found for the first time get",
        file1GetFirst != null && "text/xml".equals(file1GetFirst.getResourceMimeType())) ;                
    assertTrue("expect file1 is found , but is the default one",  
        file1GetSecond != null && "text/plain".equals(file1GetSecond.getResourceMimeType())) ;
    
    file1GetSecond = service_.getDownloadResource(file1Id) ;    
    assertTrue("expect default resource can get every times",  
        file1GetSecond != null && "text/plain".equals(file1GetSecond.getResourceMimeType())) ;
    
  }
  
  protected String getDescription() {
    return "Test Download Service" ;
  }
}
