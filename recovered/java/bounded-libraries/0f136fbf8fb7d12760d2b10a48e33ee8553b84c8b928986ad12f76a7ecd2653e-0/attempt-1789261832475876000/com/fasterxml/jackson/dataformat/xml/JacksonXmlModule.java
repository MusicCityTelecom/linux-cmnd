/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.AnnotationIntrospector
 *  com.fasterxml.jackson.databind.Module$SetupContext
 *  com.fasterxml.jackson.databind.deser.BeanDeserializerModifier
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.fasterxml.jackson.databind.ser.BeanSerializerModifier
 */
package com.fasterxml.jackson.dataformat.xml;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlAnnotationIntrospector;
import com.fasterxml.jackson.dataformat.xml.PackageVersion;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.deser.XmlBeanDeserializerModifier;
import com.fasterxml.jackson.dataformat.xml.ser.XmlBeanSerializerModifier;
import java.io.Serializable;

public class JacksonXmlModule
extends SimpleModule
implements Serializable {
    private static final long serialVersionUID = 1L;
    protected boolean _cfgDefaultUseWrapper = true;
    protected String _cfgNameForTextElement = "";

    public JacksonXmlModule() {
        super("JacksonXmlModule", PackageVersion.VERSION);
    }

    public void setupModule(Module.SetupContext context) {
        context.addBeanSerializerModifier((BeanSerializerModifier)new XmlBeanSerializerModifier());
        context.addBeanDeserializerModifier((BeanDeserializerModifier)new XmlBeanDeserializerModifier(this._cfgNameForTextElement));
        context.insertAnnotationIntrospector(this._constructIntrospector());
        if (this._cfgNameForTextElement != "") {
            XmlMapper m = (XmlMapper)context.getOwner();
            m.setXMLTextElementName(this._cfgNameForTextElement);
        }
        super.setupModule(context);
    }

    public void setDefaultUseWrapper(boolean state) {
        this._cfgDefaultUseWrapper = state;
    }

    public void setXMLTextElementName(String name) {
        this._cfgNameForTextElement = name;
    }

    protected AnnotationIntrospector _constructIntrospector() {
        return new JacksonXmlAnnotationIntrospector(this._cfgDefaultUseWrapper);
    }
}

