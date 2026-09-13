/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ch.qos.logback.core.joran.action.Action
 *  ch.qos.logback.core.joran.action.ActionUtil
 *  ch.qos.logback.core.joran.action.ActionUtil$Scope
 *  ch.qos.logback.core.joran.spi.ActionException
 *  ch.qos.logback.core.joran.spi.InterpretationContext
 *  ch.qos.logback.core.util.OptionHelper
 *  org.springframework.core.env.Environment
 */
package org.springframework.boot.logging.logback;

import ch.qos.logback.core.joran.action.Action;
import ch.qos.logback.core.joran.action.ActionUtil;
import ch.qos.logback.core.joran.spi.ActionException;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.util.OptionHelper;
import org.springframework.core.env.Environment;
import org.xml.sax.Attributes;

class SpringPropertyAction
extends Action {
    private static final String SOURCE_ATTRIBUTE = "source";
    private static final String DEFAULT_VALUE_ATTRIBUTE = "defaultValue";
    private final Environment environment;

    SpringPropertyAction(Environment environment) {
        this.environment = environment;
    }

    public void begin(InterpretationContext context, String elementName, Attributes attributes) throws ActionException {
        String name = attributes.getValue("name");
        String source = attributes.getValue(SOURCE_ATTRIBUTE);
        ActionUtil.Scope scope = ActionUtil.stringToScope((String)attributes.getValue("scope"));
        String defaultValue = attributes.getValue(DEFAULT_VALUE_ATTRIBUTE);
        if (OptionHelper.isEmpty((String)name) || OptionHelper.isEmpty((String)source)) {
            this.addError("The \"name\" and \"source\" attributes of <springProperty> must be set");
        }
        ActionUtil.setProperty((InterpretationContext)context, (String)name, (String)this.getValue(source, defaultValue), (ActionUtil.Scope)scope);
    }

    private String getValue(String source, String defaultValue) {
        if (this.environment == null) {
            this.addWarn("No Spring Environment available to resolve " + source);
            return defaultValue;
        }
        return this.environment.getProperty(source, defaultValue);
    }

    public void end(InterpretationContext context, String name) throws ActionException {
    }
}

