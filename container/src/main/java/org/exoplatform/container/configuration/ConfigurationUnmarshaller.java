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
package org.exoplatform.container.configuration;

import org.exoplatform.container.xml.Configuration;
import org.xml.sax.InputSource;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;
import org.xml.sax.SAXException;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IUnmarshallingContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.Transformer;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringWriter;
import java.io.StringReader;
import java.net.URL;

/**
 * Unmarshall a configuration.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class ConfigurationUnmarshaller {

  private class Reporter implements ErrorHandler {

    private final URL url;

    private Reporter(URL url) {
      this.url = url;
    }

    private void log(String prefix, SAXParseException e) {
      System.out.println(prefix + " in document " + url + "  at (" + e.getLineNumber()
        + "," + e.getColumnNumber() + ") :" + e.getMessage());
    }

    public void warning(SAXParseException exception) throws SAXException {
      log("Warning", exception);
    }

    public void error(SAXParseException exception) throws SAXException {
      if (exception.getMessage().equals("cvc-elt.1: Cannot find the declaration of element 'configuration'.")) {
        System.out.println("The document " + url + " does not contain a schema declaration, it should have an " +
        "XML declaration similar to\n" +
        "<configuration\n" +
        "   xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
        "   xsi:schemaLocation=\"http://www.exoplaform.org/xml/ns/kernel_1_0.xsd http://www.exoplaform.org/xml/ns/kernel_1_0.xsd\"\n" +
        "   xmlns=\"http://www.exoplaform.org/xml/ns/kernel_1_0.xsd\">");
      } else {
        log("Error", exception);
      }
    }

    public void fatalError(SAXParseException exception) throws SAXException {
      log("Fatal error", exception);
    }
  }

  public Configuration unmarshall(URL url) throws Exception {
    //
    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    URL schemaURL = getClass().getResource("kernel-configuration_1_0.xsd");
    if (schemaURL != null) {
      Schema schema = factory.newSchema(schemaURL);
      Validator validator = schema.newValidator();
      validator.setErrorHandler(new Reporter(url));

      // Validate the document
      validator.validate(new StreamSource(url.openStream()));
    }

    // The buffer
    StringWriter buffer = new StringWriter();

    // Create a sax transformer result that will serialize the output
    SAXTransformerFactory tf = (SAXTransformerFactory)SAXTransformerFactory.newInstance();
    final TransformerHandler hd = tf.newTransformerHandler();
    hd.setResult(new StreamResult(buffer));
    Transformer serializer = tf.newTransformer();
    serializer.setOutputProperty(OutputKeys.ENCODING,"UTF8");
    serializer.setOutputProperty(OutputKeys.INDENT,"yes");

    // Perform
    InputSource source = new InputSource(url.openStream());
    SAXParserFactory spf = SAXParserFactory.newInstance();
    SAXParser saxParser = spf.newSAXParser();
    saxParser.parse(source, new NoKernelNamespaceSAXFilter(hd));

    // Reuse the parsed document
    String document = buffer.toString();

    // Debug
//    Log log = LogFactory.getLog(ConfigurationUnmarshaller.class);
//    log.debug("About to parse\n" + document);
//    System.out.println("document = " + document);

    //
    IBindingFactory bfact = BindingDirectory.getFactory(Configuration.class);
    IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
    return (Configuration) uctx.unmarshalDocument(new StringReader(document), null);
  }

}
