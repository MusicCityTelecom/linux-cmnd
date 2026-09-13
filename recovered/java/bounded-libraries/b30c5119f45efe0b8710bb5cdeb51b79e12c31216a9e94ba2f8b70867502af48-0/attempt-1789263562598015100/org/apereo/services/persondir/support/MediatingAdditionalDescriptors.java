/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.Validate
 */
package org.apereo.services.persondir.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.Validate;
import org.apereo.services.persondir.support.IAdditionalDescriptors;

public class MediatingAdditionalDescriptors
implements IAdditionalDescriptors {
    private static final long serialVersionUID = 1L;
    private List<IAdditionalDescriptors> delegateDescriptors = Collections.emptyList();

    public void setDelegateDescriptors(List<IAdditionalDescriptors> delegateDescriptors) {
        Validate.noNullElements(delegateDescriptors, (String)"delegateDescriptors List cannot be null or contain null attributes", (Object[])new Object[0]);
        this.delegateDescriptors = new ArrayList<IAdditionalDescriptors>(delegateDescriptors);
    }

    @Override
    public void addAttributes(Map<String, List<Object>> attributes) {
        for (IAdditionalDescriptors additionalDescriptors : this.delegateDescriptors) {
            additionalDescriptors.addAttributes(attributes);
        }
    }

    @Override
    public List<Object> removeAttribute(String name) {
        ArrayList<Object> removedValues = null;
        for (IAdditionalDescriptors additionalDescriptors : this.delegateDescriptors) {
            List<Object> values = additionalDescriptors.removeAttribute(name);
            if (values == null) continue;
            if (removedValues == null) {
                removedValues = new ArrayList<Object>(values);
                continue;
            }
            removedValues.addAll(values);
        }
        return removedValues;
    }

    @Override
    public List<Object> setAttributeValues(String name, List<Object> values) {
        ArrayList<Object> replacedValues = null;
        for (IAdditionalDescriptors additionalDescriptors : this.delegateDescriptors) {
            List<Object> oldValues = additionalDescriptors.setAttributeValues(name, values);
            if (oldValues == null) continue;
            if (replacedValues == null) {
                replacedValues = new ArrayList<Object>(oldValues);
                continue;
            }
            replacedValues.addAll(oldValues);
        }
        return replacedValues;
    }

    @Override
    public void setAttributes(Map<String, List<Object>> attributes) {
        for (IAdditionalDescriptors additionalDescriptors : this.delegateDescriptors) {
            additionalDescriptors.setAttributes(attributes);
        }
    }

    @Override
    public void setName(String name) {
        for (IAdditionalDescriptors additionalDescriptors : this.delegateDescriptors) {
            additionalDescriptors.setName(name);
        }
    }

    public Object getAttributeValue(String name) {
        for (IAdditionalDescriptors additionalDescriptors : this.delegateDescriptors) {
            Map attributes = additionalDescriptors.getAttributes();
            if (attributes == null || !attributes.containsKey(name)) continue;
            return additionalDescriptors.getAttributeValue(name);
        }
        return null;
    }

    public List<Object> getAttributeValues(String name) {
        for (IAdditionalDescriptors additionalDescriptors : this.delegateDescriptors) {
            Map attributes = additionalDescriptors.getAttributes();
            if (attributes == null || !attributes.containsKey(name)) continue;
            return additionalDescriptors.getAttributeValues(name);
        }
        return null;
    }

    public Map<String, List<Object>> getAttributes() {
        for (IAdditionalDescriptors additionalDescriptors : this.delegateDescriptors) {
            Map attributes = additionalDescriptors.getAttributes();
            if (attributes == null || attributes.isEmpty()) continue;
            return attributes;
        }
        return Collections.emptyMap();
    }

    public String getName() {
        for (IAdditionalDescriptors additionalDescriptors : this.delegateDescriptors) {
            String name = additionalDescriptors.getName();
            if (name == null) continue;
            return name;
        }
        return null;
    }
}

