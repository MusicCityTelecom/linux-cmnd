/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.context.ResourceLoaderAware
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.security.core.authority.mapping.MappableAttributesRetriever
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.authentication.preauth.j2ee;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.authority.mapping.MappableAttributesRetriever;
import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class WebXmlMappableAttributesRetriever
implements ResourceLoaderAware,
MappableAttributesRetriever,
InitializingBean {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private ResourceLoader resourceLoader;
    private Set<String> mappableAttributes;

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Set<String> getMappableAttributes() {
        return this.mappableAttributes;
    }

    public void afterPropertiesSet() throws Exception {
        Resource webXml = this.resourceLoader.getResource("/WEB-INF/web.xml");
        Document doc = this.getDocument(webXml.getInputStream());
        NodeList webApp = doc.getElementsByTagName("web-app");
        Assert.isTrue((webApp.getLength() == 1 ? 1 : 0) != 0, () -> "Failed to find 'web-app' element in resource" + webXml);
        NodeList securityRoles = ((Element)webApp.item(0)).getElementsByTagName("security-role");
        List<String> roleNames = this.getRoleNames(webXml, securityRoles);
        this.mappableAttributes = Collections.unmodifiableSet(new HashSet<String>(roleNames));
    }

    private List<String> getRoleNames(Resource webXml, NodeList securityRoles) {
        ArrayList<String> roleNames = new ArrayList<String>();
        for (int i = 0; i < securityRoles.getLength(); ++i) {
            Element securityRoleElement = (Element)securityRoles.item(i);
            NodeList roles = securityRoleElement.getElementsByTagName("role-name");
            if (roles.getLength() > 0) {
                String roleName = roles.item(0).getTextContent().trim();
                roleNames.add(roleName);
                this.logger.info((Object)("Retrieved role-name '" + roleName + "' from web.xml"));
                continue;
            }
            this.logger.info((Object)("No security-role elements found in " + webXml));
        }
        return roleNames;
    }

    private Document getDocument(InputStream aStream) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver(new MyEntityResolver());
            Document document = builder.parse(aStream);
            return document;
        }
        catch (IOException | FactoryConfigurationError | ParserConfigurationException | SAXException ex) {
            throw new RuntimeException("Unable to parse document object", ex);
        }
        finally {
            try {
                aStream.close();
            }
            catch (IOException ex) {
                this.logger.warn((Object)"Failed to close input stream for web.xml", (Throwable)ex);
            }
        }
    }

    private static final class MyEntityResolver
    implements EntityResolver {
        private MyEntityResolver() {
        }

        @Override
        public InputSource resolveEntity(String publicId, String systemId) {
            return new InputSource(new StringReader(""));
        }
    }
}

