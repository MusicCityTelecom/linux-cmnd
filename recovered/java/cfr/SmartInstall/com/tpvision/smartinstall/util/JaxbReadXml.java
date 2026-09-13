/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JaxbReadXml {
    private static final Logger LOG = LoggerFactory.getLogger(JaxbReadXml.class);

    private JaxbReadXml() {
    }

    public static <T> T readString(Class<T> clazz, String context) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(clazz);
        Unmarshaller u = jc.createUnmarshaller();
        return (T)u.unmarshal(new File(context));
    }

    public static String convertToXml(Object obj, String encoding, String settingLocation) {
        String result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", true);
            marshaller.setProperty("jaxb.encoding", encoding);
            marshaller.marshal(obj, new File(settingLocation));
        }
        catch (Exception e) {
            LOG.error("" + e.getMessage(), e);
        }
        return result;
    }
}

