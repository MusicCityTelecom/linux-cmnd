/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Content;
import be.tpvision.smartcontrol.repository.OrderDirection;
import be.tpvision.smartcontrol.repository.order_fields.OrderField;
import java.util.Set;

public interface ContentService {
    public Set<Content> getContent();

    public Set<Content> getContentOrderedBy(OrderField<? super Content> var1, OrderDirection var2);

    public Content getContent(String var1);

    public void addContent(Content var1);

    public void updateContent(Content var1);

    public void deleteContent(Content var1);

    public void deleteContent(String var1);
}

