/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.services.persondir.IPersonAttributes
 */
package org.apereo.services.persondir.support;

import java.util.List;
import java.util.Map;
import org.apereo.services.persondir.IPersonAttributes;

public interface IAdditionalDescriptors
extends IPersonAttributes {
    public void setName(String var1);

    public void addAttributes(Map<String, List<Object>> var1);

    public void setAttributes(Map<String, List<Object>> var1);

    public List<Object> setAttributeValues(String var1, List<Object> var2);

    public List<Object> removeAttribute(String var1);
}

